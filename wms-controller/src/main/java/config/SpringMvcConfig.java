package config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.nf.commons.scan.JacksonObjectMapper;
import com.nf.commons.uilts.DateConverter;
import org.springframework.context.annotation.*;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Spring的WebMvcConfigurer配置
 * https://blog.csdn.net/qq_40981804/article/details/88898174
 * 参考资料
 */
//配置类
@Configuration
//来导入配置类或者导入一个带有@Component等注解要放入Spring容器中的类
//@Import(BasicConfig.class)
//这个注解是用来导入其它的xml文件
@ImportResource("classpath:spring/mvc-config.xml")
//ComponentScan类似于xml中的context:componentScan要求被管理的bean有相关的的注解
@EnableAspectJAutoProxy(proxyTargetClass = true)
//spring扫描组件
@ComponentScan(
        value = {"com.nf.commons.scan","com.nf.controller"}
)
/* 启用 MVC 和 注解驱动*/
@EnableWebMvc

public class SpringMvcConfig implements WebMvcConfigurer  {

    /**
     * 配置请求视图映射
     * @return
     */
    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver()
    {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        //请求视图文件的前缀地址
        internalResourceViewResolver.setPrefix("/WEB-INF/views/");
        //请求视图文件的后缀
        internalResourceViewResolver.setSuffix(".jsp");
        return internalResourceViewResolver;
    }


    /**
     * 视图配置
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(getInternalResourceViewResolver());
    }

    /**
     * 配置文件上传的大小10MB
     * @return
     */
    @Bean
    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(10485760);
        commonsMultipartResolver.setDefaultEncoding("UTF-8");
        return commonsMultipartResolver;
    }

    /**
     * 静态文件
     * setCachePeriod：(31556926)10M
     * 指定此资源处理程序提供的资源的缓存周期(以秒为单位)。
     * 默认情况下不发送任何缓存标头，
     * 而是仅依赖于最后修改的时间戳。将其设置为0以发送阻止缓存的缓存标头，
     * 或设置为正数秒，以发送缓存头与给定的最大年龄值。
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        ResourceHandlerRegistration resourceHandlerRegistration = registry.addResourceHandler("/static/**","favicon.ico");
        resourceHandlerRegistration.addResourceLocations("/static/","/static/style/images/");
        resourceHandlerRegistration.setCachePeriod(31556926);
    }


    /**
     *  配置消息转换器
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(stringHttpMessageConverter());
        converters.add(getMappingJackson2HttpMessageConverter());
    }

    /**
     * StandardCharsets 是标准字符集的类
     * @return
     */
    @Bean
    public HttpMessageConverter<String> stringHttpMessageConverter(){
        //修改StringHttpMessageConverter默认配置
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        return  stringHttpMessageConverter;
    }

    /**
     * 当数据库字段为date类型时，@ResponseBody注解在转换日期类型时会默认把日期转换为时间戳
     * （例如： date：2017-10-25  转换为 时间戳：15003323990）。
     * 此时有两种方式可以解决日期转换Json问题：
     * 1、（不推荐）局部修改，自定义注解进行将日期类型转换为Date类型。
     * 2、（强烈推荐）全局修改，用MappingJackson2HttpMessageConverter配置在XML（SpringMVC）
     * 或者 配置在Application.Java启动类（Spring boot、Spring cloud）。
     *
     * 我的参考文章：https://blog.csdn.net/m0_38016299/article/details/78338048
     *
     * 如果想要单个bean的某个日期字段显示年月日时分秒的话，
     * 只需要在对应日期的get方法上添加@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")即可。
     *     <bean id="jacksonMessageConverter" class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
     *         <property name="objectMapper" ref="jacksonObjectMapper" />
     *         <property name="supportedMediaTypes">
     *             <list>
     *                 <value>text/html;charset=UTF-8</value>
     *                 <value>application/json;charset=UTF-8</value>
     *             </list>
     *         </property>
     *     </bean>
     *         list.add(MediaType.TEXT_HTML);
     *         list.add(MediaType.APPLICATION_JSON);
     *         list.add(MediaType.APPLICATION_JSON_UTF8);
     */
    @Bean
    public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
        Jackson2ObjectMapperBuilder.json();
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter =
                new MappingJackson2HttpMessageConverter();
        //日期设置
        JacksonObjectMapper jacksonObjectMapper = new JacksonObjectMapper();
        //忽略未知属性 防止解析报错
        jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mappingJackson2HttpMessageConverter.setObjectMapper(jacksonObjectMapper);

        List<MediaType> list = new ArrayList<>();
        //类型 子类型 字符编码
        list.add(new MediaType("text","html",Charset.forName("UTF-8")));
        list.add(new MediaType("application","json",Charset.forName("UTF-8")));

        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(list);
        return  mappingJackson2HttpMessageConverter;
    }

    /**
     *  添加类型转换器和格式化器:
     *  将普通转换器添加到此注册表。
     *  可转换源目标类型对派生自转换器的参数化类型。
     *  如果参数化类型不能被解析，则抛出IllegalArgumentException
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new DateConverter());
    }

}
