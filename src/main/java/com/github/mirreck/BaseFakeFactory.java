package com.github.mirreck;

import static org.apache.commons.lang.StringUtils.capitalize;
import static org.apache.commons.lang.StringUtils.join;
import static org.apache.commons.lang.math.RandomUtils.nextInt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.WordUtils;
import org.ho.yaml.Yaml;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class BaseFakeFactory {
    private static final String ROOT_ELEMENT = "faker";

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseFakeFactory.class);

    private static final String PARAM_PREFIX = "{{";
    private static final String PARAM_SUFFIX = "}}";
    private static final char[] METHOD_NAME_DELIMITERS = { '_' };
    private static final String DIGITS = "0123456789";
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";


    private Map<String, Object> fakeValuesMap;

    protected final Locale locale;

    protected final Random random;

    public BaseFakeFactory(Locale locale) {
        this(locale, new Random());
    }

    public BaseFakeFactory(Locale locale, Random random) {
        LOGGER.debug("Using locale " + locale);
        this.locale = locale;
        String languageCode = locale.getLanguage();
        Map valuesMap = (Map) Yaml.load(BaseFakeFactory.class.getResourceAsStream(languageCode + ".yml"));
        valuesMap = (Map) valuesMap.get(languageCode);
        fakeValuesMap = (Map<String, Object>) valuesMap.get(ROOT_ELEMENT);

        this.random = random;
    }

    protected BaseFakeFactory extend(Locale locale) {
        return extend(locale.getLanguage());
    }

    protected BaseFakeFactory extend(String localeExtension) {
        Map valuesMap = (Map) Yaml.load(BaseFakeFactory.class.getResourceAsStream(localeExtension + ".yml"));
        valuesMap = (Map) valuesMap.get(locale.getLanguage());
        fakeValuesMap.putAll((Map<String, Object>) valuesMap.get(ROOT_ELEMENT));
        return this;
    }



    /**
     * Return the object selected by the key from yaml file.
     * 
     * @param key
     *            key contains path to an object. Path segment is separated by
     *            dot. E.g. name.first_name
     * @return
     */
    private Object fetchObject(String key) {
        String[] path = key.split("\\.");
        Object currentValue = fakeValuesMap;
        for (String pathSection : path) {
            if (currentValue == null) {
                return null;
            }
            currentValue = ((Map<String, Object>) currentValue).get(pathSection);
        }
        return currentValue;
    }

    /**
     * Fetch a random value from an array item specified by the key
     * 
     * @param key
     * @return
     */
    private Object fetch(String key) {
        List valuesArray = (List) fetchObject(key);
        if (valuesArray == null) {
            return null;
        }
        return valuesArray.get(nextInt(random, valuesArray.size()));
    }

    private String fetchString(String key) {
        return (String) fetch(key);
    }

    protected Integer fetchInteger(String key) {
        return (Integer) fetchObject(key);
    }

    private List<Object> fetchList(String key) {
        Object obj = fetchObject(key);
        if (!(obj instanceof List<?>)) {
            throw new FakeFactoryException("Fetched Object is not a List. Key=" + key);
        }
        return (List<Object>) obj;
    }

    private static boolean isWeightMap(Object obj) {
        if (!(obj instanceof Map<?, ?>)) {
            return false;
        }
        Map<?, ?> map = (Map<?, ?>) obj;
        if (map.isEmpty()) {
            return false;
        }
        return map.keySet().iterator().next() instanceof String && map.values().iterator().next() instanceof Integer;
    }

    protected String evaluate(String key) {
        Object obj = fetchObject(key);
        return evaluateObject(obj);
    }

    private String evaluateObject(Object obj) {

        if (obj instanceof List<?>) {
            List<String> list = (List<String>) obj;
            return evaluatePattern(RandomUtils.randomElement(random,list));
        } else if (isWeightMap(obj)) {
            Map<String, Integer> map = (Map<String, Integer>) obj;
            return evaluatePattern(RandomUtils.randomWeightedElement(random,map));
        } else if (obj instanceof String) {
            String str = (String) obj;
            return evaluatePattern(str);
        }
        return null;
    }

    private String evaluatePattern(String pattern) {
        int prefixIndex = pattern.indexOf(PARAM_PREFIX);
        if (prefixIndex != -1) {
            int suffixIndex = pattern.indexOf(PARAM_SUFFIX, prefixIndex);
            if (suffixIndex == -1) {
                throw new FakeFactoryException("Unmatched prefix/suffix");
            }
            String expression = pattern.substring(prefixIndex + PARAM_PREFIX.length(), suffixIndex);
            String replacement = evaluate(expression);
            if (replacement == null) {
                replacement = evaluateCall(expression);
            }
            return evaluatePattern(pattern.substring(0, prefixIndex) + replacement
                    + pattern.substring(suffixIndex + PARAM_SUFFIX.length()));
        }
        return bothify(pattern);
    }

    private String evaluateCall(String method) {
        String methodName = toCamelCase(method);
        try {
            return (String) FakeFactory.class.getDeclaredMethod(methodName, (Class[]) null).invoke(this);
        } catch (Exception e) {
            throw new FakeFactoryException("Unable to call method :" + method, e);
        }
    }

    protected String[] evaluateMultipleLines(String key) {
        List list = fetchList(key);
        List<String> result = new ArrayList<String>(list.size());
        for (Object line : list) {
            result.add(evaluateObject(line));
        }
        return result.toArray(new String[result.size()]);
    }

    public char digit() {
        return DIGITS.charAt(nextInt(random, DIGITS.length()));
    }

    public String digits(int size) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(digit());
        }
        return sb.toString();
    }

    public char letter() {
        return LETTERS.charAt(nextInt(random, LETTERS.length()));
    }

    public String letters(int size) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(letter());
        }
        return sb.toString();
    }
    
    public String numerify(String numberString) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberString.length(); i++) {
            if (numberString.charAt(i) == '#') {
                sb.append(digit());
            } else {
                sb.append(numberString.charAt(i));
            }
        }
        return sb.toString();
    }

    public String letterify(String letterString) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < letterString.length(); i++) {
            if (letterString.charAt(i) == '?') {
                sb.append(letter()); // a-z
            } else {
                sb.append(letterString.charAt(i));
            }
        }
        return sb.toString();
    }

    public String bothify(String string) {
        return letterify(numerify(string));
    }

    private String toCamelCase(String methodName) {
        String result = WordUtils.capitalizeFully(methodName, METHOD_NAME_DELIMITERS).replaceAll("_", "");
        result = result.substring(0, 1).toLowerCase() + result.substring(1);
        return result;
    }

    public List<String> words(int num) {
        List<String> words = new ArrayList<String>();
        for (int i = 0; i < num; i++) {
            words.add(fetchString("lorem.words"));
        }
        return words;
    }

    public List<String> words() {
        return words(3);
    }

    public String sentence(int wordCount) {
        return capitalize(join(words(wordCount + nextInt(random, 6)), " ") + ".");
    }

    public String sentence() {
        return sentence(3);
    }

    public List<String> sentences(int sentenceCount) {
        List<String> sentences = new ArrayList<String>(sentenceCount);
        for (int i = 0; i < sentenceCount; i++) {
            sentences.add(sentence());
        }
        return sentences;
    }

    public String paragraph(int sentenceCount) {
        return join(sentences(sentenceCount + nextInt(random, 3)), " ");
    }

    public String paragraph() {
        return paragraph(3);
    }

    public List<String> paragraphs(int paragraphCount) {
        List<String> paragraphs = new ArrayList<String>(paragraphCount);
        for (int i = 0; i < paragraphCount; i++) {
            paragraphs.add(paragraph());
        }
        return paragraphs;
    }


    
    public Date date(int minYear, int maxYear){
        DateTime dt = new DateTime();
        dt.withYear(RandomUtils.intInInterval(random,minYear,maxYear));
        dt.withDayOfYear(RandomUtils.intInInterval(random,0,365));
        return dt.toDate();
    }
    

    public double[] coordinatesLatLng() {
        return new double[] { RandomUtils.doubleInInterval(random,-90.0d, 90.0d), RandomUtils.doubleInInterval(random,-180.0d, 180.0d) };
    }
}