package springai.carbonfootprint.service;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.*;

@Slf4j
public class ObjectMapper {

    public static Map<String, Object> convertObjectToMap(Object object) {
        Map<String, Object> resultMap = new HashMap<>();

        if (object == null) {
            return resultMap; // Return empty map for null objects
        }

        Class<?> objClass = object.getClass();
        Field[] fields = objClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true); // Enable access to private fields
            try {
                Object value = field.get(object);
                // Handle nested objects, collections, and arrays
                if (value != null) {
                    if (value.getClass().isArray()) {
                        resultMap.put(field.getName(), convertArrayToList(value));
                    } else if (value instanceof Collection) {
                        resultMap.put(field.getName(), convertCollectionToList((Collection<?>) value));
                    } else if (isBasicType(value.getClass())) {
                        resultMap.put(field.getName(), value);
                    } else {
                        resultMap.put(field.getName(), convertObjectToMap(value)); // Recursion for nested objects
                    }
                }
            } catch (IllegalAccessException e) {
                log.error(e.getMessage());
            }
        }

        return resultMap;
    }

    private static List<Object> convertArrayToList(Object array) {
        List<Object> list = new ArrayList<>();

        if (array instanceof Object[]) {
            Collections.addAll(list, (Object[]) array);

        } else if (array instanceof int[]) {
            for (int i : (int[]) array) {
                list.add(i);
            }

        } else if (array instanceof double[]) {
            for (double d : (double[]) array) {
                list.add(d);
            }
        }
        // Add more cases for other primitive types if needed
        return list;
    }

    private static List<Object> convertCollectionToList(Collection<?> collection) {
        List<Object> list = new ArrayList<>();
        for (Object item : collection) {
            if (item != null) {
                list.add(item);
            }
        }
        return list;
    }

    private static boolean isBasicType(Class<?> clazz) {
        return clazz.isPrimitive() ||
                clazz == String.class ||
                Number.class.isAssignableFrom(clazz) ||
                clazz == Boolean.class ||
                clazz == Character.class ||
                clazz == Instant.class;
    }
}
