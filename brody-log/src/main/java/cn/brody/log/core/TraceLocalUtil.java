package cn.brody.log.core;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * <p>设置和获取调用链上下文</p>
 * 使用线程池时,推荐如下用法,保证调用链上下文在父子线程中正确传递:<br>
 * ExecutorService executorService = ...<br>
 * // 额外的处理,生成修饰了的对象executorService<br>
 * executorService = TtlExecutors.getTtlExecutorService(executorService);<br>
 * 跨线程场景更多用法参考:https://github.com/alibaba/transmittable-thread-local<br>
 *
 * @author wujian3 2019年5月13日 下午2:30:18
 * @version V1.0
 */
public class TraceLocalUtil {

    private static final ThreadLocal<TraceContext> TRACE_CONTEXT = new TransmittableThreadLocal<>();

    public static TraceContext getTraceContext() {
        return TRACE_CONTEXT.get();
    }

    public static void setTraceContext(TraceContext tc) {
        TRACE_CONTEXT.set(tc);
    }

    public static void clearTraceContext() {
        TRACE_CONTEXT.remove();
    }

    /**
     * 用途：将context的拷贝放入子线程，context需要在主线程中获取
     */
    public static void setOnCopyTraceContext(TraceContext tc) {
        TRACE_CONTEXT.set((TraceContext) tc.clone());
    }

}
