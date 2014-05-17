package de.ftes.hpi.nlp.util;


public class CountingHashMap<K> extends DefaultHashMap<K, Long> {
	private static final long serialVersionUID = -7053138431923266161L;
	
	public CountingHashMap() {
		super(new DefaultFactory<Long>() {
			@Override
			public Long getDefault() {
				return 0L;
			}
		});
	}
	
	public void increment(K key) {
		put(key, get(key) + 1);
	}
}
