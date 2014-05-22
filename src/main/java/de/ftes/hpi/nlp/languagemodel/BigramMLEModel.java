package de.ftes.hpi.nlp.languagemodel;

import de.ftes.hpi.nlp.util.CountingHashMap;
import de.ftes.hpi.nlp.util.Pair;

public class BigramMLEModel implements TokenLanguageModel {
	private final CountingHashMap<String> unigrams = new CountingHashMap<>();
	private final CountingHashMap<Pair<String, String>> bigrams = new CountingHashMap<>();
	private final long numSentences;
	private final long vocabSize;
	
	public BigramMLEModel(Corpus corpus) {
		numSentences = corpus.getNumSentences();
		vocabSize = corpus.getVocabularySize();
		
		for (Sentence sentence : corpus.getSentences()) {
			String prevText = null;
			for (Token token : sentence) {
				String text = token.getText().toLowerCase();
				unigrams.increment(text);
				if (prevText != null) {
					bigrams.increment(new Pair<>(prevText, text));
				}
				prevText = text;
			}
		}
	}

	@Override
	public double wordProbabilityAtBeginningOfSentence(String text) {
		return (unigrams.get(text) + 1.) / (numSentences + vocabSize);
	}

	@Override
	public double wordProbabilitiyGivenPrevious(String first, String second) {
		return (bigrams.get(new Pair<>(first, second)) + 1.) / (unigrams.get(first) + vocabSize);
	}

	@Override
	public double sentenceProbabilityLogarithm(Sentence sentence) {
		String prevText = null;
		double sum = 0;
		for (Token token : sentence) {
			String text = token.getText().toLowerCase();
			if (prevText == null) {
				sum += Math.log(wordProbabilityAtBeginningOfSentence(text));
			} else {
				sum += Math.log(wordProbabilitiyGivenPrevious(prevText, text));
			}
			prevText = text;
		}
		return sum;
	}
}
