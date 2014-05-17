package de.ftes.hpi.nlp.languagemodel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import com.google.common.collect.Iterables;

public class Corpus implements Iterable<Token> {	
	private final List<Sentence> sentences = new ArrayList<>();
	private Set<String> vocabulary = new HashSet<>();
	private long numSentences = 0;
	private long numTokens = 0;
	
	private Stack<Constituent> openConstituents = new Stack<>();
	
	public void addSentence(Sentence sentence) {
		sentences.add(sentence);
		for (Token token : sentence) {
			vocabulary.add(token.getText());
			numTokens++;
		}
		numSentences++;
	}
	
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
	
	public void addToken(String tag, String text) {
		addPartOfSpeech(new Token(tag, text));
		vocabulary.add(text);
		numTokens++;
	}

	public long getVocabularySize() {
		return vocabulary.size();
	}

	public long getNumSentences() {
		return numSentences;
	}
	
	public long getNumTokens() {
		return numTokens;
	}
	
	@Override
	public String toString() {
		String s = "";
		for (Sentence sentence : sentences) {
			s += sentence + "\n";
		}
		return s;
	}

	public List<Sentence> getSentences() {
		return sentences;
	}

	@Override
	public Iterator<Token> iterator() {
		return Iterables.concat(sentences).iterator();
	}
}
