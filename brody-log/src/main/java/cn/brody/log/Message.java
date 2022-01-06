package cn.brody.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 日志描述.<br>
 * 日志描述 = 描述详情 [参数1={},参数2={},...] <br>
 *
 * @author chenyifu6
 * @date 2022/01/06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    String message;
    String[] params;

    public Message(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        if (params == null || params.length == 0) {
            return "- " + message;
        } else {
            StringBuilder message = new StringBuilder("- " + this.message + " [");
            for (String param : params) {
                message.append(param).append("={},");
            }
            message.setLength(message.length() - 1);
            message.append("] ");
            return message.toString();
        }
    }
}
