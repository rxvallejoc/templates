/**
 * 
 */
package com.obiectumclaro.templates.util;

/**
 * @author fausto
 *
 */
public class ValidateCommons {

	public static void assertNotNull(Object o){
		assertNotNull(o, "El elemento no puede ser nulo");
	}

	public static void assertNotNull(Object o, String message){
		if(o == null){
			throw new RuntimeException(message);
		}
	}
}
