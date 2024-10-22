package hu.zeletrik.couponsvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.List;

/**
 * Common configurations.
 */
@Configuration
public class AppConfig {

    @Bean
    @Primary
    public ConversionService conversionService(List<Converter<?, ?>> converters) {
        var factory = new ConversionServiceFactoryBean();
        factory.setConverters(new HashSet<>(converters));
        factory.afterPropertiesSet();
        return factory.getObject();
    }
}
