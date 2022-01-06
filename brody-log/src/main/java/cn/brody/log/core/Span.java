package cn.brody.log.core;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 一次调用
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Span {

    /**
     * 用于表示某一阶段调用序号
     */
    private String spanId;
    /**
     * 用于表示某一阶段父调用序号
     */
    private String parentId;

    private String spanName;

    private Action action;

    public Span(String spanId, String parentId, String spanName) {
        this.spanId = spanId;
        this.parentId = parentId;
        this.spanName = spanName;
    }

}
