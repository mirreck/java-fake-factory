package com.github.mirreck.bean;

import static org.fest.assertions.Assertions.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mirreck.bean.domain.Person;

public class FakeBeanBuilderTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(FakeBeanBuilderTest.class);

	
	@Test
	public void test() {
		FakeBeanBuilder<Person> fbb = FakeBeanBuilder.forClass(Person.class)
			.withParameterPattern("job", "abc")
            .withParameterPattern("birthDate", "{{date 1900 2010}}")
            .withParameterSequence("id");
		
		Person person = fbb.build();
		assertThat(person.getId()).isNotNull().isPositive();
        assertThat(person.getJob()).isNotEmpty();
        assertThat(person.getBirthDate()).isNotNull();
        LOGGER.info("bean :" + person);
	}

}
