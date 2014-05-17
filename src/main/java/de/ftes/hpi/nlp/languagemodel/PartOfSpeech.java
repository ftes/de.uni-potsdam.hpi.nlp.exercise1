package de.ftes.hpi.nlp.languagemodel;


public interface PartOfSpeech extends Iterable<Token> {
	String getTag();
}
