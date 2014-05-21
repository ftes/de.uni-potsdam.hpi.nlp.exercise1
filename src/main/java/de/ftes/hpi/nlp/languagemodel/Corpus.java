package de.ftes.hpi.nlp.languagemodel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Iterables;

public class Corpus implements Iterable<Token> {
	private final List<Article> articles = new ArrayList<>();
	private final List<Iterable<Sentence>> sentences = new ArrayList<>();
	private Set<String> vocabulary = new HashSet<>();
	private long numSentences = 0;
	private long numTokens = 0;
	private Set<String> tags = new HashSet<>();
	
	public void addSentence(Sentence sentence) {
		articles.get(articles.size()-1).addSentence(sentence);
		for (Token token : sentence) {
			tokenAdded(token.getText(), token.getTag());
		}
		numSentences++;
	}
	
	private void tokenAdded(String text, String tag) {
		vocabulary.add(text);
		tags.add(tag);
		numTokens++;
	}
	
	public void startSentence() {
		articles.get(articles.size()-1).startSentence();
	}
	
	public void startArticle() {
		articles.add(new Article());
		sentences.add(articles.get(articles.size()-1).getSentences());
		numSentences++;
	}
	
	public void openConstituent(String tag) {
		articles.get(articles.size()-1).openConstituent(tag);
	}
	
	public void closeConstituent() {
		articles.get(articles.size()-1).closeConstituent();
	}
	
	public void addToken(String text, String tag) {
		articles.get(articles.size()-1).addToken(text, tag);
		tokenAdded(text, tag);
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
	
	public Set<String> getTagSet() {
		return tags;
	}
	
	@Override
	public String toString() {
		String s = "";
		for (Article article : articles) {
			s += article + "\n";
		}
		return s;
	}

	public Iterable<Sentence> getSentences() {
		return Iterables.concat(sentences);
	}

	@Override
	public Iterator<Token> iterator() {
		return Iterables.concat(articles).iterator();
	}
	
	public Iterable<Article> getArticles() {
		return articles;
	}
	
	public void addArticle(Article article) {
		startArticle();
		for (Sentence sentence : article.getSentences()) {
			addSentence(sentence);
		}
	}
}
