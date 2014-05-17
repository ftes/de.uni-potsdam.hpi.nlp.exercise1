package de.ftes.hpi.nlp.util;

import de.ftes.hpi.nlp.languagemodel.Corpus;
import de.ftes.hpi.nlp.languagemodel.Sentence;

public class TrainingAndTestCorpus {
	private final Corpus training;
	private final Corpus test;
	
	public TrainingAndTestCorpus(Corpus corpus, double trainingFraction) {
		training = new Corpus();
		test = new Corpus();
		
		for (Sentence sentence : corpus.getSentences()) {
			if (Math.random() < trainingFraction) {
				training.addSentence(sentence);
			} else {
				test.addSentence(sentence);
			}
		}
	}

	public Corpus trainingSet() {
		return training;
	}

	public Corpus testSet() {
		return test;
	}	
}
