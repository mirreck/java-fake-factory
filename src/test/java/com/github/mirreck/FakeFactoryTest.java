package com.github.mirreck;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.github.mirreck.domain.Gender;

public class FakeFactoryTest {

   private static final long SEED = 123456789L;

   private FakeFactory fixedSeedFactory;
   private FakeFactory randomSeedFactory;

   @Before
   public void init() {
      fixedSeedFactory = new FakeFactory(new Random(SEED));
      randomSeedFactory = new FakeFactory();
   }
   
   @Test
   public void testStreetName() {
      assertThat(fixedSeedFactory.streetName()).isEqualTo("Brakus River");
      assertThat(randomSeedFactory.streetName()).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFactory.streetName());

   }

   @Test
   public void testStreetAddress() {
      assertThat(fixedSeedFactory.streetAddress()).isEqualTo("34043 Olaf Motorway");
      assertThat(randomSeedFactory.streetAddress()).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFactory.streetAddress());
   }

   @Test
   public void testStreetAddressWithSecondary() {
      assertThat(fixedSeedFactory.streetAddressWithSecondary()).isEqualTo(new String[] {"34043 Olaf Motorway","Apt. 424"});
      assertThat(randomSeedFactory.streetAddressWithSecondary()).isNotNull().isNotEmpty().isNotSameAs(randomSeedFactory.streetAddressWithSecondary());
   }

   @Test
   public void testName() {
      assertThat(fixedSeedFactory.name()).isEqualTo("Ms. Karlee McCullough");
      assertThat(randomSeedFactory.name()).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFactory.name());

   }

   @Test
   public void testFirstName() {
      assertThat(fixedSeedFactory.firstName()).isEqualTo("Noel");
      assertThat(randomSeedFactory.firstName()).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFactory.firstName());

   }

   @Test
   public void testFirstNameGender() {
      assertThat(fixedSeedFactory.firstName(Gender.F)).isEqualTo("Jaleel");
      assertThat(fixedSeedFactory.firstName(Gender.M)).isEqualTo("Louvenia");
      assertThat(randomSeedFactory.firstName(Gender.F)).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFactory.firstName(Gender.F));
      assertThat(randomSeedFactory.firstName(Gender.M)).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFactory.firstName(Gender.M));

   }

   @Test
   public void testLastName() {
      assertThat(fixedSeedFactory.lastName()).isEqualTo("Wilderman");
      assertThat(randomSeedFactory.lastName()).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFactory.lastName());

   }

   @Test
   public void testNameTitle() {
      assertThat(fixedSeedFactory.nameTitle()).isEqualTo("Mr.");
      // TODO:too oftenly the same,find a more accurate test
      //assertThat(randomSeedFaker.nameTitle()).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFaker.nameTitle());

   }

   @Test
   public void testPhoneNumber() {
      assertThat(fixedSeedFactory.phoneNumber()).isEqualTo("(034)043-7771");
      assertThat(randomSeedFactory.phoneNumber()).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFactory.phoneNumber());

   }

   @Test
   public void testSecondaryAddress() {
      assertThat(fixedSeedFactory.secondaryAddress()).isEqualTo("Suite 034");
      assertThat(randomSeedFactory.secondaryAddress()).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFactory.secondaryAddress());

   }

   @Test
   public void testZipCode() {
      assertThat(fixedSeedFactory.zipCode()).isEqualTo("03404-3777");
      assertThat(randomSeedFactory.zipCode()).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFactory.zipCode());

   }

   @Test
   public void testCity() {
      assertThat(fixedSeedFactory.city()).isEqualTo("Northdenisville");
      assertThat(randomSeedFactory.city()).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFactory.city());

   }

   @Test
   public void testCountry() {
      assertThat(fixedSeedFactory.country()).isEqualTo("Turkmenistan");
      assertThat(randomSeedFactory.country()).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFactory.country());

   }

   @Test
   public void testHeight() {
      assertThat(fixedSeedFactory.height()).isEqualTo(200);
      assertThat(randomSeedFactory.height()).isNotNull().isNotEqualTo(randomSeedFactory.height());
   }

   @Test
   public void testEyeColor() {
      assertThat(fixedSeedFactory.eyeColor()).isEqualTo("brown");
      //  TODO:too oftenly the same,find a more accurate test
      //assertThat(randomSeedFaker.eyeColor()).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFaker.eyeColor());

   }

@Test
public void testFullAddress() throws Exception {
    assertThat(fixedSeedFactory.fullAddress()).containsOnly("34043 Olaf Motorway", "NewAntoninaville 90926");
}

}
