package main.java.org.pojoparse.util;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import main.java.org.pojoparse.exception.ComplexTypeException;
import main.java.org.pojoparse.io.FileOutput;
import main.java.org.pojoparse.outputformat.CsvFormat;
import main.java.org.pojoparse.outputformat.FileFormat;

/**
 * Utility class that allows applications parse their POJOS
 *
 * @author  Max Fertig
 */
public class PojoParse {

	/**
	 * Java logger instance class
	 */
	private static final Logger logger = Logger.getLogger("main.java.org.util.PojoParse");

	/**
	 * List of Java object types
	 */
	private static final Set<Class<?>> JAVA_OBJECT_TYPES = getJavaObjectTypes();

	/**
	 * Map for the POJO contents
	 */
	private static Map<String, List<String>> pojoMap = null;

	/**
	 * Returns a OutputStream of the POJO object converted to a CSV file.
	 *
	 * @param Object
	 *            some POJO object
	 * @return The OutputSream of CSV text generated
	 */
	public static OutputStream parseToCsv(Object pojo) {
		getMapOfObject(pojo);
		FileFormat csv = new CsvFormat(pojoMap);
		FileOutput output = new FileOutput(csv.getContent());
		return output.getContent();
	}

	/**
	 * Returns a OutputStream of the POJO object list converted to a CSV file.
	 *
	 * @param List<Object>
	 *            some POJO object list
	 * @return The OutputSream of CSV text generated
	 */
	public static OutputStream parseToCsv(List<Object> pojos) {
		getMapOfList(pojos);
		FileFormat csv = new CsvFormat(pojoMap);
		FileOutput output = new FileOutput(csv.getContent());
		return output.getContent();
	}

	/**
	 * Get a map with contents of a list of POJO objects.
	 *
	 * @param List<Object>
	 *            some POJO object list
	 */
	private static void getMapOfList(List<Object> pojos) {
		pojoMap = new HashMap<String, List<String>>();
		for (Object pojo : pojos) {
			mapObject(pojo);
		}
	}

	/**
	 * Get a map with contents of a single POJO object.
	 *
	 * @param Object
	 *            some POJO object
	 */
	private static void getMapOfObject(Object pojo) {
		pojoMap = new HashMap<String, List<String>>();
		mapObject(pojo);
	}

	/**
	 * Set the pojoMap class attribute with a Map of the POJO object attributes
	 * and its values, this map will be used to specify the POJO data to the
	 * output formats.
	 *
	 * @param Object
	 *            some POJO object
	 */
	private static void mapObject(Object pojo) {
		Map<String, String> keyValue = parse(pojo);
		for (Map.Entry<String, String> entry : keyValue.entrySet()) {
			if (pojoMap.containsKey(entry.getKey())) {
				pojoMap.get(entry.getKey()).add(entry.getValue());
			} else {
				pojoMap.put(entry.getKey(), new ArrayList<String>());
				pojoMap.get(entry.getKey()).add(entry.getValue());
			}
		}
	}

	/**
	 * Parse the POJO object
	 *
	 * @param Object
	 *            some POJO object
	 * @return Map<String, String> map with POJO attribute and its value
	 */
	private static Map<String, String> parse(Object pojo) {
		Map<String, String> keyValue = new HashMap<String, String>();
		Class<?> pojoClass = pojo.getClass();
		for (Field field : pojoClass.getDeclaredFields()) {
			try {
				if (!(field.getType().isPrimitive() || isJavaObjectType(field.getType()))) {
					throw new ComplexTypeException("Parameter [" + field.getName() + "] in class [" + pojoClass
							+ "] has a complex type [" + field.getType() + "].");
				}
				keyValue.put(field.getName(), runGetter(field, pojo).toString());
			} catch (IllegalArgumentException e) {
				logger.warning(e.getMessage());
			} catch (ComplexTypeException e) {
				logger.warning(e.getMessage());
			} catch (Exception e) {
				logger.warning(e.getMessage());
			}
		}
		return keyValue;
	}

	/**
	 * Invokes the POJO getters.
	 *
	 * @param Field
	 *            some POJO attribute
	 * @return Object return of getter invoke
	 */
	private static Object runGetter(Field field, Object pojo) {
		Class<?> pojoClass = pojo.getClass();

		for (Method method : pojoClass.getMethods()) {
			if ((method.getName().startsWith("get")) && (method.getName().length() == (field.getName().length() + 3))) {
				if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {
					try {
						return method.invoke(pojo);
					} catch (IllegalAccessException e) {
						logger.warning("Could not determine method: " + method.getName());
					} catch (InvocationTargetException e) {
						logger.warning("Could not determine method: " + method.getName());
					}
				}
			}
		}
		return null;
	}

	/**
	 * Checks if POJO attribute type is some of the Java object types.
	 *
	 * @param Class<?>
	 *            some POJO attribute class
	 * @return Boolean existence of the POJO attribute class in Set of java
	 *         object types
	 */
	private static Boolean isJavaObjectType(Class<?> clazz) {
		return JAVA_OBJECT_TYPES.contains(clazz);
	}

	/**
	 * Creates the list of known Java object types.
	 *
	 * @return Set<Class<?>> Set of the Java object types
	 */
	private static Set<Class<?>> getJavaObjectTypes() {
		Set<Class<?>> ret = new HashSet<Class<?>>();
		ret.add(Boolean.class);
		ret.add(Character.class);
		ret.add(Byte.class);
		ret.add(Short.class);
		ret.add(Integer.class);
		ret.add(Long.class);
		ret.add(Float.class);
		ret.add(Double.class);
		ret.add(Void.class);
		ret.add(String.class);
		return ret;
	}

}
