/**
 * 
 */
package com.obiectumclaro.templates.generator;

import java.util.List;
import java.util.Map;

import com.obiectumclaro.templates.exception.TemplateGenerationException;
import com.obiectumclaro.templates.model.FileFormat;
import com.obiectumclaro.templates.model.Template;
import com.obiectumclaro.templates.util.ValidateCommons;

/**
 * @author fausto
 * 
 */
public class TemplateGenerator {

	private AbstractTemplateGenerator generator;

	public TemplateGenerator(Template template) {
		ValidateCommons.assertNotNull(template, "El template no puede ser null");
		ValidateCommons.assertNotNull(template.getType(), "El tipo de template no puede ser null");
		if (template.getTemplate() == null && template.getTemplatePath() == null) {
			throw new RuntimeException("Debe existir un template por path o por arreglo de bytes");
		}

		generator = TemplateGeneratorFactory.createGenerator(template);
	}

	public byte[] generate(Map<String, Object> simpleParameters) throws TemplateGenerationException {
		return generator.generate(simpleParameters);
	}

	public byte[] generate(Map<String, Object> simpleParameters, FileFormat outputFormat) throws TemplateGenerationException {
		return generator.generate(simpleParameters, outputFormat);
	}

	public byte[] generate(Map<String, Object> simpleParameters, List<?> mainList) throws TemplateGenerationException {
		return generator.generate(simpleParameters, mainList);
	}

	public byte[] generate(Map<String, Object> simpleParameters, List<?> mainList, FileFormat outputFormat)
			throws TemplateGenerationException {
		return generator.generate(simpleParameters, mainList, outputFormat);
	}

}
