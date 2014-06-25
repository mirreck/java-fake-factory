package com.github.mirreck.fr;

import static org.fest.assertions.Assertions.assertThat;

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
   
   @Test
   public void testPhone(){
       assertThat(factory.phoneNumber()).isEqualTo("+33 134043777");
   }
   @Test
   public void testCellPhone(){
       assertThat(factory.cellPhoneNumber()).isEqualTo("+33 734043777");
   }
   
   
   @Test
   public void testMany() {
      for (int i = 0; i < 1000; i++) {
         LOGGER.info("street:"+ factory.streetName());
        
      }
      
   }


}
