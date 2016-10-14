package com.jql.spring.shell;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.PromptProvider;
import org.springframework.stereotype.Component;

/**
 * @author 金奇樑(hzjinqiliang)
 * @since 202016/10/12 20:34
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomPromptProvider implements PromptProvider {
    public String getPrompt() {
        return "~$";
    }

    public String getProviderName() {
        return "HadesJK";
    }
}
