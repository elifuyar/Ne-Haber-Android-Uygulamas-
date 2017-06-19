package com.example.user.nehaber.models;

import java.util.LinkedList;

public class LimitedQueue<E> extends LinkedList<E> {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5511902997291600522L;
	
	private int limit;

    public LimitedQueue(int limit) {
        this.limit = limit;
    }

    @Override
    public boolean add(E o) {
        super.add(o);
        while (size() > limit) { super.remove(); }
        return true;
    }
}