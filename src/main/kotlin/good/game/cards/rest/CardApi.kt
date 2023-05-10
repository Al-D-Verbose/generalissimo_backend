package good.game.cards.rest

import com.fasterxml.jackson.core.JsonParseException
import good.game.cards.domain.CardActionDto
import good.game.cards.domain.CardDto
import good.game.cards.domain.CardPropertyDto
import good.game.cards.domain.DeleteCardDto
import good.game.cards.service.CardService
import org.springframework.http.HttpStatus
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.lang.Exception
import java.util.*
import javax.persistence.EntityExistsException
import javax.persistence.EntityNotFoundException

interface CardApi {
    fun getAllCards(): List<CardDto>
    fun getAllCardActions(): List<CardActionDto>
    fun getAllCardProperties(): List<CardPropertyDto>
    fun getCard(id: Long): CardDto?
    fun getCardAction(id: Long): CardActionDto?
    fun getCardProperty(id: Long): CardPropertyDto?
    fun createCard(cardDto: CardDto): Any
    fun createCardAction(cardActionDto: CardActionDto): Any
    fun createCardProperty(cardPropertyDto: CardPropertyDto): Any
    fun updateCard(cardDto: CardDto): Any
    fun deleteCard(deleteCardDto: DeleteCardDto): Any
}

@CrossOrigin("localhost")
@RestController
@RequestMapping("api", produces = ["application/json"], consumes = ["application/json"])
class CardApiV1Impl(
        val cardService: CardService
): CardApi{

    @PutMapping("/cards/update")
    override fun updateCard(@RequestBody cardDto: CardDto): Any = handleApplicationCall { cardService.updateCard(cardDto)}

    @GetMapping("/cards/list")
    override fun getAllCards(): List<CardDto> = handleApplicationCall { cardService.getAllCards() }

    @GetMapping("/actions/list")
    override fun getAllCardActions(): List<CardActionDto> = handleApplicationCall { cardService.getAllCardActions() }

    @GetMapping("/props/list")
    override fun getAllCardProperties(): List<CardPropertyDto> = handleApplicationCall { cardService.getAllCardProperties() }

    @GetMapping("/cards/{id}")
    override fun getCard(@PathVariable("id") id: Long): CardDto? = handleApplicationCall { cardService.getCard(id) }

    @GetMapping("/actions/{id}")
    override fun getCardAction(@PathVariable("id") id: Long): CardActionDto? = handleApplicationCall { cardService.getCardAction(id) }

    @GetMapping("/props/{id}")
    override fun getCardProperty(@PathVariable("id") id: Long): CardPropertyDto? = handleApplicationCall { cardService.getCardProperty(id) }

    @PostMapping("/cards/new")
    override fun createCard(@RequestBody(required = true) cardDto: CardDto): Any = handleApplicationCall { cardService.createCard(cardDto) }

    @PostMapping("/cards/delete")
    override fun deleteCard(@RequestBody deleteCardDto: DeleteCardDto): DeleteCardDto = handleApplicationCall { cardService.deleteCard(deleteCardDto.number) }

    @PostMapping("/actions/new")
    override fun createCardAction(@RequestBody(required = true) cardActionDto: CardActionDto): Any = handleApplicationCall { cardService.createCardAction(cardActionDto) }
    @PostMapping("/props/new")
    override fun createCardProperty(@RequestBody(required = true) cardPropertyDto: CardPropertyDto): Any = handleApplicationCall { cardService.createCardProperty(cardPropertyDto) }

    private inline fun <T> handleApplicationCall(call: () -> T): T {
        return try {
            call()
        } catch (exception: Exception) {
            when (exception) {
                is EntityNotFoundException, is JpaObjectRetrievalFailureException -> throw ResponseStatusException(HttpStatus.NOT_FOUND, exception.localizedMessage, exception)
                is JsonParseException -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.localizedMessage, exception)
                is EntityExistsException -> throw ResponseStatusException(HttpStatus.CONFLICT, exception.localizedMessage, exception)
                else -> throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.localizedMessage, exception)
            }
        }
    }
}