package good.game.cards.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.http.MediaType
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer





@Configuration
@EnableTransactionManagement
@EnableAsync
@Component
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactory",
        basePackages = ["good.game.cards"]
)
@Profile("db")
class DataConfiguration{

    @Bean("dataSource")
    @ConfigurationProperties(prefix = "spring.database")
    fun dataSource(): DataSource = DataSourceBuilder.create().build()

    @Bean("entityManagerFactory")
    fun localContainerEntityManagerFactoryBean(builder: EntityManagerFactoryBuilder, @Qualifier("dataSource") dataSource: DataSource): LocalContainerEntityManagerFactoryBean = builder.dataSource(dataSource)
            .packages("good.game.cards").persistenceUnit("database").properties(mapOf("hibernate.hbm2ddl.auto" to "update"))
            .build()

    @Bean("transactionManager")
    fun platformTransactionManager(@Qualifier("entityManagerFactory") entityManagerFactory: EntityManagerFactory): PlatformTransactionManager = JpaTransactionManager(entityManagerFactory)
}

@Configuration
@EnableWebMvc
class WebConfig : WebMvcConfigurerAdapter() {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("PUT")
    }


    override fun configureContentNegotiation(configurer: ContentNegotiationConfigurer) {
        configurer.favorPathExtension(true).favorParameter(true).ignoreAcceptHeader(true).useJaf(false).defaultContentType(MediaType.APPLICATION_JSON)
    }
}

@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http?.csrf()?.disable()?.cors()?.and()?.httpBasic()?.and()?.antMatcher("/**")?.authorizeRequests()?.anyRequest()?.authenticated()?.and()
    }


    override fun configure(auth: AuthenticationManagerBuilder){
        auth.inMemoryAuthentication().withUser("cardtest").password("{noop}bückelnmitbisi").roles("ADMIN")
    }

    @Bean
    fun corsConfigurationSource() : UrlBasedCorsConfigurationSource{
        val configuration: CorsConfiguration = CorsConfiguration()
        configuration.let { it.allowedOrigins = mutableListOf("*")
                            it.allowedMethods = mutableListOf("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                            it.allowedHeaders = mutableListOf("authorization", "content-type", "x-auth-token", "access-control-allow-origin", "access-control-allow-credentials")
                            it.exposedHeaders = mutableListOf("x-auth-token")}
        val source: UrlBasedCorsConfigurationSource = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}