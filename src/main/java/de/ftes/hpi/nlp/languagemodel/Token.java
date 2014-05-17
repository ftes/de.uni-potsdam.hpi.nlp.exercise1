package de.ftes.hpi.nlp.languagemodel;

import java.util.Collections;
import java.util.Iterator;

public class Token implements PartOfSpeech {
	private final String text;
	private final String tag;
	
	public Token(String tag, String text) {
		this.text = text;
		this.tag = tag;
	}
	
	@Override
	public String toString() {
		return text;
	}
	
	@Override
	public String getTag() {
		return tag;
	}
	
	@Override
	public Iterator<Token> iterator() {
		return Collections.singleton(this).iterator();
	}

	@Override
	public Iterable<PartOfSpeech> getPartsOfSpeech() {
		return Collections.singleton((PartOfSpeech) this);
	}
}
