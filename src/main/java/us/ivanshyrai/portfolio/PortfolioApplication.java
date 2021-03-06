package us.ivanshyrai.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.convert.Jsr310Converters;
import us.ivanshyrai.portfolio.property.FileStorageProperties;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EntityScan(basePackageClasses = {
        PortfolioApplication.class,
        Jsr310Converters.class
})
@EnableConfigurationProperties({FileStorageProperties.class})
public class PortfolioApplication {

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(PortfolioApplication.class, args);
    }
}
