package com.github.javafaker.fr;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FakerFrTest {

   private static final Logger LOGGER = LoggerFactory.getLogger(FakerFrTest.class);
   
   private static final long SEED = 123456789L;

   private FakerFr faker;

   @Before
   public void init() {
      faker = new FakerFr(new Random(SEED));
   }
   public void testStreetName() {
      
   }
   @Test
   public void testMany() {
      for (int i = 0; i < 1000; i++) {
         LOGGER.info("street:"+ faker.streetName());
        
      }
      
   }


}
