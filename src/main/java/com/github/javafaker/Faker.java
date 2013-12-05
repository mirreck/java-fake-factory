package com.github.javafaker;

import java.util.Locale;
import java.util.Random;

import com.github.javafaker.domain.Gender;


public class Faker extends BaseFaker {

   public Faker() {
      super(Locale.ENGLISH);
   }

   public Faker(Random random) {
      super(Locale.ENGLISH, random);
   }

   public Faker(Locale locale) {
      super(locale);
   }

   public Faker(Locale locale, Random random) {
      super(locale, random);
   }

   public String streetName() {
      return evaluate("address.street_name.formats");
   }

   public String streetAddress() {
      return evaluate("address.street_address");
   }

   public String[] streetAddressWithSecondary() {
      return new String[] { evaluate("address.street_address"), secondaryAddress()};
   }

   public String name() {
      return evaluate("name.formats");
   }

   public String firstName() {
      return evaluate("name.first_name");
   }

   public String firstName(Gender gender) {
      switch (gender) {
         case M:
            return evaluate("name.male_first_name");
         case F:
            return evaluate("name.female_first_name");
      }
      // should not happen
      return null;
   }

   public String lastName() {
      return evaluate("name.last_name");
   }

   public String nameTitle() {
      return evaluate("name.title");
   }

   public String phoneNumber() {
      return evaluate("phone_number.formats");
   }

   public String secondaryAddress() {
      return evaluate("address.secondary_address");
   }

   public String zipCode() {
      return evaluate("address.postcode");
   }

   public String city() {
      return capitalizeFully(evaluate("city.formats"));
   }

   public String country() {
      return evaluate("address.country");
   }

   public int height() {
      return randomGaussianInt(fetchInteger("measurements.height.min"), fetchInteger("measurements.height.max"));
   }

   public String eyeColor() {
      return evaluate("measurements.eye_color");
   }
}
