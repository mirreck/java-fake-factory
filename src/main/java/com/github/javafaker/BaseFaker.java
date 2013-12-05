package com.github.javafaker;

import static org.apache.commons.lang.StringUtils.*;
import static org.apache.commons.lang.math.RandomUtils.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang.text.StrBuilder;
import org.ho.yaml.Yaml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({ "unchecked", "rawtypes"})
public class BaseFaker {
   private static final Logger LOGGER = LoggerFactory.getLogger(BaseFaker.class);

   private static final String PARAM_PREFIX = "{{";
   private static final String PARAM_SUFFIX = "}}";
   private static final char[] METHOD_NAME_DELIMITERS = { '_'};
   public static final String DIGITS = "0123456789";
   public static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";
   private static final double GAUSSIAN_LIMIT = 3.0d;

   private Map<String, Object> fakeValuesMap;

   private final Random random;

   public BaseFaker(Locale locale) {
      this(locale, new Random());
   }

   public BaseFaker(Locale locale, Random random) {
      LOGGER.debug("Using locale " + locale);
      String languageCode = locale.getLanguage();
      Map valuesMap = (Map) Yaml.load(BaseFaker.class.getResourceAsStream(languageCode + ".yml"));
      valuesMap = (Map) valuesMap.get(languageCode);
      fakeValuesMap = (Map<String, Object>) valuesMap.get("faker");

      this.random = random;
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

   public <T> T randomElement(T[] array) {
      return array[nextInt(random, array.length)];
   }

   public <T> T randomElement(List<T> list) {
      return list.get(nextInt(random, list.size()));
   }

   public <T> T randomWeightedElement(Map<T, Integer> map) {
      Collection<Integer> values = map.values();
      int totalWeight = 0;
      for (Integer val : values) {
         totalWeight += val;
      }
      int index = nextInt(random, totalWeight);
      int i = 0;
      for (T key : map.keySet()) {
         int next = i + map.get(key);
         if (index >= i && index < next) {
            return key;
         }
         i = next;
      }
      return null;
   }

   public int randomGaussianInt(int min, int max) {
      if (max < min) {
         throw new IllegalArgumentException("max < min");
      }
      double det = (random.nextGaussian() + GAUSSIAN_LIMIT) / GAUSSIAN_LIMIT / 2.0d;
      return min + new Double(det * (max - min)).intValue();
   }

   public String evaluate(String key) {
      // LOGGER.info("evaluate =" + key);
      Object obj = fetchObject(key);
      if (obj instanceof List< ? >) {
         // LOGGER.info("evaluate (List) =" + key);
         List<String> list = (List<String>) obj;
         return evaluatePattern(randomElement(list));
      }
      else if (isWeightMap(obj)) {
         // LOGGER.info("evaluate (WeightMap) =" + key);
         Map<String, Integer> map = (Map<String, Integer>) obj;
         return evaluatePattern(randomWeightedElement(map));
      }
      else if (obj instanceof String) {
         String str = (String) obj;
         return evaluatePattern(str);
      }
      return null;
   }

   private boolean isWeightMap(Object obj) {
      if (!(obj instanceof Map< ? , ? >)) {
         return false;
      }
      Map< ? , ? > map = (Map< ? , ? >) obj;
      if (map.isEmpty()) {
         return false;
      }
      return map.keySet().iterator().next() instanceof String && map.values().iterator().next() instanceof Integer;
   }

   public String evaluatePattern(String pattern) {
      // LOGGER.info("evaluatePattern =" + pattern);
      int prefixIndex = pattern.indexOf(PARAM_PREFIX);
      if (prefixIndex != -1) {
         int suffixIndex = pattern.indexOf(PARAM_SUFFIX, prefixIndex);
         if (suffixIndex == -1) {
            throw new RuntimeException("Unmatched prefix/suffix");
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

   public String evaluateCall(String method) {
      // LOGGER.info("evaluateCall =" + method);
      String methodName = toCamelCase(method);
      try {
         return (String) Faker.class.getDeclaredMethod(methodName, (Class[]) null).invoke(this);
      }
      catch (Exception e) {
         e.printStackTrace();
         throw new RuntimeException("Unable to call method :" + method, e);
      }
   }

   public String numerify(String numberString) {
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < numberString.length(); i++) {
         if (numberString.charAt(i) == '#') {
            sb.append(digit());
         }
         else {
            sb.append(numberString.charAt(i));
         }
      }

      return sb.toString();
   }

   public String letterify(String letterString) {
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < letterString.length(); i++) {
         if (letterString.charAt(i) == '?') {
            sb.append(letter()); // a-z
         }
         else {
            sb.append(letterString.charAt(i));
         }
      }

      return sb.toString();
   }

   public String bothify(String string) {
      // LOGGER.info("bothify =" + string);
      return letterify(numerify(string));
   }

   /**
    * Fetch a random value from an array item specified by the key
    * 
    * @param key
    * @return
    */
   public Object fetch(String key) {
      List valuesArray = (List) fetchObject(key);
      if (valuesArray == null) {
         return null;
      }
      return valuesArray.get(nextInt(random, valuesArray.size()));
   }

   public String fetchString(String key) {
      return (String) fetch(key);
   }

   public Integer fetchInteger(String key) {
      return (Integer) fetchObject(key);
   }

   /**
    * Return the object selected by the key from yaml file.
    * 
    * @param key key contains path to an object. Path segment is separated by dot. E.g. name.first_name
    * @return
    */
   public Object fetchObject(String key) {
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

   private String toCamelCase(String methodName) {
      // convert to camel case
      methodName = WordUtils.capitalizeFully(methodName, METHOD_NAME_DELIMITERS).replaceAll("_", "");
      methodName = methodName.substring(0, 1).toLowerCase() + methodName.substring(1);
      return methodName;
   }

   protected String capitalizeFully(String str) {
      int strLen;
      if (str == null || (strLen = str.length()) == 0) {
         return str;
      }
      return new StrBuilder(strLen).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1).toLowerCase()).toString();
   }

   // lorem
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
}
