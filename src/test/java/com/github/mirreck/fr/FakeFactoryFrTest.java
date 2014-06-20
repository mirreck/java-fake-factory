package com.github.mirreck.fr;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FakeFactoryFrTest {

   private static final Logger LOGGER = LoggerFactory.getLogger(FakeFactoryFrTest.class);
   
   private static final long SEED = 123456789L;

   private FakeFactoryFr factory;

   @Before
   public void init() {
      factory = new FakeFactoryFr(new Random(SEED));
   }
   public void testStreetName() {
      
   }
   @Test
   public void testMany() {
      for (int i = 0; i < 1000; i++) {
         LOGGER.info("street:"+ factory.streetName());
        
      }
      
   }


}
