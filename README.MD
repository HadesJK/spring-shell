# spring-shell


[TOC]

**==今天了解apache commons cli的时候，无意见看到了spring shell这个东西，学习一下，就从官方文档开始吧==** 

http://docs.spring.io/spring-shell/docs/current/reference/htmlsingle/

# Introduction


`The Spring Shell provides an interactive shell that allows you to plugin your own custom commands using a Spring based programming model.`

`The shell has been extracted from the Spring Roo project, giving it a strong foundation and rich feature set. One significant change from Spring Roo is that the plugin model is no longer based on OSGi but instead uses the Spring IoC container to discover commands through classpath scanning. There is currently no classloader isolation between plugins, however that maybe added in future versions.`


# demo

官方喜欢先讲理论，结合应用，我这种粗人喜欢直接干！那么给个代码吧。

1. 执行的类

```java
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
```

2. 启动类

```java
package com.jql;

import org.springframework.shell.Bootstrap;

import java.io.IOException;

/**
 * @author 金奇樑(hzjinqiliang)
 * @since 202016/10/12 15:23
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Bootstrap.main(args);
    }
}
```

3. pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.jql</groupId>
    <artifactId>spring-shell</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <spring.shell.version>1.2.0.RELEASE</spring.shell.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.shell</groupId>
            <artifactId>spring-shell</artifactId>
            <version>${spring.shell.version}</version>
        </dependency>
    </dependencies>

</project>
```

4. 在resources下加入文件
```xml
resources
         | META-INF
         |         | spring
         |         |       |spring-shell-plugin.xml
```

**==事实上可以看一下依赖关系，spring shell依赖几个spring的组件==**

**==在ide中执行main函数既可==**

**==下面我执行了2条命令：==**

**==1. hw simple --message jql --location hz==**

**==2. exit==**


# Reference Documentation

spring shell 有3个核心组建：plugin model, built-in commands, and converters。


## Plugin Model

没看出这个plugin jar，大致的作用应该是加载配置文件，地址是： ==classpath*:/META-INF/spring/spring-shell-plugin.xml==，这个值是在org.springframework.shell.Bootstrap类的数组常量CONTEXT_PATH定义的。

这个配置文件其实就是一个普通的spring配置

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans 
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context 
       http://www.springframework.org/schema/context/spring-context-3.1.xsd">

  <context:component-scan base-package="com.jql.spring.shell" />

</beans>
```

## commands

实现一个执行shell命令的类，可以实现一个接口CommandMarker，这是一个标签接口，貌似可以使用注解@Cli也可以。

```java
@Component
public class HelloWorldCommands implements CommandMarker {
// TODO:
}

```

## Converters

这个接口处理命令行字符串到java类型的转换，并传递给参数给方法，这些参数被@Cli*相关注解修饰。

默认情况下，常见的类型的转换器已经注册了，常见的类型是涉及到基本类型、Date、Character、File。

可以自己实现一个Converter接口的实例，通过spring-shell-plugin.xml文件注册到spring容器。


# Built in commands

spring shell提供了一些内置命令

- clr or clear 
清除console

- date 
显示时间日期

- exit or quit
退出shell，相当于关闭了这个java进程

- help 
列出当前命令和它们的使用方法，这个命令超级棒！！！

- ! ${操作系统命令}
可以通过这个执行操作系统的命令，是不是有一点像vim的命令了？

- version
列出当前的spring shell版本

可能一些命令这里没写出来，但是通过上面的 **==help==** 命令可以全部查出来

# Customizing the shell

官方文档给出了3个扩展点。

## BannerProvider

用过spring boot的胖友们应该晓得这个东西是什么鬼吧。

之前的demo运行的话就知道，会出现 spring shell这个图标，version信息，welcome信息，这个东西就是这个作用。

```java
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
```

不要问我为什么要加这两个注解，看它的默认实现就知道了(DefaultBannerProvider)

另外知道spring boot的胖友又知道了吧，还支持从文件从读取喔。

## PromptProvider

这个东西不太好翻译，看看官网的解释：

`Specifies the command prompt text, eg. "shell>" or "#" or "$". This will be called after every command execution so it does not need to be a static string.`

直接上代码

```java
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
        return "(=.=)>";
    }

    public String getProviderName() {
        return "HadesJK";
    }
}
```

同样，要看解释，直接看默认实现（DefaultPromptProvider）

## HistoryFileNameProvider

历史命令记录文件。

```java
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
```


