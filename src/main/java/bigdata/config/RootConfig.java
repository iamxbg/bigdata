package bigdata.config;

import javax.servlet.annotation.WebListener;

import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.context.annotation.ComponentScan.Filter;

@Configuration
@ComponentScan(basePackages="bigdata"
	,excludeFilters={@Filter(type=FilterType.ANNOTATION,classes={Controller.class,Configuration.class})})
public class RootConfig {

		/**
		 * Configuration for Quartz
		 */

	
}
