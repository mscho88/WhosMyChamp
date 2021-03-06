package com.example.whosmychamp;

import java.util.ArrayList;

public class Champion {

	private int id;
	private String name;
	private String nickname;
	private ArrayList<String> role;
	private ArrayList<String> skill;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getProfilePic(){
		String address = name.replaceAll(" ","");
		address = address.replaceAll("['.]","");
		address = address.toLowerCase();
		return address;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public ArrayList<String> getRole() {
		return role;
	}

	public void setRole(ArrayList<String> role) {
		this.role = role;
	}

	public ArrayList<String> getSkill() {
		return skill;
	}

	public void setSkill(ArrayList<String> skill) {
		this.skill = skill;
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
