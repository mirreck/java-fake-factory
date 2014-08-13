package com.github.mirreck.bean;

import static org.fest.assertions.Assertions.*;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mirreck.bean.domain.Person;

public class FakeBeanBuilderTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(FakeBeanBuilderTest.class);

	
	@Test
	public void should_build_with_given_configuration() {
		FakeBeanBuilder<Person> fbb = new FakeBeanBuilder<Person>(Person.class)
			.withParameterPattern("job", "abc")
            .withParameterPattern("birthDate", "{{date 1900 2010}}")
            .withParameterSequence("id");
		
		Person person = fbb.build();
		assertThat(person.getId()).isNotNull().isPositive();
        assertThat(person.getJob()).isNotEmpty();
        assertThat(person.getBirthDate()).isNotNull();
        LOGGER.info("bean :" + person);
	}

    @Test
    public void should_build_with_default_file_configuration() {
        FakeBeanBuilder<Person> fbb = new FakeBeanBuilder<Person>(Person.class)
                .initWithConfigurationFile();

        Person person = fbb.build();
        assertThat(person.getId()).isNotNull().isPositive();
        assertThat(person.getJob()).isNotEmpty();
        assertThat(person.getBirthDate()).isNotNull();
        LOGGER.info("bean :" + person);
    }

    @Test
    public void should_build_with_another_file_configuration() {
        FakeBeanBuilder<Person> fbb = new FakeBeanBuilder<Person>(Person.class)
                .initWithConfigurationFile("/conf/fake-factory/another_cfg.yml");

        Person person = fbb.build();
        assertThat(person.getId()).isNotNull().isPositive();
        assertThat(person.getJob()).isNotEmpty();
        assertThat(person.getBirthDate()).isNotNull();
        LOGGER.info("bean :" + person);
    }

    @Test
    @Ignore("not working yet...")
    public void should_build_with_guessed_configuration() {
        FakeBeanBuilder<Person> fbb = new FakeBeanBuilder<Person>(Person.class)
                .withGuessedFillers();

        Person person = fbb.build();
        assertThat(person.getId()).isNotNull().isPositive();
        assertThat(person.getJob()).isNotEmpty();
        assertThat(person.getBirthDate()).isNotNull();
        LOGGER.info("bean :" + person);
    }

}
