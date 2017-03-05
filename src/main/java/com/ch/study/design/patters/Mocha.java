package com.ch.study.design.patters;

public class Mocha extends CondimentDecorator{
	Beverage beverage;
	public Mocha(Beverage b){
		this.beverage = b;
	}
	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return beverage.getDesc()+",摩可";
	}

	@Override
	double cost() {
		// TODO Auto-generated method stub
		return 0.89 + beverage.cost();
	}

}
