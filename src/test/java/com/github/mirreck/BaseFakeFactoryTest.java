package com.github.mirreck;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Date;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class BaseFakeFactoryTest {

   private static final String PATTERN1_AA_BB_CC = "AA##BB##CC";
   private static final String PATTERN2_AA_BB_CC = "AA??BB??CC";
   private static final String PATTERN3_AA_BB_CC = "AA##BB??CC";

   private static final long SEED = 123456789L;

   private FakeFactory fixedSeedFactory;
   private FakeFactory randomSeedFactory;

   @Before
   public void init() {
      fixedSeedFactory = new FakeFactory(new Random(SEED));
      randomSeedFactory = new FakeFactory();
   }

   @Test
   public void testDigit() {
      assertThat(fixedSeedFactory.digit()).isEqualTo('5');
      // TODO:too oftenly the same,find a more accurate test
      //assertThat(randomSeedFaker.digit()).isNotNull().isNotEqualTo(randomSeedFaker.digit());
   }

   @Test
   public void testDigits() {
      assertThat(fixedSeedFactory.digits(5)).isEqualTo("50340");
      assertThat(randomSeedFactory.digits(5)).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFactory.digits(5));
   }

   @Test
   public void testLetter() {
      assertThat(fixedSeedFactory.letter()).isEqualTo('p');
      assertThat(randomSeedFactory.letter()).isNotNull().isNotEqualTo(randomSeedFactory.letter());
   }

   @Test
   public void testLetters() {
      assertThat(fixedSeedFactory.letters(5)).isEqualTo("pclec");
      assertThat(randomSeedFactory.letters(5)).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFactory.letters(5));
   }

   @Test
   public void testNumerify() {
      assertThat(fixedSeedFactory.numerify(PATTERN1_AA_BB_CC)).isEqualTo("AA50BB34CC");
      assertThat(randomSeedFactory.numerify(PATTERN1_AA_BB_CC)).isNotNull().isNotEmpty().isNotEqualTo(
            randomSeedFactory.numerify(PATTERN1_AA_BB_CC));
   }

   @Test
   public void testLetterify() {
      assertThat(fixedSeedFactory.letterify(PATTERN2_AA_BB_CC)).isEqualTo("AApcBBleCC");
      assertThat(randomSeedFactory.letterify(PATTERN2_AA_BB_CC)).isNotNull().isNotEmpty().isNotEqualTo(
            randomSeedFactory.letterify(PATTERN2_AA_BB_CC));
   }

   @Test
   public void testBothify() {
      assertThat(fixedSeedFactory.bothify(PATTERN3_AA_BB_CC)).isEqualTo("AA50BBleCC");
      assertThat(randomSeedFactory.bothify(PATTERN3_AA_BB_CC)).isNotNull().isNotEmpty().isNotEqualTo(
            randomSeedFactory.bothify(PATTERN3_AA_BB_CC));
   }

   @Test
   public void testWordsInt() {
      assertThat(fixedSeedFactory.words(4)).containsExactly("libero", "dicta", "minus", "et");
      assertThat(randomSeedFactory.words(4)).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFactory.words(4));
   }

   @Test
   public void testWords() {
      assertThat(fixedSeedFactory.words()).containsExactly("libero", "dicta", "minus");
      assertThat(randomSeedFactory.words()).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFactory.words());
   }

   @Test
   public void testSentenceInt() {
      assertThat(fixedSeedFactory.sentence(4)).isEqualTo("Dicta minus et placeat nemo.");
      assertThat(randomSeedFactory.sentence(4)).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFactory.sentence(4));
   }

   @Test
   public void testSentence() {
      assertThat(fixedSeedFactory.sentence()).isEqualTo("Dicta minus et placeat.");
      assertThat(randomSeedFactory.sentence()).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFactory.sentence());
   }

   @Test
   public void testSentences() {
      assertThat(fixedSeedFactory.sentences(3)).containsExactly("Dicta minus et placeat.",
            "Autem libero consequuntur consectetur odit qui non.", "Magni sit vel consequuntur dolor facere et.");
      assertThat(randomSeedFactory.sentence()).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFactory.sentence());
   }

   @Test
   public void testParagraphInt() {
      assertThat(fixedSeedFactory.paragraph(2)).isEqualTo(
            "Minus et placeat. Autem libero consequuntur consectetur odit qui non. Magni sit vel consequuntur dolor facere et.");
   }

   @Test
   public void testParagraph() {
      assertThat(fixedSeedFactory.paragraph()).isEqualTo(
            "Minus et placeat. Autem libero consequuntur consectetur odit qui non. Magni sit vel consequuntur dolor facere et. Sit cupiditate facere et eos.");
   }

   @Test
   public void testParagraphs() {
      assertThat(fixedSeedFactory.paragraphs(3)).containsExactly(
            "Minus et placeat. Autem libero consequuntur consectetur odit qui non. Magni sit vel consequuntur dolor facere et. Sit cupiditate facere et eos.",
            "Est omnis aut harum dolorem recusandae. Necessitatibus quaerat est quibusdam enim. Vitae culpa quos ullam.", 
            "Pariatur dolorem id nisi quibusdam quas. Eveniet dignissimos et vero laborum repellendus ut officia. Quo praesentium eum quia. Ipsa et eum recusandae consequuntur. Quibusdam suscipit beatae.");
   }
   
   @Test
   public void testCoordinates() {
      assertThat(fixedSeedFactory.coordinatesLatLng()).isEqualTo(new double[]{29.526858589007873d, -15.49735707412566d});
   }

    @Test
    public void testDate() {
        Date dt = fixedSeedFactory.evaluatePattern("{{date 1900 2010}}",Date.class);
        assertThat(dt).isEqualTo(new Date(-2044789491000L));

    }

}
