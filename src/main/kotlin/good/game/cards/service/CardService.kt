package good.game.cards.service

import good.game.cards.domain.*
import good.game.cards.model.Card
import good.game.cards.model.CardAction
import good.game.cards.model.CardProperty
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional
import javax.validation.constraints.Null

interface CardService {
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
    fun deleteCard(number: UUID): DeleteCardDto
}

@Service
class CardServiceImpl(
        val cardRepository: CardRepository,
        val cardActionRepository: CardActionRepository,
        val cardPropertyRepository: CardPropertyRepository
) : CardService{
    @Transactional
    override fun updateCard(cardDto: CardDto): Any {
        val newCard = assembleCard(cardDto, cardRepository.getCardByNumber(cardDto.number!!).let { it?.id } ?: 0)
        newCard.actions.forEach { cardActionRepository.save(it) }
        return cardRepository.save(newCard)
    }

    override fun getAllCards(): List<CardDto> = cardRepository.findAll().map { assembleCardDto(it) }

    override fun getAllCardActions(): List<CardActionDto> = cardActionRepository.findAll().map { assembleCardActionDto(it) }

    override fun getAllCardProperties(): List<CardPropertyDto> = cardPropertyRepository.findAll().map { assembleCardPropertyDto(it) }

    override fun getCard(id: Long): CardDto? = cardRepository.findById(id).orNull().let { assembleCardDto(it!!) }

    override fun getCardAction(id: Long): CardActionDto? = cardActionRepository.findById(id).orNull().let { assembleCardActionDto(it!!) }

    override fun getCardProperty(id: Long): CardPropertyDto? = cardPropertyRepository.findById(id).orNull().let { assembleCardPropertyDto(it!!) }

    override fun createCard(cardDto: CardDto): Any {
        val newCard = assembleCard(cardDto)
        newCard.actions.forEach { cardActionRepository.save(it) }
        newCard.properties.forEach { cardPropertyRepository.save(it) }
        return cardRepository.save(newCard)
    }

    override fun createCardAction(cardActionDto: CardActionDto): Any = cardActionRepository.save((assembleCardAction(cardActionDto)))

    override fun createCardProperty(cardPropertyDto: CardPropertyDto): Any = cardPropertyRepository.save(assembleCardProperty(cardPropertyDto))

    override fun deleteCard(number: UUID): DeleteCardDto {
        val cardToDelete: Card? = cardRepository.getCardByNumber(number)
        cardToDelete.let { cardRepository.delete(it!!) }
        return DeleteCardDto(number)
    }

    fun <T> Optional<T>.orNull(): T? = orElse(null)
}