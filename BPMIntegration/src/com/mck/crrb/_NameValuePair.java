/**
 * 
 */
package com.mck.crrb;

import java.util.Map;

/**
 * @author akatre
 *
 */
public class _NameValuePair<K, V> implements Map.Entry<K, V>, Comparable<_NameValuePair<K, V>> {

    private K key;
    private V value;

    public _NameValuePair(K key, V value)
    {
        this.key = key;
        this.value = value;
    }

    public K getKey()
    {
        return this.key;
    }

    public V getValue()
    {
        return this.value;
    }

    public K setKey(K key)
    {
        return this.key = key;
    }

    public V setValue(V value)
    {
        return this.value = value;
    }

	@Override
	public int compareTo(_NameValuePair<K, V> arg0) {
		int comparison = -1;
		if (((String)this.key).equals((String)arg0.getKey()) && ((String)this.value).equals((String)arg0.getValue())) {
			comparison = 0;
		}
		return comparison;
	}
	
	@Override
	public String toString() {
		return "Key: " + this.key.toString() + ", Value: " + this.value.toString();
		
	}
}