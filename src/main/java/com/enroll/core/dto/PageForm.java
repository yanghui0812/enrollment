package com.enroll.core.dto;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class PageForm {

	@SerializedName("button_font_color")
	private String buttonFontColor = "white";

	@SerializedName("addon_color")
	private String addonColor = "#555555";

	@SerializedName("recipient_email")
	private String recipient_Email = "";

	@SerializedName("submit_text")
	private String submitText = "Submit";

	@SerializedName("corner_style")
	private String cornerStyle = "round";

	@SerializedName("label_orientation")
	private String labelOrientation = "vertical";

	@SerializedName("button_style")
	private String buttonStyle = "solid";

	@SerializedName("button_size")
	private String buttonSize = "medium";

	@SerializedName("hide_intro")
	private String hideIntro = "on";

	@SerializedName("input_size")
	private String inputSize = "medium";

	@SerializedName("font_family")
	private String fontFamily = "Arial, Helvetic; sans-serif";

	@SerializedName("description")
	private String description = "";

	@SerializedName("button_color")
	private String buttonColor = "blue";

	@SerializedName("focus_color")
	private String focusColor = "#66afe9";

	@SerializedName("extra_js_css")
	private String extraJsCss = "";

	@SerializedName("nickname")
	private String nickname = "Contact Form";

	@SerializedName("addon_background")
	private String addonBackground = "#eeeeee";

	@SerializedName("font_size")
	private String fontSize = "14";

	@SerializedName("name")
	private String name = "Contact Form";

	@SerializedName("counter")
	private int counter = 8;

	@SerializedName("button_color_custom")
	private String buttonColorCustom = "#337ab7";

	@SerializedName("font_color")
	private String fontColor = "black";

	@SerializedName("focus_glow")
	private String focusGlow = "yes";

	private List<PageFormField> fields = new ArrayList<>();

	public PageForm(FormMetaDTO formMeta) {
		formMeta.getFormFieldMetaList().stream().forEach(formFieldMeta -> {
			PageFormField pageField = new PageFormField();
			pageField.setType(formFieldMeta.getFieldType());
			pageField.setRequired(formFieldMeta.isRequired());
			pageField.setName(formFieldMeta.getFieldName());
			if (formFieldMeta.hasOptions()) {
				formFieldMeta.getFieldOptionList().stream().forEach(option -> {
					PageOption pageOption = new PageOption(option.getDisplay());
					pageField.addChoice(pageOption);
				});
			}
			fields.add(pageField);
		});
		this.name = formMeta.getFormName();
		this.description = formMeta.getFormDescription();
	}

	public String getButtonFontColor() {
		return buttonFontColor;
	}

	public void setButtonFontColor(String buttonFontColor) {
		this.buttonFontColor = buttonFontColor;
	}

	public String getAddonColor() {
		return addonColor;
	}

	public void setAddonColor(String addonColor) {
		this.addonColor = addonColor;
	}

	public String getRecipient_Email() {
		return recipient_Email;
	}

	public void setRecipient_Email(String recipient_Email) {
		this.recipient_Email = recipient_Email;
	}

	public String getSubmitText() {
		return submitText;
	}

	public void setSubmitText(String submitText) {
		this.submitText = submitText;
	}

	public String getCornerStyle() {
		return cornerStyle;
	}

	public void setCornerStyle(String cornerStyle) {
		this.cornerStyle = cornerStyle;
	}

	public String getLabelOrientation() {
		return labelOrientation;
	}

	public void setLabelOrientation(String labelOrientation) {
		this.labelOrientation = labelOrientation;
	}

	public String getButtonStyle() {
		return buttonStyle;
	}

	public void setButtonStyle(String buttonStyle) {
		this.buttonStyle = buttonStyle;
	}

	public String getButtonSize() {
		return buttonSize;
	}

	public void setButtonSize(String buttonSize) {
		this.buttonSize = buttonSize;
	}

	public String getHideIntro() {
		return hideIntro;
	}

	public void setHideIntro(String hideIntro) {
		this.hideIntro = hideIntro;
	}

	public String getInputSize() {
		return inputSize;
	}

	public void setInputSize(String inputSize) {
		this.inputSize = inputSize;
	}

	public String getFontFamily() {
		return fontFamily;
	}

	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getButtonColor() {
		return buttonColor;
	}

	public void setButtonColor(String buttonColor) {
		this.buttonColor = buttonColor;
	}

	public String getFocusColor() {
		return focusColor;
	}

	public void setFocusColor(String focusColor) {
		this.focusColor = focusColor;
	}

	public String getExtraJsCss() {
		return extraJsCss;
	}

	public void setExtraJsCss(String extraJsCss) {
		this.extraJsCss = extraJsCss;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAddonBackground() {
		return addonBackground;
	}

	public void setAddonBackground(String addonBackground) {
		this.addonBackground = addonBackground;
	}

	public String getFontSize() {
		return fontSize;
	}

	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public String getButtonColorCustom() {
		return buttonColorCustom;
	}

	public void setButtonColorCustom(String buttonColorCustom) {
		this.buttonColorCustom = buttonColorCustom;
	}

	public String getFontColor() {
		return fontColor;
	}

	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

	public String getFocusGlow() {
		return focusGlow;
	}

	public void setFocusGlow(String focusGlow) {
		this.focusGlow = focusGlow;
	}

	public List<PageFormField> getFields() {
		return fields;
	}

	public void setFields(List<PageFormField> fields) {
		this.fields = fields;
	}
}