package de.ftes.hpi.nlp.languagemodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Iterables;

public class Constituent implements PartOfSpeech {
	private final String tag;
	private final List<PartOfSpeech> pos = new ArrayList<>();
	
	public Constituent(String tag) {
		this.tag = tag;
	}

	public void addPartOfSpeech(PartOfSpeech pos) {
		this.pos.add(pos);
	}

	@Override
	public String getTag() {
		return tag;
	}

	@Override
	public List<PartOfSpeech> getPartsOfSpeech() {
		return pos;
	}
	@Override
	public Iterator<Token> iterator() {
		return Iterables.concat(pos).iterator();
	}
}
