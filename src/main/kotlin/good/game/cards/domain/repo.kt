package good.game.cards.domain

import good.game.cards.model.Card
import good.game.cards.model.CardAction
import good.game.cards.model.CardProperty
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CardRepository: JpaRepository<Card, Long>{
    fun getCardByNumber(number: UUID): Card?
}

interface CardActionRepository: JpaRepository<CardAction, Long>

interface CardPropertyRepository: JpaRepository<CardProperty, Long>