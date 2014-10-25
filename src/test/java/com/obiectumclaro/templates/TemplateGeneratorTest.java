/**
 * 
 */
package com.obiectumclaro.templates;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.obiectumclaro.templates.exception.TemplateGenerationException;
import com.obiectumclaro.templates.generator.TemplateGenerator;
import com.obiectumclaro.templates.model.FileFormat;
import com.obiectumclaro.templates.model.SimpleTemplate;
import com.obiectumclaro.templates.model.Template;
import com.obiectumclaro.templates.model.TemplateType;

/**
 * @author fausto
 * 
 */
public class TemplateGeneratorTest {

	private Template jasperTemplate;
	private Template velocityTemplateSimple;
	private Template velocityTemplate;

	private Map<String, Object> parameters;
	private List<DetalleReporte> mainList;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		parameters = new HashMap<String, Object>();
		parameters.put("titulo", "REPORTE DE PRUEBA");
		parameters.put("tituloPagina", "Productos por pagina");
		parameters.put("codigo", "asd545s4dfg1d54g");
		mainList = new ArrayList<DetalleReporte>();
		Calendar c = Calendar.getInstance();
		Random r = new Random();
		for (int i = 0; i < 200; i++) {
			c.add(Calendar.DATE, 1);
			mainList.add(new DetalleReporte(c.getTime(), "producto " + i, (r.nextInt(200) + 1), (r.nextFloat() * 200 + 100)));
		}

		prepareJasperTemplates();
		prepareVelocityTemplates();
	}

	private void prepareJasperTemplates() throws IOException {
		File f = new File("src/test/resources/report1.jasper");
		FileInputStream fis = new FileInputStream(f);
		byte[] jt = new byte[(int) f.length()];
		fis.read(jt);
		fis.close();
		jasperTemplate = new SimpleTemplate(jt, TemplateType.JASPER);

	}

	private void prepareVelocityTemplates() throws IOException {
		byte[] vt = "Titulo: $titulo - Pagina: $tituloPagina".getBytes();
		velocityTemplateSimple = new SimpleTemplate(vt, TemplateType.VELOCITY);

		File f = new File("src/test/resources/velocityReport1.vm");
		FileInputStream fis = new FileInputStream(f);
		byte[] vt2 = new byte[(int) f.length()];
		fis.read(vt2);
		fis.close();
		velocityTemplate = new SimpleTemplate(vt2, TemplateType.VELOCITY);
	}

	@Test
	public void testGenerateSuccessfulJasperTemplate() {
		TemplateGenerator templateGenerator = new TemplateGenerator(jasperTemplate);
		byte[] result;
		try {
			result = templateGenerator.generate(parameters, mainList, FileFormat.PDF);
			generateFileFromByteArray("jasperReport1.pdf", result);
			assertTrue(result.length > 0);

			result = templateGenerator.generate(parameters, mainList, FileFormat.EXCEL);
			generateFileFromByteArray("jasperReport1.xls", result);
			assertTrue(result.length > 0);
		} catch (TemplateGenerationException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test(expected = TemplateGenerationException.class)
	public void testGenerateUnsuccessfulJasperTemplate() throws TemplateGenerationException {
		TemplateGenerator templateGenerator = new TemplateGenerator(jasperTemplate);
		templateGenerator.generate(parameters, FileFormat.PLAIN);
	}

	@Test
	public void testGenerateVelocityTemplateSimple() {
		TemplateGenerator templateGenerator = new TemplateGenerator(velocityTemplateSimple);
		try {
			byte[] result = templateGenerator.generate(parameters);
			String r = new String(result);
			assertTrue("Titulo: REPORTE DE PRUEBA - Pagina: Productos por pagina".compareTo(r) == 0);
		} catch (TemplateGenerationException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGenerateVelocityTemplate() {
		TemplateGenerator templateGenerator = new TemplateGenerator(velocityTemplate);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.putAll(this.parameters);
		parameters.put("detalles", mainList);

		try {
			byte[] result = templateGenerator.generate(parameters);
			generateFileFromByteArray("velocityReport1.html", result);
			assertTrue(result.length > 0);

			parameters.put("titulo", "Otro Reporte");
			result = templateGenerator.generate(parameters);
			generateFileFromByteArray("velocityReport2.html", result);
			assertTrue(result.length > 0);
		} catch (TemplateGenerationException e) {
			fail(e.getMessage());
		}
	}

	@Test(expected = RuntimeException.class)
	public void testGenerateUnsuccessfulVelocityTemplate() {
		TemplateGenerator templateGenerator = new TemplateGenerator(velocityTemplate);
		try {
			templateGenerator.generate(parameters, mainList);
		} catch (TemplateGenerationException e) {
			fail(e.getMessage());
		}
	}

	private void generateFileFromByteArray(String fileName, byte[] bytes) {
		try {
			FileOutputStream fos = new FileOutputStream("src/test/resources/" + fileName);
			fos.write(bytes);
			fos.close();
			assertTrue(bytes.length > 0);
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
}
