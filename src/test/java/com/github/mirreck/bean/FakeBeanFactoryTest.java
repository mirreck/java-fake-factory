package com.github.mirreck.bean;

import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mirreck.bean.domain.Person;

public class FakeBeanFactoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FakeBeanFactoryTest.class);

    private FakeBeanFactory<Person> factory;
    @Before
    public void setUp() throws Exception {
        factory = new FakeBeanFactory<Person>(Person.class);
    }

    @Test
    public void testFillBean() throws Exception {

        Person person = new Person();
        factory.fillBean(person);
        Assertions.assertThat(person.getEyeColor()).isNotNull();
        Assertions.assertThat(person.getJob()).isNotEmpty();
        LOGGER.info("bean :" + person);

    }
}