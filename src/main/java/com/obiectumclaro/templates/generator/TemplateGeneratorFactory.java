/**
 * 
 */
package com.obiectumclaro.templates.generator;

import com.obiectumclaro.templates.model.Template;

/**
 * @author fausto
 *
 */
class TemplateGeneratorFactory {

	public static AbstractTemplateGenerator createGenerator(Template template){
		switch (template.getType()) {
		case JASPER:
			return new JasperTemplateGenerator(template);
		case VELOCITY:
			return new VelocityTemplateGenerator(template);
		default:
			throw new IllegalArgumentException(String.format("No existe un generador de templates para %s", template.getType()));
		}
	}
}
