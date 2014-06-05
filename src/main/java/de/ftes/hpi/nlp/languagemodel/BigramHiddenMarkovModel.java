package de.ftes.hpi.nlp.languagemodel;

import de.ftes.hpi.nlp.util.CountingHashMap;
import de.ftes.hpi.nlp.util.Pair;

public class BigramHiddenMarkovModel implements TagLanguageModel {
	private final CountingHashMap<String> tags = new CountingHashMap<>();
	private final CountingHashMap<Pair<String, String>> tagTransitions = new CountingHashMap<>();
	private final CountingHashMap<Pair<String, String>> tagsAndTexts = new CountingHashMap<>();
	private final long numSentences;
	private final Corpus corpus;
	private final long vocabSize;
	
	public BigramHiddenMarkovModel(Corpus corpus) {
		this.corpus = corpus;
		numSentences = corpus.getNumSentences();
		vocabSize = corpus.getVocabularySize();
		
		for (Sentence sentence : corpus.getSentences()) {
			String prevTag = null;
			for (Token token : sentence) {
				String text = token.getText();
				String tag = token.getTag();
				tags.increment(tag);
				tagsAndTexts.increment(new Pair<>(tag, text));
				if (prevTag != null) {
					tagTransitions.increment(new Pair<>(prevTag, tag));
				}
				prevTag = tag;
			}
		}
	}
	
	@Override
	public double beginningOfSentenceTagProbability(String tag) {
		return (tags.get(tag) + 1.) / (numSentences + vocabSize);
	}
	
	@Override
	public double tagTransitionProbability(String first, String second) {
		return (tagTransitions.get(new Pair<>(first, second)) + 1.) / (tags.get(first) + vocabSize);
	}
	
	@Override
	public double tokenProbability(String tokenText, String givenTag) {
		return (tagsAndTexts.get(new Pair<>(givenTag, tokenText)) + 1.) / (tags.get(givenTag) + vocabSize);
	}
	
	@Override
	public Corpus getCorpus() {
		return corpus;
	}
}
