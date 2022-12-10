package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Creator {

    private static TestLoggingInterface object;
    private static List<Method> methods;

    public static TestLoggingInterface create(TestLoggingInterface ob) {
        object = ob;
        methods = Arrays.stream(object.getClass().getMethods()).filter(e -> Arrays.stream(e.getAnnotations())
                        .anyMatch(v -> v.annotationType().equals(Log.class)))
                .collect(Collectors.toList());
        return (TestLoggingInterface) Proxy.newProxyInstance(Creator.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class}, new MyInvocationHandler());

    }


    static class MyInvocationHandler implements InvocationHandler {


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (methods.stream().anyMatch(e -> methodEquals(method, e))) {
                System.out.print("executed method: calculation, param: ");
                Arrays.stream(args).forEach(e -> System.out.print(e + ", "));
                System.out.println();
            }

            return method.invoke(object, args);
        }

        //Далее идет своя реализация метода Equals для сравнения методов. Так как стандартная реализация сравнивает
        //то, что методы должны быть объявлены одним и тем же классом, она нам не подходит.В своей реализации выпилил эту проверку.
        //Остальная вся логика взята оттуда.


        private static boolean methodEquals(Method method, Method method2) {
            if (method != null && method2 != null) {
                if (method.getName().equals(method2.getName())) {
                    if (!method.getReturnType().equals(method2.getReturnType())) {
                        return false;
                    }
                    return equalParamTypes(method.getParameterTypes(), method2.getParameterTypes());
                }
            }
            return false;
        }

        private static boolean equalParamTypes(Class<?>[] params1, Class<?>[] params2) {
            /* Avoid unnecessary cloning */
            if (params1.length == params2.length) {
                for (int i = 0; i < params1.length; i++) {
                    if (params1[i] != params2[i])
                        return false;
                }
                return true;
            }
            return false;
        }

    }
}
