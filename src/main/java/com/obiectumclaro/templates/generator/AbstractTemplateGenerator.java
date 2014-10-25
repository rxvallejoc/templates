/**
 * 
 */
package com.obiectumclaro.templates.generator;

import java.util.List;
import java.util.Map;

import com.obiectumclaro.templates.exception.TemplateGenerationException;
import com.obiectumclaro.templates.model.FileFormat;
import com.obiectumclaro.templates.model.Template;

/**
 * @author fausto
 * 
 */
public abstract class AbstractTemplateGenerator {

	protected Template template;

	public AbstractTemplateGenerator(Template template) {
		this.template = template;
	}

	protected byte[] generate(Map<String, Object> simpleParameters)
			throws TemplateGenerationException {
		return generate(simpleParameters, getOutputDefault());
	}

	protected byte[] generate(Map<String, Object> simpleParameters,
			List<?> mainList) throws TemplateGenerationException {
		return generate(simpleParameters, mainList, getOutputDefault());
	}

	protected abstract byte[] generate(Map<String, Object> simpleParameters,
			FileFormat outputFormat) throws TemplateGenerationException;

	protected abstract byte[] generate(Map<String, Object> simpleParameters,
			List<?> mainList, FileFormat outputFormat)
			throws TemplateGenerationException;

	protected abstract FileFormat getOutputDefault();
}
