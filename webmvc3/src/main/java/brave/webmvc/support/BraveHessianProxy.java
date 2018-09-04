package brave.webmvc.support;

import brave.Tracing;
import brave.propagation.TraceContext;
import com.caucho.hessian.client.HessianConnection;
import com.caucho.hessian.client.HessianProxy;
import com.caucho.hessian.client.HessianProxyFactory;

import java.net.URL;

public class BraveHessianProxy extends HessianProxy {
    /**
     * Protected constructor for subclassing
     *
     * @param url
     * @param factory
     */
    public BraveHessianProxy(URL url, HessianProxyFactory factory) {
        super(url, factory);
    }

    /**
     * Protected constructor for subclassing
     *
     * @param url
     * @param factory
     * @param type
     */
    public BraveHessianProxy(URL url, HessianProxyFactory factory, Class<?> type) {
        super(url, factory, type);
    }

    /**
     * Method that allows subclasses to add request headers such as cookies.
     * Default implementation is empty.
     *
     * @param conn
     */
    @Override
    protected void addRequestHeaders(HessianConnection conn) {
        super.addRequestHeaders(conn);
        TraceContext traceContext =
                Tracing.current().currentTraceContext().get();
        conn.addHeader("X-B3-TraceId", traceContext.traceIdString());
        conn.addHeader("X-B3-SpanId", Long.toHexString(traceContext.spanId()));
        conn.addHeader("X-B3-ParentSpanId", Long.toHexString(traceContext.parentId()));
        conn.addHeader("X-B3-Sampled", "1");
        conn.addHeader("X-B3-Flags", "1");
    }
}
