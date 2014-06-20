Java Fake Factory
======

inspired by the Ruby library Faker

## Basic Example


```java
FakeFactory factory = new FakeFactory();

String streetName = factory.streetName(); // Celestino Street
String city = factory.city(); // Lakewilfordfurt
double[] coordinatesLatLng = factory.coordinatesLatLng(); // [-0.6698821091060267, -53.76053391427611]

```


Based On
--------
Initially forked from [Java java-faker](https://github.com/DiUS/java-faker)
and Inspired by
- [Ruby Faker](https://github.com/stympy/faker)
- [Python faker](https://github.com/joke2k/faker)
- [Javascript Faker.js](https://github.com/Marak/Faker.js)
- [PHP Faker](https://github.com/fzaninotto/Faker)