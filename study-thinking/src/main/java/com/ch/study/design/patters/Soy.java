package com.ch.study.design.patters;

public class Soy extends CondimentDecorator {
	Beverage beverage;
	public Soy(Beverage b){
		this.beverage = b;
	}
	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return beverage.getDesc()+", Soy";
	}

	@Override
	double cost() {
		// TODO Auto-generated method stub
		return 1.1+beverage.cost();
	}

}
