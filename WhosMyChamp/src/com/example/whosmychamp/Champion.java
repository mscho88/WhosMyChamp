package com.example.whosmychamp;

import java.util.ArrayList;

public class Champion {

	private char name;
	private ArrayList<String> lane;
	private int popularity;
	private char damage_style;
	private char appearance;
	private int price;
	private char type;
	private ArrayList<String> activeSkill;
	private ArrayList<String> passiveSkill;
	private int difficulty;
	
	public Champion() {
		// TODO Auto-generated constructor stub
	}

	public char getName() {
		return name;
	}

	public void setName(char name) {
		this.name = name;
	}

	public ArrayList<String> getLane() {
		return lane;
	}

	public void setLane(ArrayList<String> lane) {
		this.lane = lane;
	}

	public int getPopularity() {
		return popularity;
	}

	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}

	public char getDamage_style() {
		return damage_style;
	}

	public void setDamage_style(char damage_style) {
		this.damage_style = damage_style;
	}

	public char getAppearance() {
		return appearance;
	}

	public void setAppearance(char appearance) {
		this.appearance = appearance;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}

	public ArrayList<String> getActiveSkill() {
		return activeSkill;
	}

	public void setActiveSkill(ArrayList<String> activeSkill) {
		this.activeSkill = activeSkill;
	}

	public ArrayList<String> getPassiveSkill() {
		return passiveSkill;
	}

	public void setPassiveSkill(ArrayList<String> passiveSkill) {
		this.passiveSkill = passiveSkill;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
}
