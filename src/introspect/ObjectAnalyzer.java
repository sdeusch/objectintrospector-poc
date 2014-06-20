package introspect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

/**
 * Utility class for introsepcting and instantiating objects
 * 
 * @author deuscs01
 */
public class ObjectAnalyzer {

    /**
     * Prints all attributes of an object regardless of access modifier private, protected, default, public
     */
    public static void printAllAttributes(final Object o) {

        for (final Field field : o.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            final String name = field.getName();
            Object value;
            try {
                value = field.get(o);
                System.out.printf("Object member: %s, value = %s%n", name, value);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                System.err.println("Cannot print value of attribute " + name + ": " + e.getMessage());
            }
        }
    }

    public static void printAttribute(final Object o, final String attribute) {
        Field field;
        try {
            field = o.getClass().getDeclaredField(attribute);
            field.setAccessible(true);
            final String name = field.getName();
            final Object value = field.get(o);
            System.out.printf("Object member: %s, value = %s%n", name, value);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            System.err.println("Cannot print value of attribute " + attribute + ": " + e.getMessage());
        }
    }

    public static void setAttribute(final Object o, final String attribute, final String value)
            throws NoSuchMethodException {

        try {
            BeanUtils.getProperty(o, attribute);

            // delegating reflection to BeanUtils API
            BeanUtils.setProperty(o, attribute, value);
            System.out.println("value of attribute " + attribute + " changed to " + value);
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            System.err.println("Could not set attribute " + attribute + " to value " + value + ": " + e.getMessage());
        }

    }

    public static void setInstanceValue(final Object classInstance, final String fieldName, final Object newValue)
            throws SecurityException, NoSuchFieldException, ClassNotFoundException, IllegalArgumentException,
            IllegalAccessException {
        // Get the private field
        final Field field = classInstance.getClass().getDeclaredField(fieldName);
        // Allow modification on the field
        field.setAccessible(true);
        // Sets the field to the new value for this instance
        field.set(classInstance, newValue);
    }

    /**
     * Reassign new empty instance of type class for passed in Object handle, or unchanged if clasz does not exist
     */
    public static Object makeEmptyObject(final String classname) {
        Object object = null;
        try {
            object = Class.forName(classname).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            System.err.println("Error, could not instantiate new object of type " + classname + ": " + e);
        }
        return object;
    }

}
