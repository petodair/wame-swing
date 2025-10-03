package br.com.wame.model;

import javax.swing.Icon;

public class CardModel {
	Icon icon;
	String title;
	String values;
	String description;	
	
	public CardModel() {}
	
	public CardModel(Icon icon, String title, String values, String description) {
		super();
		this.icon = icon;
		this.title = title;
		this.values = values;
		this.description = description;
	}
	
	public Icon getIcon() {
		return icon;
	}
	public void setIcon(Icon icon) {
		this.icon = icon;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getValues() {
		return values;
	}
	public void setValues(String values) {
		this.values = values;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
