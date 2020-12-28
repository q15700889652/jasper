package jasper.driveselection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import jasper.driveselection.Cache.JdbcCache;

/**
 * 动态代理   
 * 当序列化的时候进行代理,Before执行之前执行读取                    After执行完之后进行写入
 * 写入+读取   对不同模块的key进行加工
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
    	//key值加工
    	System.out.println("add...delete...update...query..."+args[args.length-1]);
    	JdbcCache.entityname=args[args.length-1].toString();
    	//读取
    	Serialize.readDisk();
        Object result = method.invoke(target, args);
        //写入
        Serialize.writeDisk();
        return result;
    }
}
