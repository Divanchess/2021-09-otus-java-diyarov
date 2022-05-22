package ru.otus.aop.proxy;

import ru.otus.aop.annotations.Log;
import ru.otus.aop.logging.TestLogging;
import ru.otus.aop.logging.TestLoggingInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
        private Set<Method> methodsWithAnnotation = new HashSet<>();

        DemoInvocationHandler(TestLoggingInterface myClass) {
            this.myClass = myClass;
            for ( Method m : myClass.getClass().getMethods()) {
                if (m.isAnnotationPresent(Log.class)) {
                    this.methodsWithAnnotation.add(m);
                }
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            for (Method m : methodsWithAnnotation) {
                if(m.getName().equals(method.getName()) && Arrays.equals(m.getParameterTypes(),(method.getParameterTypes())))
                    System.out.println("Proxy  | executed method:" + method.getName() + ", params:" + Arrays.toString(args));
            }
            return method.invoke(myClass, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" + "myClass=" + myClass + '}';
        }
    }

}


    

