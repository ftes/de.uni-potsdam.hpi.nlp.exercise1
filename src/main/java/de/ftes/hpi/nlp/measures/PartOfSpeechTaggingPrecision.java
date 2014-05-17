package de.ftes.hpi.nlp.measures;

import java.util.Iterator;

import de.ftes.hpi.nlp.languagemodel.Corpus;
import de.ftes.hpi.nlp.languagemodel.PartOfSpeechTagger;
import de.ftes.hpi.nlp.languagemodel.Sentence;
import de.ftes.hpi.nlp.languagemodel.Token;

public class PartOfSpeechTaggingPrecision {
	public static double calc(PartOfSpeechTagger tagger, Corpus testSet) {
		long correct = 0;
		long incorrect = 0;
		
		for (Sentence s : testSet.getSentences()) {
			Iterable<Token> tagged = tagger.determineMostLikelyTags(s);
			Iterator<Token> testSetIter = s.iterator();
			for (Token taggedToken : tagged) {
				Token testSetToken = testSetIter.next();
				if (testSetToken.getTag().equals(taggedToken.getTag())) {
					correct++;
				} else {
					incorrect++;
				}
			}
		}
		
		return ((double) correct) / (correct + incorrect);
	}
}
