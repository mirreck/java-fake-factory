package com.github.javafaker;

import static org.apache.commons.lang.math.RandomUtils.nextInt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

public class RandomUtils {

    private static final double GAUSSIAN_LIMIT = 3.0d;
    
    private RandomUtils() {
        // Utility class: private constructor
    }
    
    
    private static Random newRandom() {
        return new Random(System.currentTimeMillis());
    }
    
    public static <T> T randomElement(T[] array) {
        return randomElement(newRandom(), array);
    }
    
    public static <T> T randomElement(Random random,T[] array) {
        return array[nextInt(random, array.length)];
    }

    public static <T> T randomElement(Set<T> set) {
        return randomElement(newRandom(), set);
    }
    
    
    @SuppressWarnings("unchecked")
    public static <T> T randomElement(Random random,Set<T> set) {
        return  (T) set.toArray()[nextInt(random, set.size())];
    }
    
    public static <T> T randomElement(List<T> list) {
        return randomElement(newRandom(), list);
    }
    
    public static <T> T randomElement(Random random,List<T> list) {
        return list.get(nextInt(random, list.size()));
    }
    
    
    public static <T> T randomWeightedElement( Map<T, Integer> map) {
        return randomWeightedElement(newRandom(), map);
    }

    
    public static <T> T randomWeightedElement(Random random, Map<T, Integer> map) {
        Collection<Integer> values = map.values();
        int totalWeight = 0;
        for (Integer val : values) {
            totalWeight += val;
        }
        int index = nextInt(random, totalWeight);
        int i = 0;
        for (Entry<T, Integer> entry : map.entrySet()) {
            int next = i + entry.getValue();
            if (index >= i && index < next) {
                return entry.getKey();
            }
            i = next;
        }
        return null;
    }
    
    public static int intInInterval(int min, int max){
        return intInInterval(newRandom(), min, max);
    }
    
    public static int intInInterval(Random random,int min, int max){
        return min + random.nextInt(max - min);
    }
    
    public static double doubleInInterval(double min, double max) {
        return doubleInInterval(newRandom(), min, max);
    }
    public static double doubleInInterval(Random random, double min, double max) {
        return min + (max - min) * random.nextDouble();
    }
    
    public static int gaussianInt(Random random, int min, int max) {
        if (max < min) {
            throw new IllegalArgumentException("max < min");
        }
        double det = (random.nextGaussian() + GAUSSIAN_LIMIT) / GAUSSIAN_LIMIT / 2.0d;
        return min + (int)(det * (max - min));
    }
    
}
