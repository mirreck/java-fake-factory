package com.github.javafaker;

import static org.fest.assertions.Assertions.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.github.javafaker.domain.Gender;

public class FakerTest {

   private static final long SEED = 123456789L;

   private Faker fixedSeedFaker;
   private Faker randomSeedFaker;

   @Before
   public void init() {
      fixedSeedFaker = new Faker(new Random(SEED));
      randomSeedFaker = new Faker();
   }
   
   @Test
   public void testStreetName() {
      assertThat(fixedSeedFaker.streetName()).isEqualTo("Brakus River");
      assertThat(randomSeedFaker.streetName()).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFaker.streetName());

   }

   @Test
   public void testStreetAddress() {
      assertThat(fixedSeedFaker.streetAddress()).isEqualTo("34043 Olaf Motorway");
      assertThat(randomSeedFaker.streetAddress()).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFaker.streetAddress());
   }

   @Test
   public void testStreetAddressWithSecondary() {
      assertThat(fixedSeedFaker.streetAddressWithSecondary()).isEqualTo(new String[] {"34043 Olaf Motorway","Apt. 424"});
      assertThat(randomSeedFaker.streetAddressWithSecondary()).isNotNull().isNotEmpty().isNotSameAs(randomSeedFaker.streetAddressWithSecondary());
   }

   @Test
   public void testName() {
      assertThat(fixedSeedFaker.name()).isEqualTo("Ms. Karlee McCullough");
      assertThat(randomSeedFaker.name()).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFaker.name());

   }

   @Test
   public void testFirstName() {
      assertThat(fixedSeedFaker.firstName()).isEqualTo("Noel");
      assertThat(randomSeedFaker.firstName()).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFaker.firstName());

   }

   @Test
   public void testFirstNameGender() {
      assertThat(fixedSeedFaker.firstName(Gender.F)).isEqualTo("Jaleel");
      assertThat(fixedSeedFaker.firstName(Gender.M)).isEqualTo("Louvenia");
      assertThat(randomSeedFaker.firstName(Gender.F)).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFaker.firstName(Gender.F));
      assertThat(randomSeedFaker.firstName(Gender.M)).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFaker.firstName(Gender.M));

   }

   @Test
   public void testLastName() {
      assertThat(fixedSeedFaker.lastName()).isEqualTo("Wilderman");
      assertThat(randomSeedFaker.lastName()).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFaker.lastName());

   }

   @Test
   public void testNameTitle() {
      assertThat(fixedSeedFaker.nameTitle()).isEqualTo("Mr.");
      // TODO:too oftenly the same,find a more accurate test
      //assertThat(randomSeedFaker.nameTitle()).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFaker.nameTitle());

   }

   @Test
   public void testPhoneNumber() {
      assertThat(fixedSeedFaker.phoneNumber()).isEqualTo("(034)043-7771");
      assertThat(randomSeedFaker.phoneNumber()).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFaker.phoneNumber());

   }

   @Test
   public void testSecondaryAddress() {
      assertThat(fixedSeedFaker.secondaryAddress()).isEqualTo("Suite 034");
      assertThat(randomSeedFaker.secondaryAddress()).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFaker.secondaryAddress());

   }

   @Test
   public void testZipCode() {
      assertThat(fixedSeedFaker.zipCode()).isEqualTo("03404-3777");
      assertThat(randomSeedFaker.zipCode()).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFaker.zipCode());

   }

   @Test
   public void testCity() {
      assertThat(fixedSeedFaker.city()).isEqualTo("Northdenisville");
      assertThat(randomSeedFaker.city()).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFaker.city());

   }

   @Test
   public void testCountry() {
      assertThat(fixedSeedFaker.country()).isEqualTo("Turkmenistan");
      assertThat(randomSeedFaker.country()).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFaker.country());

   }

   @Test
   public void testHeight() {
      assertThat(fixedSeedFaker.height()).isEqualTo(200);
      assertThat(randomSeedFaker.height()).isNotNull().isNotEqualTo(randomSeedFaker.height());
   }

   @Test
   public void testEyeColor() {
      assertThat(fixedSeedFaker.eyeColor()).isEqualTo("brown");
      //  TODO:too oftenly the same,find a more accurate test
      //assertThat(randomSeedFaker.eyeColor()).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFaker.eyeColor());

   }

}
