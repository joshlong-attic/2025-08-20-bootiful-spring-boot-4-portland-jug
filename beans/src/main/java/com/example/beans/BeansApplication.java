package com.example.beans;

import org.springframework.beans.factory.BeanRegistrar;
import org.springframework.beans.factory.BeanRegistry;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Locale;
import java.util.Map;

@Import(CartsBeanRegistrar.class)
@SpringBootApplication
public class BeansApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(BeansApplication.class, args);
        Thread.sleep(60_000);
    }

    @Bean
    ApplicationRunner runner(Map<String, LocaleAwareCart> carts) {
        return _ -> carts.forEach((k, v) -> System.out.println(k + " : " + v));
    }


}

class CartsBeanRegistrar implements BeanRegistrar {

    @Override
    public void register(BeanRegistry registry, Environment env) {

        var property = env.getProperty("locale.carts.enabled", Boolean.class, false);
        if (property) {
            registry.registerBean("defaultLocaleAwareCart", LocaleAwareCart.class);
        }

        Locale.availableLocales().filter(l -> l.toLanguageTag().toLowerCase(Locale.ROOT).contains("fr"))
                .forEach(locale -> registry
                        .registerBean(
                                locale.toLanguageTag() + "Cart",
                                LocaleAwareCart.class, spec -> spec
                                        .description("locale-aware Cart for " + locale.toLanguageTag() + ".")
                                        .supplier(supplierContext -> {
                                            //    var ds = supplierContext.bean(DataSource.class);
                                            var dataSources = supplierContext.beanProvider(DataSource.class);
                                            if (dataSources.getIfAvailable() != null) {

                                            }

                                            return new LocaleAwareCart(locale);
                                        })

                        ));

        //     registry.registerBean("defaultLocaleAwareCart", LocaleAwareCart.class);

    }
}


class LocaleAwareCart {

    private final Locale locale;

    LocaleAwareCart() {
        this(Locale.getDefault());
    }

    LocaleAwareCart(Locale locale) {
        this.locale = locale;

    }

    @Override
    public String toString() {
        return "LocaleAwareCart{" +
                "locale=" + locale +
                '}';
    }
}
