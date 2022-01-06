package cn.brody.log;

import cn.brody.log.core.TraceContext;
import cn.brody.log.core.TraceLocalUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.slf4j.MDC;

/**
 * * 日志模板类。<br>
 * * 需手动填写的日志内容有：<调用链ID信息> [错误码] - 日志描述<br>
 * * 调用链ID信息：通过{@link BrodyLog#trace()}} 方法构造。<br>
 * * 错误码:String类型 <br>
 * * 日志描述: 由{@link Message}对象定义, 日志描述 = [参数1={},...] 日志详情, 通过{@link BrodyLog#message(String, String...)}构造
 * * </p>
 *
 * @author chenyifu6
 * @date 2022/01/06
 */
@Data
public class BrodyLog {
    /**
     * 由错误码和日志描述构造日志模板,默认添加调用链信息<br>
     * 示例:<br>
     * BrodyLog.toLog("0x100001",BrodyLog.message("Login failed","ip","username")) -> [0x100001] - [ip={},username={}] Login failed
     *
     * @param errorCode
     * @param message   {@link BrodyLog#message(String, String...)}
     * @return
     * @author wujian3 2017年10月20日 上午9:41:51
     */
    public static String toLog(String errorCode, Message message) {
        return toLogWithTrace(errorCode, message);
    }

    /**
     * 由错误码，日志描述参数，日志描述详情构造日志模板,默认添加调用链信息<br>
     * 示例： <br>
     * BrodyLog.toLog("0x100001","Login failed","ip","username") -> <br>
     * [0x100001] - [ip={},username={}] Login failed
     *
     * @param errorCode      错误码
     * @param messageDetail  日志描述详情
     * @param parameterNames 日志参数名称
     * @return
     * @author wujian3 2017年10月20日 上午9:42:21
     */
    public static String toLog(String errorCode, String messageDetail, String... parameterNames) {
        return toLogWithTrace(errorCode, new Message(messageDetail, parameterNames));
    }

    /**
     * 日志描述参数，日志描述详情构造日志模板,默认添加调用链信息<br>
     * 示例： <br>
     * BrodyLog.toLog("Login success","ip","username") -> <br>
     * <3ae561e8b65d4b8c,2qwe61e8b65d4b88> - [ip={},username={}] Login success
     *
     * @param messageDetail
     * @param parameterNames
     * @return
     * @author wujian3 2019年5月27日 上午11:10:23
     */
    public static String toLogWithParam(String messageDetail, String... parameterNames) {
        return toLogWithTrace(new Message(messageDetail, parameterNames));
    }

    /**
     * 由埋点信息,错误码，日志描述参数，日志描述详情构造日志模板<br>
     * BrodyLog.toLogWithTrace("0x100001","Login failed","ip","username")-> <br>
     * <3ae561e8b65d4b8c8a34ae1eb99241a7,2qwe61e8b65d4b8c8a34ae1eb99267uu> [0x100001] - [ip={},username={}] Login failed
     *
     * @param errorCode
     * @param messageDetail
     * @param parameterNames
     * @return
     * @author wujian3 2017年11月1日 上午11:33:38
     */
    /*public static String toLogWithTrace(String errorCode, String messageDetail, String... parameterNames) {
        MDC.put("errorCode", errorCode);
        return String.valueOf(trace()) + new ErrorCode(errorCode) +
                new Message(messageDetail, parameterNames);
    }*/

    /**
     * 由错误码，日志描述(无参数)构造日志模板,默认添加调用链信息<br>
     * 示例:<br>
     * BrodyLog.log("0x100001","Login failed") -> <br>
     * [0x100001] - Login failed
     *
     * @param errorCode
     * @param message
     * @return
     * @author wujian3 2017年10月23日 上午11:30:24
     */
    public static String toLog(String errorCode, String message) {
        return toLogWithTrace(errorCode, new Message(message));
    }

    /**
     * 由埋点信息和日志描述(无参数)构造日志模板<br>
     * 示例<br>
     * BrodyLog.toLogWithTrace("Start to call getUserName interface start") -> <3ae561e8b65d4b8c8a34ae1eb99241a7,0> - Start to call getUserName interface start
     *
     * @param message
     * @return
     * @author wujian3 2017年10月23日 下午4:38:00
     */
    public static String toLogWithTrace(String message) {
        return trace() + "- " + message;
    }

    /**
     * 由日志描述(无参数)构造日志模板,默认添加调用链信息<br>
     * BrodyLog.toLog("sample log") ->- sample log
     *
     * @param message
     * @return
     * @author wujian3 2017年10月23日 上午11:31:10
     */
    public static String toLog(String message) {
        return toLogWithTrace(message);
    }

    /**
     * 由埋点信息和日志描述构造日志模板<br>
     * BrodyLog.toLogWithTrace(BrodyLog.message("Start to call","interfaceName")) -><br>
     * <3ae561e8b65d4b8c8a34ae1eb99241a7,2qwe61e8b65d4b8c8a34ae1eb99267uu> - [interfaceName={}] Start to call
     *
     * @param message
     * @return
     * @author wujian3 2017年10月20日 上午9:43:23
     */
    public static String toLogWithTrace(Message message) {
        return String.valueOf(trace()) + message;
    }

    /**
     * 由日志描述构造日志模板,默认添加调用链信息<br>
     * BrodyLog.toLog(BrodyLog.message("It is a log","param1","param2")) -><br>
     * - [param1={},param2={}] It is a log
     *
     * @param message
     * @return
     * @author wujian3 2017年10月23日 上午11:31:33
     */
    public static String toLog(Message message) {
        return String.valueOf(trace()) + message;
    }

    /**
     * 由埋点信息，错误码，日志描述构造日志模板<br>
     * BrodyLog.toLogWithTrace("0x100001",BrodyLog.message("fail to call","interfaceName")) -><br>
     * <3ae561e8b65d4b8c8a34ae1eb99241a7,2qwe61e8b65d4b8c8a34ae1eb99267uu> [0x100001]- [interfaceName={}] fail to call
     *
     * @param errorCode
     * @param message
     * @return
     * @author wujian3 2017年10月23日 下午4:49:22
     */
    public static String toLogWithTrace(String errorCode, Message message) {
        MDC.put("errorCode", errorCode);
        return trace() + "[" + errorCode + "] " + message;
    }

    private static Trace trace() {
        // 从 MDC 中获取 traceId 和 spanId
        String traceId = MDC.get("traceId");
        String spanId = MDC.get("spanId");
        String sampledString = MDC.get("sampled");
        boolean traceExist = StrUtil.isNotEmpty(traceId) && StrUtil.isNotEmpty(spanId);
        // 当 MDC 中有 trace 内容和采样率标识为 true 时才打印
        boolean sampledTrace = traceExist && Boolean.parseBoolean(sampledString);
        if (sampledTrace) {
            return new Trace(traceId, spanId);
        }

        TraceContext tx = TraceLocalUtil.getTraceContext();
        try {
            if (tx != null && StrUtil.isNotEmpty(tx.getTraceId()) && StrUtil.isNotEmpty(tx.getSpanId())) {
                return new Trace(tx.getTraceId(), tx.getSpanId());
            }
        } catch (Exception e) {
        }
        return new Trace(StrUtil.EMPTY, StrUtil.EMPTY);
    }

    /**
     * 构造日志描述
     *
     * @param detail        描述详情
     * @param parameterName 参数名列表
     * @return
     * @author wujian3 2017年10月20日 上午9:41:08
     */
    public static Message message(String detail, String... parameterName) {
        return new Message(detail, parameterName);
    }
}
