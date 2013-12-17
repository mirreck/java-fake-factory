package com.github.javafaker.en;

import java.util.Locale;
import java.util.Random;

import com.github.javafaker.Faker;

public class FakerEn extends Faker {
   public FakerEn() {
      super(Locale.ENGLISH);
   }

   public FakerEn(Random random) {
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
