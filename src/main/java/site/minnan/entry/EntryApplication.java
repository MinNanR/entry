package site.minnan.entry;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import java.util.Date;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

@SpringBootApplication
@MapperScan("site.minnan.entry.domain.mapper")
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableScheduling
@EnableCaching
public class EntryApplication {

    public static void main(String[] args) {
        SpringApplication.run(EntryApplication.class, args);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
        c.setIgnoreUnresolvablePlaceholders(true);
        return c;
    }

    @Bean(name = "BlankFilter")
    public static Function<String, Optional<String>> notBlank() {
        return s -> StrUtil.isNotBlank(s) ? Optional.of(s) : Optional.empty();
    }

    @Bean(name = "EndOfDayString")
    public static Function<String, String> endOfDateString() {
        return s -> DateUtil.format(DateUtil.endOfDay(DateUtil.parse(s, "yyyy-MM-dd", "yyyy-MM-dd HH:mm")), "yyyy-MM-dd HH:mm:ss");
    }

    @Bean(name = "EndOfDay")
    public static Function<String, DateTime> endOfDate() {
        return s -> DateUtil.endOfDay(DateUtil.parse(s, "yyyy-MM-dd", "yyyy-MM-dd HH:mm"));
    }
}
