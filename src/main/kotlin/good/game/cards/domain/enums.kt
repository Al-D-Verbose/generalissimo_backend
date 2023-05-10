package good.game.cards.domain

enum class CardType{ INFANTRY, VEHICLE, PLANE , STRUCTURE, COMMAND, SPY, RESOURCE, MODIFICATION }

enum class CardPropertyType { FLY, RANGE, PIERCING, AREA }

enum class CardModifierType { MOVEMENT, ATTACK_DEFAULT, ATTACK_BONUS, ARMOR }

enum class CardEffect { BURN, STUN }

enum class GameResourceType { GOLD, OIL, AMMO, RESEARCH, SPY }

enum class CardGlobalFaction { ALLIANCE, AXIS, NEUTRAL }

enum class CardFaction { UNITED_KINGDOM, FRANCE, UNITED_STATES, UDSSR, THIRD_REICH, ITALY, JAPAN, FINLAND, NEUTRAL }

enum class ActionCost { INSTANT, DELAYED }