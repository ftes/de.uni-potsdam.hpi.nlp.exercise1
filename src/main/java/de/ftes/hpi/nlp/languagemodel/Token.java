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
		return text + "(" + tag + ")";
	}
	
	@Override
	public String getTag() {
		return tag;
	}
	
	public String getText() {
		return text;
	}
	
	@Override
	public Iterator<Token> iterator() {
		return Collections.singleton(this).iterator();
	}
}
