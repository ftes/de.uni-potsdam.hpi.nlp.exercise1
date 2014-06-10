package de.ftes.hpi.nlp.languagemodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.ftes.hpi.nlp.util.CountingHashMap;
import de.ftes.hpi.nlp.util.DefaultHashMap;
import de.ftes.hpi.nlp.util.DefaultHashMap.DefaultFactory;

public class BaseLineModel implements PartOfSpeechTagger {
	public Map<String, CountingHashMap<String>> tokenToTagOccurences = new DefaultHashMap<>(
			new DefaultFactory<CountingHashMap<String>>() {
				@Override
				public CountingHashMap<String> getDefault() {
					return new CountingHashMap<>();
				}
			});
	
	public BaseLineModel(Corpus corpus) {
		for (Token token : corpus) {
			tokenToTagOccurences.get(token.getText()).increment(token.getTag());
		}
	}

	@Override
	public Iterable<Token> determineMostLikelyTags(Iterable<Token> sentence) {
		List<Token> result = new ArrayList<>();
		for (Token oldToken : sentence) {
			Map<String, Long> tagCounts = tokenToTagOccurences.get(oldToken.getText());
			String newTag;
			if (tagCounts.size() == 0) {
				newTag = "NN";
			} else {
				List<Entry<String, Long>> list = new LinkedList<>(tagCounts.entrySet());
				// Defined Custom Comparator here
				Collections.sort(list, new Comparator<Entry<String, Long>>() {
					public int compare(Entry<String, Long> o1, Entry<String, Long> o2) {
						return o2.getValue().compareTo(o1.getValue());
					}
				});
				newTag = list.get(0).getKey();
			}
			result.add(new Token(newTag, oldToken.getText()));
		}
		return result;
	}

}
