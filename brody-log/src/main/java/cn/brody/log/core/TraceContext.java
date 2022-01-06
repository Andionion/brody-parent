package cn.brody.log.core;


import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Stack;

/**
 * 埋点日志上下文
 *
 * @author chenyifu6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TraceContext implements Cloneable {

    private Stack<Span> spans = new Stack<>();

    /**
     * 用于表示一笔业务的唯一序号
     */
    private String traceId;

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getSpanId() {
        if (!spans.empty()) {
            return spans.peek().getSpanId();
        } else {
            return StrUtil.EMPTY;
        }
    }

    /**
     * 生成新Span,请勿单独使用
     *
     * @param spanId
     * @param parentId
     * @param spanName
     * @author wujian3 2019年1月10日 下午6:51:43
     */
    public void createSpan(String spanId, String parentId, String spanName) {
        spans.push(new Span(spanId, parentId, spanName));
    }

    public TraceContext newSpan(Span span) {
        spans.push(span);
        return this;
    }

    /**
     * 请勿单独使用
     *
     * @return
     * @author wujian3 2019年1月9日 下午5:08:10
     */
    public Span popSpan() {
        if (!spans.empty()) {
            return spans.pop();
        }
        return null;
    }

    public String getSpanName() {
        return spans.peek().getSpanName();
    }

    @Override
    public String toString() {
        return "TraceContext [" + "traceId=" + traceId + " ]";
    }

    @Override
    public Object clone() {
        try {
            TraceContext clone = (TraceContext) super.clone();
            clone.spans = (Stack<Span>) spans.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new InternalError("clone trace context failed", e);
        }
    }
}
