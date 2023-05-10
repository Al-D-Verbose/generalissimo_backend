package good.game.cards.model

import good.game.cards.domain.CardEffect
import good.game.cards.domain.CardPropertyType
import good.game.cards.domain.GameResourceType
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class CardAction(
        @Id @GeneratedValue val id: Long,
        val name: String,
        val description: String?,
        @ElementCollection val effects: List<CardEffect>,
        val cost: Int = 0,
        val costType: GameResourceType,
        @ElementCollection val effectRequirements: List<Int>,
        val attack: Int,
        val attackEffect: CardPropertyType?,
        val attackEffectLevel: Int
        )