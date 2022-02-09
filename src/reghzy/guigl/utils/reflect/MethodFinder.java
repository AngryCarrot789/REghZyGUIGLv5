package reghzy.guigl.utils.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MethodFinder {
    private final List<Method> methods;
    private final Class<?> clazz;

    public MethodFinder(Class<?> clazz) {
        this.clazz = clazz;
        this.methods = new ArrayList<Method>(Arrays.asList(clazz.getDeclaredMethods()));
    }

    public MethodFinder annotatedWith(Class<? extends Annotation> annotation) {
        for (Iterator<Method> iterator = this.methods.iterator(); iterator.hasNext(); ) {
            if (iterator.next().isAnnotationPresent(annotation)) {
                continue;
            }

            iterator.remove();
        }

        return this;
    }

    public MethodFinder ofReturnType(Class<?> returnType) {
        for (Iterator<Method> iterator = this.methods.iterator(); iterator.hasNext(); ) {
            Method method = iterator.next();
            Class<?> methodRetType = method.getReturnType();
            if (!returnType.isAssignableFrom(methodRetType)) {
                iterator.remove();
            }
        }

        return this;
    }

    public MethodFinder ofPublic() {
        for (Iterator<Method> iterator = this.methods.iterator(); iterator.hasNext(); ) {
            if (iterator.next().isAccessible()) {
                continue;
            }

            iterator.remove();
        }

        return this;
    }

    public MethodFinder ofPrivate() {
        for (Iterator<Method> iterator = this.methods.iterator(); iterator.hasNext(); ) {
            if (iterator.next().isAccessible()) {
                iterator.remove();
            }
        }

        return this;
    }

    public MethodFinder setPublic() {
        for (Method method : this.methods) {
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
        }

        return this;
    }

    public MethodFinder setPrivate() {
        for (Method method : this.methods) {
            if (method.isAccessible()) {
                method.setAccessible(false);
            }
        }

        return this;
    }

    public MethodFinder ofParamCount(int count) {
        for (Iterator<Method> iterator = this.methods.iterator(); iterator.hasNext(); ) {
            Method method = iterator.next();
            Class<?>[] methodParams = method.getParameterTypes();
            if (methodParams.length != count) {
                iterator.remove();
            }
        }

        return this;
    }

    public MethodFinder ofExactParams(Class<?>... parameters) {
        for (Iterator<Method> iterator = this.methods.iterator(); iterator.hasNext(); ) {
            Method method = iterator.next();
            Class<?>[] methodParams = method.getParameterTypes();
            if (methodParams.length == parameters.length) {
                if (methodParams.length == 0) {
                    continue;
                }

                for (int i = 0, len = parameters.length; i < len; i++) {
                    Class<?> p1 = parameters[i];
                    Class<?> p2 = methodParams[i];
                    if (p1 == null) {
                        throw new NullPointerException("Parameter class cannot be null. At index: " + i);
                    }

                    if (p1.isAssignableFrom(p2)) {
                        continue;
                    }

                    iterator.remove();
                    break;
                }
            }
            else {
                iterator.remove();
            }
        }

        return this;
    }

    public List<Method> getMethods() {
        return this.methods;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}