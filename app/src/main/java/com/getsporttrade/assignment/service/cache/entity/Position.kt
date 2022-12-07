package com.getsporttrade.assignment.service.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomMasterTable.TABLE_NAME
import com.getsporttrade.assignment.extension.stubPrice
import com.getsporttrade.assignment.extension.stubQuantity
import com.google.gson.annotations.SerializedName
import org.json.JSONObject
import java.math.BigDecimal
import kotlin.random.Random.Default.nextBoolean

/**
 * Database entity for representing Position data
 */
@Entity(tableName = Position.TABLE_NAME)
data class Position(
    /**
     * The position's unique identifier
     */
    @PrimaryKey
    @ColumnInfo(name = "identifier")
    override val identifier: String,

    /**
     * The position name (e.g. DET Pistons)
     */
    @ColumnInfo(name = "position_name")
    val name: String,

    /**
     * The criteria name for the position (e.g. To win)
     */
    @SerializedName("criteria_name")
    @ColumnInfo(name = "position_criteria_name")
    val criteriaName: String,

    /**
     * The story name for the position (e.g. DET Pistons @ MIA Heat)
     */
    @SerializedName("story_name")
    @ColumnInfo(name = "position_story_name")
    val storyName: String,

    /**
     * BigDecimal value for the position price
     */
    @ColumnInfo(name = "position_price")
    val price: BigDecimal,

    /**
     * BigDecimal value for the position quantity of contracts
     */
    @ColumnInfo(name = "position_quantity")
    val quantity: BigDecimal
) : EntityIdentifiable {
    companion object : EntityStubbable {
        const val TABLE_NAME = "position_tbl"

        /**
         * Static list of possible position names and stories
         */
        private val positionNamesWithStories = listOf(
            Pair("LA Lakers", "LA Lakers @ CLE Cavaliers"),
            Pair("DAL Mavericks", "DAL Mavericks @ DEN Nuggets"),
            Pair("DET Pistons", "DET Pistons @ MIA Heat"),
            Pair("BAL Ravens", "BAL Ravens @ PIT Steelers"),
            Pair("NY Jets", "NY Jets @ PHI Eagles"),
            Pair("DET Lions", "DET Lions @ GB Packers"),
            Pair("CHI Blackhawks", "CHI Blackhawks @ PIT Penguins"),
            Pair("DET Red Wings", "DET Red Wings @ COL Blue Jackets"),
            Pair("STL Blues", "ST Blues @ NY Islanders")
        )

        /**
         * Enum for creating some appropriate name strings based on the criteria
         */
        private enum class Criterion {
            TO_WIN,
            WIN_OR_LOSE,
            COMBINED_POINTS_OVER,
            COMBINED_POINTS_UNDER;

            /**
             * Appropriate criteria name for the type
             */
            val criterionName: String
                get() {
                    return when (this) {
                        TO_WIN -> "To win"
                        WIN_OR_LOSE -> "Win or lose by less than 3.5"
                        COMBINED_POINTS_OVER -> "Combined points over 164.5"
                        COMBINED_POINTS_UNDER -> "Combined points under 164.5"
                    }
                }

            /**
             * Formatted position name based on criteria
             */
            fun positionNameFormatted(positionName: String): String {
                return when (this) {
                    TO_WIN -> positionName
                    WIN_OR_LOSE -> "$positionName + 3.5"
                    COMBINED_POINTS_OVER -> "Over 164.5"
                    COMBINED_POINTS_UNDER -> "Under 164.5"
                }
            }
        }

        /**
         * A JSONObject stub for a random position object
         */
        override fun stub(identifier: String): JSONObject {
            val criterion = Criterion.values().random()
            val positionNameWithStory = positionNamesWithStories.random()
            return JSONObject().apply {
                put("identifier", identifier)
                put("name", criterion.positionNameFormatted(positionNameWithStory.first))
                put("criteria_name", criterion.criterionName)
                put("story_name", positionNameWithStory.second)
                put("price", BigDecimal::class.stubPrice<BigDecimal>())
                put("quantity", BigDecimal::class.stubQuantity<BigDecimal>(nextBoolean()))
            }
        }
    }
}
