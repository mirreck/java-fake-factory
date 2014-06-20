package com.github.mirreck.en;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class FakeFactoryEnUsTest {

   private static final long SEED = 123456789L;

   private FakeFactoryEnUs factory;

   @Before
   public void init() {
      factory = new FakeFactoryEnUs(new Random(SEED));
   }

   @Test
   public void testState() {
      assertThat(factory.state()).isEqualTo("Kansas");
   }

   @Test
   public void testStateAbbr() {
      assertThat(factory.stateAbbr()).isEqualTo("VI");
   }

}
