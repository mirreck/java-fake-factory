package com.github.mirreck;

import java.util.Random;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SampleTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleTest.class);

    private static final long SEED = 2;

    @Test
    public void simpleExample() {
        FakeFactory factory = new FakeFactory();

        String streetName = factory.streetName(); // Celestino Street
        String city = factory.city(); // Lakewilfordfurt
        double[] coordinatesLatLng = factory.coordinatesLatLng(); // [-0.6698821091060267, -53.76053391427611]

        LOGGER.info("streetName : {}", streetName);
        LOGGER.info("city : {}", city);
        LOGGER.info("coordinatesLatLng : {}", coordinatesLatLng);
    }

    @Test
    public void fixedSeedExample() {

        FakeFactory fixedSeedFactory = new FakeFactory(new Random(SEED));

        String streetName = fixedSeedFactory.streetName();

        LOGGER.info("streetName : {}", streetName);
    }

}
