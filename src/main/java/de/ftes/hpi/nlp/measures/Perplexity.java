package de.ftes.hpi.nlp.measures;

import de.ftes.hpi.nlp.languagemodel.Corpus;
import de.ftes.hpi.nlp.languagemodel.LanguageModel;
import de.ftes.hpi.nlp.languagemodel.Sentence;

/**
 * Perplexity ({@link http://www.cs.columbia.edu/~mcollins/lm-spring2013.pdf}):
 * 2^{-{{sum_{i=1}^m {log_2 {p(x_i)}}}/M}}
 * 
 * M: number of words
 * x_i: sentence
 * m: number of sentences
 * @author fredrik
 *
 */
public class Perplexity {
	public static double calc(LanguageModel model, Corpus corpus) {
		long M = corpus.getNumTokens();
		
		double sum = 0;
		for (Sentence s : corpus.getSentences()) {
			sum += model.sentenceProbabilityLogarithm(s);
		}
		
		return Math.pow(2, - sum / M);
	}
}
