package reghzy.guigl.utils.reflect;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import reghzy.guigl.utils.RZFormats;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericSignatureFormatError;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * A class to cleanly use reflection without having to use try catch blocks everywhere
 */
public class Reflect {
    /**
     * Casts the object value to another value (generic)
     *
     * <p>
     * This should not return null, unless you give a null value
     * </p>
     */
    @Contract(value = "!null->!null;null->null", pure = true)
    public static <T> T cast(Object value) {
        // don't need to catch ClassCastException because
        // generics :)
        return (T) value;
    }

    public static Class<?> getFieldGenericType(Field field, int genericIndex) {
        Type type;

        try {
            type = field.getGenericType();
        }
        catch (GenericSignatureFormatError e) {
            throw new RuntimeException("Generic type is malformed", e);
        }

        if (type instanceof ParameterizedType) {
            Type[] types;
            try {
                types = ((ParameterizedType) type).getActualTypeArguments();
            }
            catch (TypeNotPresentException e) {
                throw new RuntimeException("Field had no generic types", e);
            }
            catch (MalformedParameterizedTypeException e) {
                throw new RuntimeException("Generic type was malformed; it most likely wasn't a class, therefore was un-instantiatable", e);
            }

            if (genericIndex >= types.length) {
                throw new RuntimeException("There is no generic type at index " + genericIndex);
            }

            return (Class<?>) types[genericIndex];
        }

        throw new RuntimeException("Generic type is not a ParameterizedType");
    }

    public static Class<?> getMethodGenericParameterType(Method method, int parameterIndex, int genericIndex) {
        Type[] types;

        try {
            types = method.getGenericParameterTypes();
        }
        catch (GenericSignatureFormatError e) {
            throw new RuntimeException("Generic type is malformed", e);
        }

        if (genericIndex >= types.length) {
            throw new RuntimeException("There is no generic type at index " + genericIndex);
        }

        if (types[parameterIndex] instanceof ParameterizedType) {
            Type[] instanceTypes;
            try {
                instanceTypes = ((ParameterizedType) types[parameterIndex]).getActualTypeArguments();
            }
            catch (TypeNotPresentException e) {
                throw new RuntimeException(RZFormats.format("Method parameter at index {0} had no generic types", parameterIndex), e);
            }
            catch (MalformedParameterizedTypeException e) {
                throw new RuntimeException("Generic type was malformed; it most likely wasn't a class, therefore was un-instantiatable", e);
            }

            if (genericIndex >= instanceTypes.length) {
                throw new RuntimeException("There is no generic type at index " + genericIndex);
            }

            return (Class<?>) instanceTypes[genericIndex];
        }

        throw new RuntimeException(RZFormats.format("Generic type at method index {0} was is not a ParameterizedType", parameterIndex));
    }

