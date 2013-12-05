package com.github.javafaker.en;

import static org.fest.assertions.Assertions.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class FakerEnTest {

   private static final long SEED = 123456789L;

   private FakerEn faker;

   @Before
   public void init() {
      faker = new FakerEn(new Random(SEED));
   }

   @Test
   public void testState() {
      assertThat(faker.state()).isEqualTo("Kansas");
   }

   @Test
   public void testStateAbbr() {
      assertThat(faker.stateAbbr()).isEqualTo("VI");
   }

}
