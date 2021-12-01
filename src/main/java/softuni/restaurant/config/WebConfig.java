package softuni.restaurant.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import softuni.restaurant.web.employees.interceptor.StatsInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  private final LocaleChangeInterceptor changeInterceptor;
  private final StatsInterceptor statsInterceptor;

  public WebConfig(LocaleChangeInterceptor changeInterceptor, StatsInterceptor statsInterceptor) {
    this.changeInterceptor = changeInterceptor;
    this.statsInterceptor = statsInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {

    registry.addInterceptor(changeInterceptor);
    registry.addInterceptor(statsInterceptor);
  }
}

