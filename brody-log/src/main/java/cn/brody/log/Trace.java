package cn.brody.log;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 调用链ID信息(埋点信息)<br>
 * 注:采用ivms6-swdf-trace埋点库时,no填写rpc_id<br>
 * 通过{@link Trace#Trace(String, String)} 构造
 * </p>
 *
 * @author chenyifu6
 * @date 2022/01/06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trace {
    String traceId;
    /**
     * 序号
     */
    String spanId;

    @Override
    public String toString() {
        if (StrUtil.isEmpty(traceId)) {
            return StrUtil.EMPTY;
        }
        return "<" + traceId + "," + spanId + "> ";

    }
}
