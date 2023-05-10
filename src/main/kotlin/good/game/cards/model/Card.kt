package good.game.cards.model

import good.game.cards.domain.*
import java.util.*
import javax.persistence.*

@Entity
data class Card(
        @Id @GeneratedValue val id: Long,
        val name: String,
        val type: CardType,
        val summonCost: Int,
        val turnCost: Int,
        val summonCostType: GameResourceType,
        val turnCostType: GameResourceType?,
        @ElementCollection var actions: List<CardAction>,
        @ElementCollection var properties: List<CardProperty>,
        val description: String?,
        val globalFaction: CardGlobalFaction,
        val faction: CardFaction,
        val health: Int?,
        val armor: Int?,
        val minRange: Int?,
        val maxRange: Int?,
        val summonActionCost: ActionCost?,
        val movementRange: Int?,
        val lineOfSight: Int?,
        val number: UUID,
        @Lob
        val imgDataUrl: String
)