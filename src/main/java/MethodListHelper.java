import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MethodListHelper {

    /**
     * Method returns methods metadata
     * @param aClass class to analyze
     * @param includeAbstract should we include abstract methods
     * @param includeSuperclass should we include methods from the class we extend (only direct superclass)
     * @return collection of methods metadata
     */
    public Collection<MethodInfo> listMethods(Class<?> aClass, boolean includeAbstract, boolean includeSuperclass) {
        List<MethodInfo> methodInfos = new ArrayList<>();

        Method[] declaredMethods = aClass.getDeclaredMethods();
        processMethods(methodInfos, declaredMethods, includeAbstract);

        if (includeSuperclass) {
            Class<?> superClass = aClass.getSuperclass();
            if (superClass != null) {
                Method[] superClassMethods = superClass.getDeclaredMethods();
                processMethods(methodInfos, superClassMethods, includeAbstract);
            }
        }

        return methodInfos;
    }

    private void processMethods(List<MethodInfo> methodInfos, Method[] methods, boolean includeAbstract) {
        for (Method method : methods) {
            if (!includeAbstract && Modifier.isAbstract(method.getModifiers())) {
                continue;
            }


            Parameter[] parameters = method.getParameters();
            List<Class<?>> parameterTypes = new ArrayList<>();

            for (Parameter parameter : parameters) {
                parameterTypes.add(parameter.getType());
            }


            MethodInfo methodInfo = new MethodInfo(
                    method.getName(),
                    Modifier.isAbstract(method.getModifiers()),
                    Collections.unmodifiableList(parameterTypes),
                    method.getReturnType()
            );

            methodInfos.add(methodInfo);
        }
    }
}
