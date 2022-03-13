package ru.otus.aop.proxy;

import ru.otus.aop.annotations.Log;
import ru.otus.aop.logging.TestLogging;
import ru.otus.aop.logging.TestLoggingInterface;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Add logging while invoking methods with @Log annotation
 */
public class LoggingProxy {

    private LoggingProxy() {
    }

    public static TestLoggingInterface createMyClass() {
        var logging = new TestLogging();
        InvocationHandler handler = new DemoInvocationHandler(logging);
        return (TestLoggingInterface) java.lang.reflect.Proxy.newProxyInstance(LoggingProxy.class.getClassLoader(), new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    public static class DemoInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface myClass;

        DemoInvocationHandler(TestLoggingInterface myClass) {
            this.myClass = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (checkMethodWithAnnotationPresent(myClass.getClass().getName(), method.getName(), Log.class, args))
                System.out.println("Proxy  | executed method:" + method.getName() + ", params:" + Arrays.toString(args));
            return method.invoke(myClass, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" + "myClass=" + myClass + '}';
        }
    }

    private static boolean checkMethodWithAnnotationPresent(String myClassName, String methodName, Class<? extends Annotation> annotation, Object[] args) throws ClassNotFoundException {
        Class<?> myClass = Class.forName(myClassName);
        boolean isMethodWithAnnotationPresent = false;
        Class argsClasses[] = new Class[args.length];
        Class argsPrimitiveClasses[] = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            argsClasses[i] = args[i].getClass();
            argsPrimitiveClasses[i] = returnPrimitiveClassIfExists(args[i].getClass());
        }
        try {
            isMethodWithAnnotationPresent = myClass.getMethod(methodName, argsClasses).isAnnotationPresent(annotation);
        } catch (NoSuchMethodException e) {
            try {
                isMethodWithAnnotationPresent = myClass.getMethod(methodName, argsPrimitiveClasses).isAnnotationPresent(annotation);
            } catch (NoSuchMethodException ne) {
                ne.printStackTrace();
            }
        }
        return isMethodWithAnnotationPresent;
    }

    private static Class<?> returnPrimitiveClassIfExists(Class<?> cls) {
        if (cls == Character.class) return char.class;
        if (cls == Integer.class) return int.class;
        if (cls == Boolean.class) return boolean.class;
        if (cls == Byte.class) return byte.class;
        if (cls == Double.class) return double.class;
        if (cls == Float.class) return float.class;
        if (cls == Long.class) return long.class;
        if (cls == Short.class) return short.class;
        return cls;
    }

}


    

