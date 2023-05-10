package good.game.cards

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
@ComponentScan("good.game.cards")
@EnableWebMvc
class CardsApplication

fun main(args: Array<String>) {
	runApplication<CardsApplication>(*args)
}
