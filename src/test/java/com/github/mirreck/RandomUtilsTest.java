package com.github.mirreck;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.github.mirreck.RandomUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class RandomUtilsTest {

    private static final long SEED = 123456789L;

    private Random rnd;

    @Before
    public void init() {
        rnd = new Random(SEED);
    }

    @Test
    public void testRandomElementTArray() {
        String[] array = new String[] { "1", "2", "3" };
        assertThat(RandomUtils.randomElement(rnd, array)).isEqualTo("2");
    }

    @Test
    public void testRandomElementListOfT() {
        List<String> array = Lists.newArrayList("1", "2", "3");
        assertThat(RandomUtils.randomElement(rnd, array)).isEqualTo("2");
    }

    @Test
    public void testRandomWeightedElementMapOfTInteger() {
        Map<String, Integer> map = Maps.newHashMap();
        map.put("1", 1);
        map.put("2", 1);
        map.put("3", 2);
        assertThat(RandomUtils.randomWeightedElement(rnd, map)).isEqualTo("2");
    }

    @Test
    public void testIntInIntervalIntInt() {
        assertThat(RandomUtils.intInInterval(rnd, 1, 3)).isEqualTo(2);
    }

    @Test
    public void testDoubleInIntervalDoubleDouble() {
        assertThat(RandomUtils.doubleInInterval(rnd, 1.0, 3.0)).isEqualTo(2.328076206544532);
    }

    @Test
    public void testGaussianInt() {
        assertThat(RandomUtils.gaussianInt(rnd, 1, 3)).isEqualTo(2);
    }

}
