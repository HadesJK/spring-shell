package com.jql.spring.shell;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.BannerProvider;
import org.springframework.stereotype.Component;

/**
 * @author 金奇樑(hzjinqiliang)
 * @since 202016/10/12 20:19
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomBanner implements BannerProvider {
    public String getBanner() {
        StringBuilder sb = new StringBuilder();
        sb.append("================================\n");
        sb.append("*                              *\n");
        sb.append("*                              *\n");
        sb.append("*         Spring Shell         *\n");
        sb.append("*                              *\n");
        sb.append("*                    HadesJK   *\n");
        sb.append("*                              *\n");
        sb.append("*                              *\n");
        sb.append("================================\n");
        return sb.toString();
    }

    public String getVersion() {
        return "1.0";
    }

    public String getWelcomeMessage() {
        return "Welcome to here.";
    }

    public String getProviderName() {
        return "This program is wrote by 一饭.";
    }
}
