package brave.webmvc.support;

import brave.Span;
import brave.Tracer;
import brave.Tracing;
import okhttp3.Request;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import zipkin2.Endpoint;

/**
 * @author pengfeisu
 */
public class BraveHessianProxyFactoryBean extends HessianProxyFactoryBean {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        Tracer tracer = Tracing.currentTracer();
        Span span = tracer.nextSpan().name("hessian").kind(Span.Kind.CLIENT);
        span.tag("v", "1.00");
        span.remoteEndpoint(Endpoint.newBuilder().serviceName(invocation.getMethod().getName()).build());
        span.start();
        Tracing.current().propagation().injector(Request.Builder::addHeader);

        Object invoke = null;
        try {
            invoke = super.invoke(invocation);
        } catch (Exception e) {
            // if there is an error, tag the span
            span.tag("error", "444");

        } finally {
            // when the response is complete, finish the span
            span.finish();
        }

        return invoke;
    }
}
