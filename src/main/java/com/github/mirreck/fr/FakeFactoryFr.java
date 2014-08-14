package com.github.mirreck.fr;

import com.github.mirreck.FakeFactory;

import java.util.Locale;
import java.util.Random;

public class FakeFactoryFr extends FakeFactory {
   public FakeFactoryFr() {
      super(Locale.FRENCH);
   }

   public FakeFactoryFr(Random random) {
      super(Locale.FRENCH, random);
   }
   
   public String region() {
      return evaluate("address.region");
   }
}
