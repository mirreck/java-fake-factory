Java Fake Factory
======

inspired by the Ruby library Faker

## Basic Usage


```java
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

```

## Fixed Random Seed


```java
private static final long SEED = 123456789L;
...
FakeFactory fixedSeedFactory = new FakeFactory(new Random(SEED));
String firstName = fixedSeedFactory.firstName();
```

## Extend Fake Factory

Extend the FakeFactory class:
```java
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
```
and the configuration file:
```yaml
# US-specific values
en:
  faker:
    address:
      line2: '{{city.formats}}, {{address.state_abbr}} {{address.postcode}}'
      state: [Alabama, Alaska, Arizona, Arkansas, California, Colorado, Connecticut, Delaware, Florida, Georgia, Hawaii, Idaho, Illinois, Indiana, Iowa, Kansas, Kentucky, Louisiana, Maine, Maryland, Massachusetts, Michigan, Minnesota, Mississippi, Missouri, Montana, Nebraska, Nevada, New Hampshire, New Jersey, New Mexico, New York, North Carolina, North Dakota, Ohio, Oklahoma, Oregon, Pennsylvania, Rhode Island, South Carolina, South Dakota, Tennessee, Texas, Utah, Vermont, Virginia, Washington, West Virginia, Wisconsin, Wyoming]
      state_abbr: [AL, AK, AS, AZ, AR, CA, CO, CT, DE, DC, FM, FL, GA, GU, HI, ID, IL, IN, IA, KS, KY, LA, ME, MH, MD, MA, MI, MN, MS, MO, MT, NE, NV, NH, NJ, NM, NY, NC, ND, MP, OH, OK, OR, PW, PA, PR, RI, SC, SD, TN, TX, UT, VT, VI, VA, WA, WV, WI, WY, AE, AA, AP]
```


Based On
--------
Initially forked from [Java java-faker](https://github.com/DiUS/java-faker)
and Inspired by
- [Ruby Faker](https://github.com/stympy/faker)
- [Python faker](https://github.com/joke2k/faker)
- [Javascript Faker.js](https://github.com/Marak/Faker.js)
- [PHP Faker](https://github.com/fzaninotto/Faker)