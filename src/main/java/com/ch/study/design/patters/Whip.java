package com.ch.study.design.patters;

public class Whip extends CondimentDecorator {
	Beverage beverage;
	public Whip(Beverage b){
		this.beverage = b;
	}
	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return beverage.getDesc()+", Whip";
	}

	@Override
	double cost() {
		// TODO Auto-generated method stub
		return 0.56+beverage.cost();
	}

}
