package com.github.javafaker;

import static org.fest.assertions.Assertions.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class BaseFakerTest {

   private static final String PATTERN1_AA_BB_CC = "AA##BB##CC";
   private static final String PATTERN2_AA_BB_CC = "AA??BB??CC";
   private static final String PATTERN3_AA_BB_CC = "AA##BB??CC";

   private static final long SEED = 123456789L;

   private Faker fixedSeedFaker;
   private Faker randomSeedFaker;

   @Before
   public void init() {
      fixedSeedFaker = new Faker(new Random(SEED));
      randomSeedFaker = new Faker();
   }

   @Test
   public void testDigit() {
      assertThat(fixedSeedFaker.digit()).isEqualTo('5');
      // TODO:too oftenly the same,find a more accurate test
      //assertThat(randomSeedFaker.digit()).isNotNull().isNotEqualTo(randomSeedFaker.digit());
   }

   @Test
   public void testDigits() {
      assertThat(fixedSeedFaker.digits(5)).isEqualTo("50340");
      assertThat(randomSeedFaker.digits(5)).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFaker.digits(5));
   }

   @Test
   public void testLetter() {
      assertThat(fixedSeedFaker.letter()).isEqualTo('p');
      assertThat(randomSeedFaker.letter()).isNotNull().isNotEqualTo(randomSeedFaker.letter());
   }

   @Test
   public void testLetters() {
      assertThat(fixedSeedFaker.letters(5)).isEqualTo("pclec");
      assertThat(randomSeedFaker.letters(5)).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFaker.letters(5));
   }

   @Test
   public void testNumerify() {
      assertThat(fixedSeedFaker.numerify(PATTERN1_AA_BB_CC)).isEqualTo("AA50BB34CC");
      assertThat(randomSeedFaker.numerify(PATTERN1_AA_BB_CC)).isNotNull().isNotEmpty().isNotEqualTo(
            randomSeedFaker.numerify(PATTERN1_AA_BB_CC));
   }

   @Test
   public void testLetterify() {
      assertThat(fixedSeedFaker.letterify(PATTERN2_AA_BB_CC)).isEqualTo("AApcBBleCC");
      assertThat(randomSeedFaker.letterify(PATTERN2_AA_BB_CC)).isNotNull().isNotEmpty().isNotEqualTo(
            randomSeedFaker.letterify(PATTERN2_AA_BB_CC));
   }

   @Test
   public void testBothify() {
      assertThat(fixedSeedFaker.bothify(PATTERN3_AA_BB_CC)).isEqualTo("AA50BBleCC");
      assertThat(randomSeedFaker.bothify(PATTERN3_AA_BB_CC)).isNotNull().isNotEmpty().isNotEqualTo(
            randomSeedFaker.bothify(PATTERN3_AA_BB_CC));
   }

   @Test
   public void testWordsInt() {
      assertThat(fixedSeedFaker.words(4)).containsExactly("libero", "dicta", "minus", "et");
      assertThat(randomSeedFaker.words(4)).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFaker.words(4));
   }

   @Test
   public void testWords() {
      assertThat(fixedSeedFaker.words()).containsExactly("libero", "dicta", "minus");
      assertThat(randomSeedFaker.words()).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFaker.words());
   }

   @Test
   public void testSentenceInt() {
      assertThat(fixedSeedFaker.sentence(4)).isEqualTo("Dicta minus et placeat nemo.");
      assertThat(randomSeedFaker.sentence(4)).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFaker.sentence(4));
   }

   @Test
   public void testSentence() {
      assertThat(fixedSeedFaker.sentence()).isEqualTo("Dicta minus et placeat.");
      assertThat(randomSeedFaker.sentence()).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFaker.sentence());
   }

   @Test
   public void testSentences() {
      assertThat(fixedSeedFaker.sentences(3)).containsExactly("Dicta minus et placeat.",
            "Autem libero consequuntur consectetur odit qui non.", "Magni sit vel consequuntur dolor facere et.");
      assertThat(randomSeedFaker.sentence()).isNotNull().isNotEmpty().isNotEqualTo(randomSeedFaker.sentence());
   }

   @Test
   public void testParagraphInt() {
      assertThat(fixedSeedFaker.paragraph(2)).isEqualTo(
            "Minus et placeat. Autem libero consequuntur consectetur odit qui non. Magni sit vel consequuntur dolor facere et.");
   }

   @Test
   public void testParagraph() {
      assertThat(fixedSeedFaker.paragraph()).isEqualTo(
            "Minus et placeat. Autem libero consequuntur consectetur odit qui non. Magni sit vel consequuntur dolor facere et. Sit cupiditate facere et eos.");
   }

   @Test
   public void testParagraphs() {
      assertThat(fixedSeedFaker.paragraphs(3)).containsExactly(
            "Minus et placeat. Autem libero consequuntur consectetur odit qui non. Magni sit vel consequuntur dolor facere et. Sit cupiditate facere et eos.",
            "Est omnis aut harum dolorem recusandae. Necessitatibus quaerat est quibusdam enim. Vitae culpa quos ullam.", 
            "Pariatur dolorem id nisi quibusdam quas. Eveniet dignissimos et vero laborum repellendus ut officia. Quo praesentium eum quia. Ipsa et eum recusandae consequuntur. Quibusdam suscipit beatae.");
   }

}
