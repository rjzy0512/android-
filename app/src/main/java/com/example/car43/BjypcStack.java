package com.example.car43;

import java.util.ArrayList;
import java.util.List;

public class BjypcStack {
	  private List<String> l;
	    private int size;
	    public String top;

	    public BjypcStack() {
	        l = new ArrayList<String>();
	        size = 0;
	        top = null;
	    }

	    public int size() {
	        return size;
	    }

	    public void push(String s) {
	        l.add(s);
	        top = s;
	        size++;
	    }

	    public String pop() {
	        String s = l.get(size - 1);
	        l.remove(size - 1);
	        size--;
	        top = size == 0 ? null : l.get(size - 1);
	        return s;
	    }
}