    public static Class<?> findClass(@NotNull String className) {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(RZFormats.format("The class '{0}' could not be found", className), e);
        }
    }

    public static Method findPublicMethod(@NotNull Class<?> clazz, @NotNull String name, Class<?>... parameterTypes) {
        Class<?> nextClass = clazz;
        while (nextClass != null) {
            try {
                return nextClass.getMethod(name, parameterTypes);
            }
            catch (NoSuchMethodException e) {
                nextClass = nextClass.getSuperclass();
            }
        }

        throw new RuntimeException(RZFormats.format("Could not find the method '{0}' in the hierarchy for the class '{1}'", name, clazz.getName()));
    }

    public static Method findPrivateMethod(@NotNull Class<?> clazz, @NotNull String name, Class<?>... parameterTypes) {
        Class<?> nextClass = clazz;
        while (nextClass != null) {
            try {
                Method method = nextClass.getDeclaredMethod(name, parameterTypes);
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }

                return method;
            }
            catch (NoSuchMethodException e) {
                nextClass = nextClass.getSuperclass();
            }
        }

        throw new RuntimeException(RZFormats.format("Could not find the declared method '{0}' in the hierarchy for the class '{1}'", name, clazz.getName()));
    }

    public static Field findPublicField(@NotNull Class<?> clazz, @NotNull String name) {
        Class<?> nextClass = clazz;
        while (nextClass != null) {
            try {
                return nextClass.getField(name);
            }
            catch (NoSuchFieldException e) {
                nextClass = nextClass.getSuperclass();
            }
        }

        throw new RuntimeException(RZFormats.format("Could not find the field '{0}' in the hierarchy for the class '{1}'", name, clazz.getName()));
    }

    public static Field findPrivateField(@NotNull Class<?> clazz, @NotNull String name) {
        Class<?> nextClass = clazz;
        while (nextClass != null) {
            try {
                Field field = nextClass.getDeclaredField(name);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }

                return field;
            }
            catch (NoSuchFieldException e) {
                nextClass = nextClass.getSuperclass();
            }
        }

        throw new RuntimeException(RZFormats.format("Could not find the declared field '{0}' in the hierarchy for the class '{1}'", name, clazz.getName()));
    }

    public static <T> T getFieldValue(@Nullable Object instance, @NotNull Field field) {
        try {
            return (T) field.get(instance);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException("Field was not accessible", e);
        }
        catch (IllegalArgumentException e) {
            throw new RuntimeException("The field was an instance method, but the specified instance an incorrect type", e);
        }
        catch (NullPointerException e) {
            throw new RuntimeException("The field was an instance method, but the specified instance was null", e);
        }
        catch (ClassCastException e) {
            throw new RuntimeException("The field return value could not be casted to the generic type", e);
        }
    }

    public static void setFieldValue(@Nullable Object instance, @NotNull Field field, Object value) {
        try {
            field.set(instance, value);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException("Field was not accessible", e);
        }
        catch (IllegalArgumentException e) {
            throw new RuntimeException("The field was an instance field, but the specified instance an incorrect type. Or, the given value's type is incompatible with the field type", e);
        }
        catch (NullPointerException e) {
            throw new RuntimeException("The field was an instance field, but the specified instance was null", e);
        }
        catch (ClassCastException e) {
            throw new RuntimeException("The field return value could not be casted to the generic type", e);
        }
    }

    public static <T> T invokeMethod(@Nullable Object instance, @NotNull Method method, Object... parameters) {
        try {
            return (T) method.invoke(instance, parameters);
        }
        catch (InvocationTargetException e) {
            throw new RuntimeException("The method threw an exception", e);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException("The method was inaccessible (protected/private)", e);
        }
        catch (IllegalArgumentException e) {
            throw new RuntimeException("The method was either an instance method (but the specified instance wasn't of that type), or there was an incorrect parameter", e);
        }
        catch (NullPointerException e) {
            throw new RuntimeException("The method was an instance method, but the specified instance was null", e);
        }
        catch (ClassCastException e) {
            throw new RuntimeException("The method return value could not be casted to the generic type", e);
        }
    }

    public static <T> Constructor<T> getPublicConstructor(@NotNull Class<T> clazz, @Nullable Class<?>... constructorParams) {
        try {
            return clazz.getConstructor(constructorParams);
        }
        catch (NoSuchMethodException e) {
            throw new RuntimeException(RZFormats.format("Could not find the public constructor with {0} parameters", constructorParams == null ? 0 : constructorParams.length), e);
        }
    }

    public static <T> Constructor<T> getPrivateConstructor(@NotNull Class<T> clazz, @Nullable Class<?>... constructorParams) {
        try {
            Constructor<T> constructor = clazz.getConstructor(constructorParams);
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }

            return constructor;
        }
        catch (NoSuchMethodException e) {
            throw new RuntimeException(RZFormats.format("Could not find the private constructor with {0} parameters", constructorParams == null ? 0 : constructorParams.length), e);
        }
    }

    public static <T> T createInstance(@NotNull Constructor<?> constructor, @Nullable Object... params) {
        try {
            return (T) constructor.newInstance(params);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException("The found constructor was inaccessible (might be private or protected)", e);
        }
        catch (IllegalArgumentException e) {
            throw new RuntimeException("The found constructor required arguments that were not present, or were incorrect types", e);
        }
        catch (InstantiationException e) {
            throw new RuntimeException("The class cannot be instantiated (it might be abstract or an interface)", e);
        }
        catch (InvocationTargetException e) {
            throw new RuntimeException("The constructor threw an exception", e);
        }
    }

    public static <T> T createInstance(@NotNull String className, @Nullable Object... params) {
        if (params == null || params.length == 0) {
            return createInstance(getPublicConstructor(findClass(className)));
        }

        Class<?>[] paramTypes = new Class[params.length];
        for (int i = 0, len = paramTypes.length; i < len; i++) {
            Object obj = params[i];
            if (obj == null) {
                throw new NullPointerException(RZFormats.format("Cannot auto-detect parameter types because a parameter was null (at index {0})", i));
            }

            paramTypes[i] = obj.getClass();
        }

        return createInstance(getPublicConstructor(findClass(className), paramTypes), params);
    }

    public static <T> T createInstance(@NotNull Class<?> clazz, @Nullable Object... params) {
        if (params == null || params.length == 0) {
            return createInstance(getPublicConstructor(clazz));
        }

        Class<?>[] paramTypes = new Class[params.length];
        for (int i = 0, len = paramTypes.length; i < len; i++) {
            Object obj = params[i];
            if (obj == null) {
                throw new NullPointerException(RZFormats.format("Cannot auto-detect parameter types because a parameter was null (at index {0})", i));
            }

            paramTypes[i] = obj.getClass();
        }

        return createInstance(getPublicConstructor(clazz, paramTypes), params);
    }

    public static <T> T createInstance0P(@NotNull String className) {
        return createInstance(getPublicConstructor(findClass(className)));
    }

    public static <T> T createInstance0P(@NotNull Class<?> clazz) {
        return createInstance(getPublicConstructor(clazz));
    }

    public static <T> T createInstance1P(@NotNull String className, @NotNull Object param) {
        return createInstance(getPublicConstructor(findClass(className), param.getClass()), param);
    }

    public static <T> T createInstance1P(@NotNull Class<?> clazz, @NotNull Object param) {
        return createInstance(getPublicConstructor(clazz, param.getClass()), param);
    }

    public static <T> T createInstance0P(@NotNull Constructor<?> constructor) {
        return createInstance(constructor);
    }

    public static <T> T createInstance1P(@NotNull Constructor<?> constructor, @NotNull Object param) {
        return createInstance(constructor, param);
    }

    public static <T> T getPrivateStaticFieldValue(@NotNull Class<?> clazz, @NotNull String field) {
        return getFieldValue(null, findPrivateField(clazz, field));
    }

    public static <T> T getPublicStaticFieldValue(@NotNull Class<?> clazz, @NotNull String field) {
        return getFieldValue(null, findPublicField(clazz, field));
    }

    public static void setPrivateStaticFieldValue(@NotNull Class<?> clazz, @NotNull String field, Object value) {
        setFieldValue(null, findPrivateField(clazz, field), value);
    }

    public static void setPublicStaticFieldValue(@NotNull Class<?> clazz, @NotNull String field, Object value) {
        setFieldValue(null, findPublicField(clazz, field), value);
    }

    public static <T> T getPrivateFieldValue(@NotNull Object instance, @NotNull String field) {
        return getFieldValue(instance, findPrivateField(instance.getClass(), field));
    }

    public static <T> T getPublicFieldValue(@NotNull Object instance, @NotNull String field) {
        return getFieldValue(instance, findPublicField(instance.getClass(), field));
    }

    public static void setPrivateFieldValue(@NotNull Object instance, @NotNull String field, Object value) {
        setFieldValue(instance, findPrivateField(instance.getClass(), field), value);
    }

    public static void setPublicFieldValue(@NotNull Object instance, @NotNull String field, Object value) {
        setFieldValue(instance, findPublicField(instance.getClass(), field), value);
    }

    public static <T> T invokePrivateMethod0P(@NotNull Object instance, @NotNull String method) {
        return invokeMethod(instance, findPrivateMethod(instance.getClass(), method));
    }

    public static <T> T invokePrivateMethod1P(@NotNull Object instance, @NotNull String method, @NotNull Object parameter) {
        return invokeMethod(instance, findPrivateMethod(instance.getClass(), method, parameter.getClass()), parameter);
    }

    public static <T> T invokePublicMethod0P(@NotNull Object instance, @NotNull String method) {
        return invokeMethod(instance, findPrivateMethod(instance.getClass(), method));
    }

    public static <T> T invokePublicMethod1P(@NotNull Object instance, @NotNull String method, @NotNull Object parameter) {
        return invokeMethod(instance, findPrivateMethod(instance.getClass(), method, parameter.getClass()), parameter);
    }

    public static <T> T invokePrivateStaticMethod0P(@NotNull Class<?> clazz, @NotNull String method) {
        return invokeMethod(null, findPrivateMethod(clazz, method));
    }

    public static <T> T invokePrivateStaticMethod1P(@NotNull Class<?> clazz, @NotNull String method, @NotNull Object parameter) {
        return invokeMethod(null, findPrivateMethod(clazz, method, parameter.getClass()), parameter);
    }

    public static <T> T invokePublicStaticMethod0P(@NotNull Class<?> clazz, @NotNull String method) {
        return invokeMethod(null, findPrivateMethod(clazz, method));
    }

    public static <T> T invokePublicStaticMethod1P(@NotNull Class<?> clazz, @NotNull String method, @NotNull Object parameter) {
        return invokeMethod(null, findPrivateMethod(clazz, method, parameter.getClass()), parameter);
    }
}
