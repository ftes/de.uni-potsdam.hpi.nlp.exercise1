package de.ftes.hpi.nlp.util;

import de.ftes.hpi.nlp.languagemodel.Article;
import de.ftes.hpi.nlp.languagemodel.Corpus;

public class TrainingAndTestCorpus {
	private final Corpus training;
	private final Corpus test;
	
	public TrainingAndTestCorpus(Corpus corpus, double trainingFraction) {
		training = new Corpus();
		test = new Corpus();
		
		for (Article article : corpus.getArticles()) {
			if (Math.random() < trainingFraction) {
				training.addArticle(article);
			} else {
				test.addArticle(article);
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
