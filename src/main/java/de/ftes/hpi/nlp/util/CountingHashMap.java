package de.ftes.hpi.nlp.util;

import java.util.HashMap;

public class CountingHashMap<K> extends HashMap<K, Long> {
	private static final long serialVersionUID = -7053138431923266161L;

	@Override
	public Long get(Object key) {
		if (! containsKey(key)) {
			return 0L;
		}
		return super.get(key);
	}
	
	public void increment(K key) {
		put(key, get(key) + 1);
	}
}
