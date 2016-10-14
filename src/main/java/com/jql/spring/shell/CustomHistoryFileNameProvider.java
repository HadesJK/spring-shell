package com.jql.spring.shell;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.HistoryFileNameProvider;
import org.springframework.stereotype.Component;

/**
 * @author 金奇樑(hzjinqiliang)
 * @since 202016/10/12 20:46
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomHistoryFileNameProvider implements HistoryFileNameProvider {
    public String getHistoryFileName() {
        return "/Users/yifan/hades/xh/spring-shell/history.txt";
    }

    public String getProviderName() {
        return "HadesJK";
    }
}
