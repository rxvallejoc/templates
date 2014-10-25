/**
 * 
 */
package com.obiectumclaro.templates.model;

/**
 * @author fausto
 * 
 */
public class SimpleTemplate implements Template {

	private byte[] template;
	private TemplateType type;
	private String templatePath;

	public SimpleTemplate(byte[] template, TemplateType type) {
		super();
		this.template = template;
		this.type = type;
	}

	public SimpleTemplate(String templatePath, TemplateType type) {
		super();
		this.templatePath = templatePath;
		this.type = type;
	}

	public byte[] getTemplate() {
		return template;
	}

	public void setTemplate(byte[] template) {
		this.template = template;
	}

	public TemplateType getType() {
		return type;
	}

	public void setType(TemplateType type) {
		this.type = type;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

}
