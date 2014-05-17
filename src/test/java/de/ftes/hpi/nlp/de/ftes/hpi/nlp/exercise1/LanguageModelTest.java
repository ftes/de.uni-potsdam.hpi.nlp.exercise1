package de.ftes.hpi.nlp.de.ftes.hpi.nlp.exercise1;

import org.junit.Test;

import de.ftes.hpi.nlp.languagemodel.BigramMLEModel;
import de.ftes.hpi.nlp.languagemodel.Corpus;
import de.ftes.hpi.nlp.languagemodel.TokenLanguageModel;
import de.ftes.hpi.nlp.languagemodel.Sentence;
import de.ftes.hpi.nlp.languagemodel.Token;

public class LanguageModelTest {
	public static Sentence toSentence(String sentence) {
		Sentence s = new Sentence();
		for (String word : sentence.split(" ")) {
			s.addPartOfSpeech(new Token("", word));
		}
		return s;
	}
	
	public static Corpus toCorpus(String... sentences) {
		Corpus corpus = new Corpus();
		for (String sentence : sentences) {
			corpus.addSentence(toSentence(sentence));
		}
		return corpus;
	}

	@Test
	public void testLanguageModel() {
		Corpus corpus = toCorpus("I saw the boy", "the man is working", "I walked in the street");
		TokenLanguageModel lm = new BigramMLEModel(corpus);
		
		System.out.println(lm.sentenceProbabilityLogarithm(toSentence("I saw the man")));
		System.out.println(lm.sentenceProbabilityLogarithm(toSentence("I saw the woman")));
	}
}
