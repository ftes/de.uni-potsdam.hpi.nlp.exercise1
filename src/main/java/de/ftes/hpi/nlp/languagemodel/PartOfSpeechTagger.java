package de.ftes.hpi.nlp.languagemodel;

public interface PartOfSpeechTagger {
	/**
	 * Existing tags in the {@code sentence} are ignored.
	 */
	Iterable<Token> determineMostLikelyTags(Iterable<Token> sentence);
}
