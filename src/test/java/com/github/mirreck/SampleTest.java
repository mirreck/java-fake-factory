package com.github.mirreck;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.fest.util.Strings;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mirreck.domain.Gender;

public class SampleTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleTest.class);

    private static final long SEED = 2;

    @Test
    public void simpleExample() {
        FakeFactory factory = new FakeFactory();

        //Basic
        char digit = factory.digit(); // Example : 7
        String digits = factory.digits(6); // Example : 257909
        Date date = factory.date(2000, 2010); // Example : Sun Jun 17 15:04:55 CEST 2007
        
        //Name
        String firstName = factory.firstName(); // Example : Henderson
        String firstNameF = factory.firstName(Gender.F); // Example : Janis
        String lastName = factory.lastName(); // Example : Renner
        String name = factory.name(); // Example : Jacey Barton
        String nameTitle = factory.nameTitle(); // Example : Miss
        
        // Address
        String streetName = factory.streetName(); // Example : 
        String streetAddress = factory.streetAddress(); // Example : 54253 Ledner Highway
        String secondaryAddress = factory.secondaryAddress(); // Example : Suite 385
        String[] streetAddressWithSecondary = factory.streetAddressWithSecondary(); // Example : ["0703 Feil Cove" , "Apt. 350"]
        String city = factory.city(); // Example : Newavaborough
        String zipCode = factory.zipCode(); // Example : 49125-1628
        String country = factory.country(); // Example : United States
        String[] fullAddress = factory.fullAddress(); // Example : [58120 Madisyn Route, SouthShyanneville 90643]
        String phoneNumber = factory.phoneNumber(); // Example : 823-668-6845 x74332
        double[] coordinatesLatLng = factory.coordinatesLatLng(); // Example :  [-0.6698821091060267, -53.76053391427611]

        // Text
        char letter = factory.letter(); // Example : r
        String letters = factory.letters(10); // Example : oqojdzgqkg
        String paragraph = factory.paragraph(); // Example : 
        String paragraph2 = factory.paragraph(3); // Example : 
        List<String> paragraphs = factory.paragraphs(2); // Example : 
        String sentence = factory.sentence(); // Example : 
        String sentence2 = factory.sentence(5); // Example : 
        List<String> sentences = factory.sentences(3); // Example : 
        List<String> words = factory.words(); // Example : 
        List<String> words2 = factory.words(5); // Example : 
        
        // Appearance
        int height = factory.height(); // Example : 156
        String eyeColor = factory.eyeColor(); // Example : black
        
        
        LOGGER.info("digit : {}", digit);
        LOGGER.info("digits : {}", digits);
        LOGGER.info("date : {}", date);
        
        LOGGER.info("firstName : {}", firstName);
        LOGGER.info("firstNameF : {}", firstNameF);
        LOGGER.info("lastName : {}", lastName);
        LOGGER.info("name : {}", name);
        LOGGER.info("nameTitle : {}", nameTitle);
        
        LOGGER.info("streetName : {}", streetName);
        LOGGER.info("streetAddress : {}", streetAddress);
        LOGGER.info("secondaryAddress : {}", secondaryAddress);
        LOGGER.info("streetAddressWithSecondary : {}", streetAddressWithSecondary);
        LOGGER.info("city : {}", city);
        LOGGER.info("zipCode : {}", zipCode);
        LOGGER.info("country : {}", country);
        LOGGER.info("fullAddress : {}", Strings.join(fullAddress).with(", "));
        LOGGER.info("phoneNumber : {}", phoneNumber);
        LOGGER.info("coordinatesLatLng : {}", coordinatesLatLng);
        
        LOGGER.info("letter : {}", letter);
        LOGGER.info("letters : {}", letters);
        
        LOGGER.info("height : {}", height);
        LOGGER.info("eyeColor : {}", eyeColor);
        
        
    }

    @Test
    public void fixedSeedExample() {

        FakeFactory fixedSeedFactory = new FakeFactory(new Random(SEED));

        String firstName = fixedSeedFactory.firstName();

        LOGGER.info("firstName : {}", firstName);
    }

}
