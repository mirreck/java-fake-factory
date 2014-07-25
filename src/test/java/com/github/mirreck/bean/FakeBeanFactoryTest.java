package com.github.mirreck.bean;

import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.fest.assertions.Assertions.assertThat;

public class FakeBeanFactoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FakeBeanFactoryTest.class);

    private FakeBeanFactory factory;
    @Before
    public void setUp() throws Exception {
        factory = new FakeBeanFactory<TestBean>(TestBean.class);
    }

    @Test
    public void testFillBean() throws Exception {

        TestBean bean = new TestBean();
        factory.fillBean(bean);
        Assertions.assertThat(bean.getColor()).isNotNull();
        Assertions.assertThat(bean.getStr()).isNotEmpty();
        LOGGER.info("bean :" + bean);

    }
}