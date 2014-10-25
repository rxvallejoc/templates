/**
 * 
 */
package com.obiectumclaro.templates.model;

/**
 * @author fausto
 * 
 */
public interface Template {

	byte[] getTemplate();

	String getTemplatePath();

	TemplateType getType();
}
