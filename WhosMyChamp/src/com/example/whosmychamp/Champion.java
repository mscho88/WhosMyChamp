package com.example.whosmychamp;

import java.util.ArrayList;

import android.view.View;

public class Champion {

	private String id;
	private String name;
	private ArrayList<String> lane;//good
	private int popularity;//good
	private ArrayList<String> damage_style;//array
	private ArrayList<String> appearance;//array
	private int price;//good
	private ArrayList<String> type;//array
	private ArrayList<String> activeSkill;//good
	private ArrayList<String> passiveSkill;//good
	private int difficulty;//good
	private ArrayList<String> style;//array
	
	public Champion() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		id = id.replaceAll(" ","");
		id = id.replaceAll("['.]","");
		id = id.toLowerCase();
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
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

	public ArrayList<String> getDamage_style() {
		return damage_style;
	}

	public void setDamage_style(ArrayList<String> damage_style) {
		this.damage_style = damage_style;
	}

	public ArrayList<String> getAppearance() {
		return appearance;
	}

	public void setAppearance(ArrayList<String> appearance) {
		this.appearance = appearance;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public ArrayList<String> getType() {
		return type;
	}

	public void setType(ArrayList<String> type) {
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

	public ArrayList<String> getStyle() {
		return style;
	}

	public void setStyle(ArrayList<String> style) {
		this.style = style;
	}
}
