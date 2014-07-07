package com.example.whosmychamp;

import java.util.ArrayList;

import android.view.View;

public class Champion {

	private String name;
	//private String address;
	private ArrayList<String> lane;
	private int popularity;
	private String damage_style;
	private String appearance;
	private int price;
	private String type;
	private ArrayList<String> activeSkill;
	private ArrayList<String> passiveSkill;
	private int difficulty;
	private String skillType;
	
	
	public Champion() {
		// TODO Auto-generated constructor stub
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	/*public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}*/


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


	public String getDamage_style() {
		return damage_style;
	}


	public void setDamage_style(String damage_style) {
		this.damage_style = damage_style;
	}


	public String getAppearance() {
		return appearance;
	}


	public void setAppearance(String appearance) {
		this.appearance = appearance;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
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


	public String getSkillType() {
		return skillType;
	}


	public void setSkillType(String skillType) {
		this.skillType = skillType;
	}
}
