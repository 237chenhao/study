package com.ch.study.design.patters;

public abstract class Beverage {
	private String desc="unknow";
	public String getDesc(){
		return desc;
	}
	abstract double cost();
}

