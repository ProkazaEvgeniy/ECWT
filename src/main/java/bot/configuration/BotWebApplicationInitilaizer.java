package bot.configuration;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import bot.listener.ApplicationListener;

public class BotWebApplicationInitilaizer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		WebApplicationContext ctx = createWebApplicationContext(servletContext);
		
		servletContext.addListener(ctx.getBean(ApplicationListener.class));
		
		registerFilters(servletContext, ctx);
		
	}

	private WebApplicationContext createWebApplicationContext(ServletContext servletContext) {
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.scan("bot.configuration");
		ctx.setServletContext(servletContext);
		ctx.refresh();
		return ctx;
	}
	
	private void registerFilters(ServletContext servletContext, WebApplicationContext ctx) {
		registerFilter(servletContext, new CharacterEncodingFilter("UTF-8", true));
		
	}
	
	private void registerFilter(ServletContext servletContext, Filter filter, String... filterNames) {
		String filterName = filterNames.length > 0 ? filterNames[0] : filter.getClass().getSimpleName();
		servletContext.addFilter(filterName, filter).addMappingForUrlPatterns(null, true, "/*");
	}
	
}
