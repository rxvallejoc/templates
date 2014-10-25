/**
 * 
 */
package com.obiectumclaro.templates.generator;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.obiectumclaro.templates.exception.TemplateGenerationException;
import com.obiectumclaro.templates.model.FileFormat;
import com.obiectumclaro.templates.model.Template;

/**
 * @author fausto
 * 
 */
class VelocityTemplateGenerator extends AbstractTemplateGenerator {

	private String template;

	public VelocityTemplateGenerator(Template template) {
		super(template);
		this.template = new String(template.getTemplate());
	}

	@Override
	protected byte[] generate(Map<String, Object> simpleParameters,
			FileFormat outputFormat) throws TemplateGenerationException {
		try {
			Velocity.init();
			VelocityContext context = new VelocityContext();
			for (Entry<String, Object> e : simpleParameters.entrySet()) {
				context.put(e.getKey(), e.getValue());
			}

			StringWriter w = new StringWriter();
			Velocity.evaluate(context, w, "template", template);
			return w.toString().getBytes();
		} catch (Exception e) {
			throw new TemplateGenerationException(e);
		}
	}

	@Override
	protected byte[] generate(Map<String, Object> simpleParameters,
			List<?> mainList, FileFormat outputFormat)
			throws TemplateGenerationException {
		throw new RuntimeException(
				"Metodo sin implementacion, ocupar en su lugar VelocityTemplateGenerator.generate(Map<String, Object> simpleParameters, FileFormat outputFormat)");
	}

	@Override
	protected FileFormat getOutputDefault() {
		return null;
	}

}
