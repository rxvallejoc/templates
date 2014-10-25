/**
 * 
 */
package com.obiectumclaro.templates.generator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import com.obiectumclaro.templates.exception.TemplateGenerationException;
import com.obiectumclaro.templates.model.FileFormat;
import com.obiectumclaro.templates.model.Template;

/**
 * @author fausto
 * 
 */
class JasperTemplateGenerator extends AbstractTemplateGenerator {

	private InputStream templateStream;

	public JasperTemplateGenerator(Template template) {
		super(template);
		setTemplate();
	}

	private void setTemplate() {
		try {
			if (templateStream == null || templateStream.available() <= 0) {
				if (template.getTemplatePath() == null) {
					this.templateStream = new ByteArrayInputStream(template.getTemplate());
				} else {
					this.templateStream = Thread.currentThread().getContextClassLoader()
							.getResourceAsStream(template.getTemplatePath());
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected byte[] generate(Map<String, Object> simpleParameters, List<?> mainList, FileFormat outputFormat)
			throws TemplateGenerationException {

		return generateTemplate(simpleParameters, new JRBeanCollectionDataSource(mainList), outputFormat);
	}

	@Override
	protected byte[] generate(Map<String, Object> simpleParameters, FileFormat outputFormat) throws TemplateGenerationException {
		return generateTemplate(simpleParameters, new JREmptyDataSource(), outputFormat);
	}

	@Override
	protected FileFormat getOutputDefault() {
		return FileFormat.PDF;
	}

	private byte[] generateTemplate(Map<String, Object> parameters, JRDataSource ds, FileFormat outputFormat)
			throws TemplateGenerationException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			setTemplate();
			JasperPrint jp = JasperFillManager.fillReport(templateStream, parameters, ds);
			byte[] result;
			switch (outputFormat) {
			case PDF:
				exportPdf(jp, bos);
				break;
			case EXCEL:
				exportXls(jp, bos);
				break;

			default:
				throw new TemplateGenerationException(String.format(
						"No existe un metodo para generar el reporte con salida %s dentro de JasperTemplateGenerator",
						outputFormat));
			}

			result = bos.toByteArray();

			return result;
		} catch (JRException e) {
			throw new TemplateGenerationException(e);
		} finally {
			try {
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void exportPdf(JasperPrint jp, OutputStream stream) throws JRException {
		JasperExportManager.exportReportToPdfStream(jp, stream);
	}

	private void exportXls(JasperPrint jp, OutputStream stream) throws JRException {
		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, stream);
		exporter.exportReport();
	}

}
