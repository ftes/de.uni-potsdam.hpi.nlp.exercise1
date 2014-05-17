package de.ftes.hpi.nlp.languagemodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Corpus {
	private final List<Sentence> sentences = new ArrayList<>();
	private long numTokens = 0;
	private long numSentences = 0;
	
	private Stack<Constituent> openConstituents = new Stack<>();
	
	public void startSentence() {
		sentences.add(new Sentence());
		numSentences++;
	}
	
	private void addPartOfSpeech(PartOfSpeech pos) {
		if (openConstituents.size() > 0) {
			openConstituents.lastElement().addPartOfSpeech(pos);
		} else {
			sentences.get(sentences.size()-1).addPartOfSpeech(pos);
		}
	}
	
	public void openConstituent(String tag) {
		Constituent newConstituent = new Constituent(tag);
		addPartOfSpeech(newConstituent);
		openConstituents.add(newConstituent);
	}
	
	public void closeConstituent() {
		openConstituents.pop();
	}
	
	public void addToken(String tag, String token) {
		addPartOfSpeech(new Token(tag, token));
		numTokens++;
	}

	public long getNumTokens() {
		return numTokens;
	}

	public long getNumSentences() {
		return numSentences;
	}
	
	@Override
	public String toString() {
		String s = "";
		for (Sentence sentence : sentences) {
			s += sentence + "\n";
		}
		return s;
	}
}
