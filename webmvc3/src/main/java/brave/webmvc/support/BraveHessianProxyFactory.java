package brave.webmvc.support;

import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.io.HessianRemoteObject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.URL;

public class BraveHessianProxyFactory extends HessianProxyFactory {

    @Override
    public Object create(Class<?> api, URL url, ClassLoader loader)
    {
        if (api == null) {
            throw new NullPointerException("api must not be null for HessianProxyFactory.create()");
        }
        InvocationHandler handler = null;

        handler = new BraveHessianProxy(url, this, api);

        return Proxy.newProxyInstance(loader,
                new Class[] { api,
                        HessianRemoteObject.class },
                handler);
    }
}
