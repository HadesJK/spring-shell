package com.jql.spring.shell;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * @author 金奇樑(hzjinqiliang)
 * @since 202016/10/12 14:57
 */
@Component
public class HelloWorldCommand implements CommandMarker {
    private final Logger LOG = Logger.getLogger(getClass().getName());

    @CliAvailabilityIndicator({"hw simple"})
    public boolean isCommandAvailable() {
        return true;
    }

    @CliCommand(value = "hw simple", help = "Print a simple hello world message")
    public String simple(
            @CliOption(key = {"message"}, mandatory = true, help = "The hello world message")
            final String message,
            @CliOption(key = {"location"}, mandatory = false, help = "Where you are saying hello", specifiedDefaultValue = "At work")
            final String location) {
        return "Message = [" + message + "] Location = [" + location + "]";

    }

}
