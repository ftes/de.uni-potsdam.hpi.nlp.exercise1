package de.ftes.hpi.nlp.de.ftes.hpi.nlp.exercise1;

import org.junit.Test;

import de.ftes.hpi.nlp.languagemodel.BigramHiddenMarkovModel;
import de.ftes.hpi.nlp.languagemodel.Corpus;
import de.ftes.hpi.nlp.languagemodel.PartOfSpeechTagger;
import de.ftes.hpi.nlp.languagemodel.ViterbiAlgorithm;

public class PartOfSpeechTaggingTest {
	@Test
	public void tesViterbiAlg() {
		Corpus corpus = new Corpus();
		corpus.startSentence();
		corpus.addToken("There", "PR");
		corpus.addToken("are", "V");
		corpus.addToken("dogs", "O");
		corpus.startSentence();
		corpus.addToken("He", "S");
		corpus.addToken("dogs", "V");
		corpus.addToken("me", "O");
		corpus.startSentence();
		corpus.addToken("She", "S");
		corpus.addToken("like", "V");
		corpus.addToken("dogs", "V");
		
		PartOfSpeechTagger tagger = new ViterbiAlgorithm(new BigramHiddenMarkovModel(corpus));
		
		System.out.println(tagger.determineMostLikelyTags(LanguageModelTest.toSentence("I like dogs")));
	}
}
