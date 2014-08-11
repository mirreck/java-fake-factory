package com.github.mirreck.bean.fill;

import java.lang.reflect.Method;

public class SequenceFiller<T> extends AbstractFiller<T>{

	public SequenceFiller(Method writerMethod) {
		super(writerMethod);
		// TODO Auto-generated constructor stub
	}

	private Long index = 1L;
	
	@Override
	protected String generateValue() {
		index++;
		return index.toString();
	}

	
	


}
