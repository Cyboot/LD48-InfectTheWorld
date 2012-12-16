package de.timweb.ld48.villain.game;

public class Player {
	private static double money = 1000;
	
	
	
	public static void addMoneyDelta(int delta){
		money += delta * 0.001;
	}
	
	public static void addMoney(int money){
		Player.money += money;
	}
	
	public static void removeMoney(int money){
		Player.money -= money;
	}
	
	public static int getMoney(){
		return (int) money;
	}
	
}