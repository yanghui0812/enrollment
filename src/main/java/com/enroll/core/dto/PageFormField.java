package com.enroll.core.dto;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class PageFormField {

	@SerializedName("prepend_text")
	private String prependText;

	@SerializedName("title")
	private String name;

	@SerializedName("prepend_icon")
	private String prependIcon;

	@SerializedName("append_icon")
	private String appendIcon;
	private String prepend;

	@SerializedName("append_text")
	private String appendText;
	private boolean inline;

	@SerializedName("is_required")
	private boolean required;

	private String type;

	private int id;

	private String append;

	private String instructions;

	private List<PageOption> choices = new ArrayList<>();

	private String placeholder;

	public String getPrependText() {
		return prependText;
	}

	public void setPrependText(String prependText) {
		this.prependText = prependText;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrependIcon() {
		return prependIcon;
	}

	public void setPrependIcon(String prependIcon) {
		this.prependIcon = prependIcon;
	}

	public String getAppendIcon() {
		return appendIcon;
	}

	public void setAppendIcon(String appendIcon) {
		this.appendIcon = appendIcon;
	}

	public String getPrepend() {
		return prepend;
	}

	public void setPrepend(String prepend) {
		this.prepend = prepend;
	}

	public String getAppendText() {
		return appendText;
	}

	public void setAppendText(String appendText) {
		this.appendText = appendText;
	}

	public boolean isInline() {
		return inline;
	}

	public void setInline(boolean inline) {
		this.inline = inline;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAppend() {
		return append;
	}

	public void setAppend(String append) {
		this.append = append;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public List<PageOption> getChoices() {
		return choices;
	}

	public void addChoice(PageOption option) {
		choices.add(option);
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}
}