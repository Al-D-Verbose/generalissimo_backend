package good.game.cards.domain

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import good.game.cards.model.Card
import good.game.cards.model.CardAction
import good.game.cards.model.CardProperty
import java.util.*


data class CardDto @JsonCreator @JsonIgnoreProperties(ignoreUnknown = true) constructor(
        @JsonProperty("name")
    var name: String,
        @JsonProperty("type")
    var type: CardType,
        @JsonProperty("summonCost")
    var summonCost: Int?,
        @JsonProperty("turnCost")
    var turnCost: Int?,
        @JsonProperty("actions")
    var actions: List<CardActionDto>,
        @JsonProperty("properties")
    var properties: List<CardPropertyDto>,
        @JsonProperty("description")
    var description: String,
        @JsonProperty("globalFaction")
        var globalFaction: CardGlobalFaction,
        @JsonProperty("faction")
        var faction: CardFaction,
        @JsonProperty("health")
        var health: Int?,
        @JsonProperty("armor")
        var armor: Int?,
        @JsonProperty("summonCostType")
        var summonCostType: GameResourceType,
        @JsonProperty("turnCostType")
        var turnCostType: GameResourceType?,
        @JsonProperty("minRange")
        var minRange: Int?,
        @JsonProperty("maxRange")
        var maxRange: Int?,
        @JsonProperty("summonActionCost")
        var summonActionCost: ActionCost?,
        @JsonProperty("movementRange")
        var movementRange: Int?,
        @JsonProperty("lineOfSight")
        var lineOfSight: Int?,
        @JsonProperty("number")
        var number: UUID?,
        @JsonProperty("image")
        var image: String
)

data class CardActionDto @JsonCreator @JsonIgnoreProperties(ignoreUnknown = true) constructor(
        @JsonProperty("id")
        var id: Long,
        @JsonProperty("name")
        var name: String,
        @JsonProperty("description")
        var description: String,
        @JsonProperty("effects")
        var effects: List<CardEffect>,
        @JsonProperty("cost")
        var cost: Int,
        @JsonProperty("costType")
        var costType: GameResourceType,
        @JsonProperty("effectRequirements")
        var effectRequirements: List<Int>,
        @JsonProperty("attack")
        var attack: Int,
        @JsonProperty("attackEffect")
        var attackEffect: CardPropertyType?,
        @JsonProperty("attackEffectLevel")
        var attackEffectLevel: Int

)

data class CardPropertyDto @JsonCreator @JsonIgnoreProperties(ignoreUnknown = true) constructor(
        @JsonProperty("name")
        var name: String,
        @JsonProperty("type")
        var type: CardPropertyType,
        @JsonProperty("level")
        var level: Int,
        @JsonProperty("modifierType")
        var modifierType: CardModifierType,
        @JsonProperty("modifierValue")
        var modifierValue: Int
)

data class DeleteCardDto @JsonCreator @JsonIgnoreProperties(ignoreUnknown = true) constructor(
        @JsonProperty("number")
        val number: UUID
)

fun assembleCardDto(card: Card) = CardDto(  card.name,
                                            card.type,
                                            card.summonCost,
                                            card.turnCost,
                                            card.actions.map { assembleCardActionDto(it) },
                                            card.properties.map { assembleCardPropertyDto(it) },
                                            card.description.let { it ?: "" },
                                            card.globalFaction,
                                            card.faction,
                                            card.health,
                                            card.armor,
                                            card.summonCostType,
                                            card.turnCostType,
                                            card.minRange,
                                            card.maxRange,
                                            card.summonActionCost,
                                            card.movementRange,
                                            card.lineOfSight,
                                            card.number,
                                            card.imgDataUrl)

fun assembleCardActionDto(cardAction: CardAction) = CardActionDto(  cardAction.id,
                                                                    cardAction.name,
                                                                    cardAction.description!!,
                                                                    cardAction.effects,
                                                                    cardAction.cost,
                                                                    cardAction.costType,
                                                                    cardAction.effectRequirements,
                                                                    cardAction.attack,
                                                                    cardAction.attackEffect,
                                                                    cardAction.attackEffectLevel)

fun assembleCardPropertyDto(cardProperty: CardProperty) = CardPropertyDto(  cardProperty.name,
                                                                            cardProperty.propertyType,
                                                                            cardProperty.level,
                                                                            cardProperty.modifierType,
                                                                            cardProperty.modifierValue)

fun assembleCardProperty(cardPropertyDto: CardPropertyDto) = CardProperty(0,
                                                                            cardPropertyDto.name,
                                                                            cardPropertyDto.type,
                                                                            cardPropertyDto.level,
                                                                            cardPropertyDto.modifierType,
                                                                            cardPropertyDto.modifierValue)

fun assembleCardAction(cardActionDto: CardActionDto) = CardAction(  cardActionDto.id,
                                                                    cardActionDto.name,
                                                                    cardActionDto.description,
                                                                    cardActionDto.effects,
                                                                    cardActionDto.cost,
                                                                    cardActionDto.costType,
                                                                    cardActionDto.effectRequirements,
                                                                    cardActionDto.attack,
                                                                    cardActionDto.attackEffect,
                                                                    cardActionDto.attackEffectLevel)

fun assembleCard(cardDto: CardDto, id: Long = 0) = Card(id,
                                            cardDto.name,
                                            cardDto.type,
                                            cardDto.summonCost ?: 0,
                                            cardDto.turnCost ?: 0,
                                            cardDto.summonCostType,
                                            cardDto.turnCostType,
                                            cardDto.actions.map { assembleCardAction(it) },
                                            cardDto.properties.map { assembleCardProperty(it) },
                                            cardDto.description,
                                            cardDto.globalFaction,
                                            cardDto.faction,
                                            cardDto.health,
                                            cardDto.armor,
                                            cardDto.minRange,
                                            cardDto.maxRange,
                                            cardDto.summonActionCost,
                                            cardDto.movementRange,
                                            cardDto.lineOfSight,
                                            cardDto.number ?: UUID.randomUUID(),
                                            cardDto.image)