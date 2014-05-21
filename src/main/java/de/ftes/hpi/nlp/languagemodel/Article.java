package de.ftes.hpi.nlp.languagemodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import com.google.common.collect.Iterables;

public class Article implements Iterable<Token> {
	private final List<Sentence> sentences = new ArrayList<>();
	
	private Stack<Constituent> openConstituents = new Stack<>();

	@Override
	public Iterator<Token> iterator() {
		return Iterables.concat(sentences).iterator();
	}
	
	public void startSentence() {
		sentences.add(new Sentence());
	}
	
	public void addSentence(Sentence sentence) {
		sentences.add(sentence);
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
	
	public void addToken(String text,String tag) {
		addPartOfSpeech(new Token(tag, text));
	}
	
	public Iterable<Sentence> getSentences() {
		return sentences;
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
