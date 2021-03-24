package site.minnan.entry;

import cn.hutool.core.util.StrUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

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
    public static Function<String, Optional<String>> notBlank(){
        return s -> StrUtil.isNotBlank(s) ? Optional.of(s) : Optional.empty();
    }
}
