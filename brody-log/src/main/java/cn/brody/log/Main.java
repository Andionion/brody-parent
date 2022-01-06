package cn.brody.log;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenyifu6
 * @date 2022/01/06
 */
@Slf4j
public class Main {

    public static void main(String[] args) {
        log.info(BrodyLog.toLog(BrodyLog.message("这是一个测试", "test1", "test2")), 1, 2);
    }
}
