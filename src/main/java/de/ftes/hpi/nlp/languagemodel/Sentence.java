package de.ftes.hpi.nlp.languagemodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Iterables;

public class Sentence implements Iterable<Token> {
	private final List<PartOfSpeech> pos = new ArrayList<>();
	
	public void addPartOfSpeech(PartOfSpeech partOfSpeech) {
		pos.add(partOfSpeech);
	}

	@Override
	public Iterator<Token> iterator() {
		return Iterables.concat(pos).iterator();
	}
	
	@Override
	public String toString() {
		String s = "";
		for (Token token : this) {
			s += token + " ";
		}
		return s;
	}
}
