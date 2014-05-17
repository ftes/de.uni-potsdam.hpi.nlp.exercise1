package de.ftes.hpi.nlp.languagemodel;

public interface TagLanguageModel {
	Corpus getCorpus();
	double beginningOfSentenceTagProbability(String tag);
	double tagTransitionProbability(String first, String second);
	double tokenProbability(String tokenText, String givenTag);
}
