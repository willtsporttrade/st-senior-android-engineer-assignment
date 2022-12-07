package com.getsporttrade.assignment.service.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.getsporttrade.assignment.service.cache.dao.PositionDao
import com.getsporttrade.assignment.service.cache.entity.Position
import java.math.BigDecimal

/**
 * Room Database for caching data in an in-memory data store
 */
@Database(
    entities = [
        Position::class
               ],
    version = 1,
    exportSchema = false
)
@TypeConverters(LocalDatabaseTypeConverters::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun positionDao(): PositionDao

    companion object {
        const val DATABASE_NAME = "application_local_database"
    }
}

/**
 * Room Database Type Converter for managing the storage of BigDecimal values
 */
class LocalDatabaseTypeConverters {
    @TypeConverter
    fun bigDecimalToString(value: BigDecimal?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun stringToBigDecimal(value: String?): BigDecimal? {
        return value?.toBigDecimal()
    }
}
