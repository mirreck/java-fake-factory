package com.github.javafaker.fr;

import java.util.Locale;
import java.util.Random;

import com.github.javafaker.Faker;

public class FakerFr extends Faker {
   public FakerFr() {
      super(Locale.FRENCH);
   }

   public FakerFr(Random random) {
      super(Locale.FRENCH, random);
   }
}
