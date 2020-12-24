package jasper.driveselection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 当序列化的时候进行代理,执行之前执行读取,执行完之后进行写入
 * jdkd动态代理              
 *
 * @author Scorrt
 * @create 2018-03-29 16:17
 **/
public class JDKDynamicProxy implements InvocationHandler {

    private Object target;

    public JDKDynamicProxy(Object target) {
        this.target = target;
    }

    /**
     * 获取被代理接口实例对象
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
	public <T> T getProxy() {
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    	Serialize.readDisk();
        Object result = method.invoke(target, args);
        Serialize.writeDisk();
        return result;
    }
}
