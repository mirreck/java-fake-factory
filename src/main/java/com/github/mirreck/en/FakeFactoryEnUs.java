package com.github.mirreck.en;

import java.util.Locale;
import java.util.Random;

import com.github.mirreck.FakeFactory;

public class FakeFactoryEnUs extends FakeFactory {
   public FakeFactoryEnUs() {
      super(Locale.ENGLISH);
   }

   public FakeFactoryEnUs(Random random) {
      super(Locale.ENGLISH, random);
      this.extend("en_us");
   }

   public String state() {
      return evaluate("address.state");
   }

   public String stateAbbr() {
      return evaluate("address.state_abbr");
   }
}
