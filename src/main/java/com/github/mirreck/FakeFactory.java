package com.github.mirreck;

import java.util.Locale;
import java.util.Random;

import org.apache.commons.lang.WordUtils;

import com.github.mirreck.domain.Gender;

public class FakeFactory extends BaseFakeFactory {

    private static final String ADDRESS_SECONDARY_ADDRESS = "address.secondary_address";
    private static final String ADDRESS_STREET_ADDRESS = "address.street_address";

    public FakeFactory() {
        super(Locale.ENGLISH);
    }

    public FakeFactory(Random random) {
        super(Locale.ENGLISH, random);
    }

    public FakeFactory(Locale locale) {
        super(locale);
    }

    public FakeFactory(Locale locale, Random random) {
        super(locale, random);
    }

    public String name() {
        return evaluate("name.formats");
    }

    public String firstName() {
        return evaluate("name.first_name");
    }

    public String firstName(Gender gender) {
        if(Gender.F.equals(gender)){
            return evaluate("name.female_first_name");
        } else {
            return evaluate("name.male_first_name");
        }
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

    public String streetName() {
        return evaluate("address.street_name.formats");
    }

    public String streetAddress() {
        return evaluate(ADDRESS_STREET_ADDRESS);
    }

    public String secondaryAddress() {
        return evaluate(ADDRESS_SECONDARY_ADDRESS);
    }

    public String[] streetAddressWithSecondary() {
        return new String[] { evaluate(ADDRESS_STREET_ADDRESS), evaluate(ADDRESS_SECONDARY_ADDRESS) };
    }

    public String zipCode() {
        return evaluate("address.postcode");
    }

    public String city() {
        return  WordUtils.capitalizeFully(evaluate("city.formats"));
    }

    public String country() {
        return evaluate("address.country");
    }

    public int height() {
        return RandomUtils.gaussianInt(random,fetchInteger("measurements.height.min"), fetchInteger("measurements.height.max"));
    }

    public String eyeColor() {
        return evaluate("measurements.eye_color");
    }

    public String[] fullAddress() {
        return new String[] { evaluate("address.line1"), evaluate("address.line2") };
    }
}
