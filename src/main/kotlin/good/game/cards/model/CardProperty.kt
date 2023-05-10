package good.game.cards.model

import good.game.cards.domain.CardModifierType
import good.game.cards.domain.CardPropertyType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class CardProperty(
        @Id @GeneratedValue val id: Long,
        val name: String,
        val propertyType: CardPropertyType,
        val level: Int,
        val modifierType: CardModifierType,
        val modifierValue: Int
)