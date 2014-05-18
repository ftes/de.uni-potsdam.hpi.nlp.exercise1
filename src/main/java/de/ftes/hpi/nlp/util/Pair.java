package de.ftes.hpi.nlp.util;

import java.util.Comparator;

public class Pair<A, B> {
	public final A a;
	public final B b;
	
	public Pair(A a, B b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a == null) ? 0 : a.hashCode());
		result = prime * result + ((b == null) ? 0 : b.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		Pair<?, ?> other = (Pair<Object, Object>) obj;
		if (a == null) {
			if (other.a != null)
				return false;
		} else if (!a.equals(other.a))
			return false;
		if (b == null) {
			if (other.b != null)
				return false;
		} else if (!b.equals(other.b))
			return false;
		return true;
	}
	
	public static class PairAComparator <C extends Comparable<C>, T extends Pair<C, ?>> implements Comparator<T> {
		@Override
		public int compare(T o1, T o2) {
			return o1.a.compareTo(o2.a);
		}
	}
	
	public static class PairBComparator <C extends Comparable<C>, T extends Pair<?, C>> implements Comparator<T> {
		@Override
		public int compare(T o1, T o2) {
			return o1.b.compareTo(o2.b);
		}
	}
}
