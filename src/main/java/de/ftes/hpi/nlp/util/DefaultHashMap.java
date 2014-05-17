package de.ftes.hpi.nlp.util;

import java.util.HashMap;

public class DefaultHashMap<K, V> extends HashMap<K, V> {
	public static interface DefaultFactory<V> {
		V getDefault();
	}
	private static final long serialVersionUID = 6884670367961256942L;
	
	private final DefaultFactory<V> factory;
	
	public DefaultHashMap(DefaultFactory<V> factory) {
		this.factory = factory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public V get(Object key) {
		if (! containsKey(key)) {
			V defaultValue = factory.getDefault();
			put((K) key, defaultValue);
		}
		return super.get(key);
	}
}
