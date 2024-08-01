package ru.lorderi.androktor.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.lorderi.androktor.model.ActivityType
import ru.lorderi.androktor.model.ClientCompany

@Entity(tableName = "Companies")
class CompanyEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,
    @ColumnInfo(name = "name")
    val name: String = "",
    @ColumnInfo(name = "activity")
    val activity: String = "",
) {
    companion object {
        fun fromCompany(company: ClientCompany): CompanyEntity = with(company) {
            CompanyEntity(
                id = id,
                name = name,
                activity = activity.toString(),
            )
        }
    }

    fun toCompany(): ClientCompany = ClientCompany(
        id = id,
        name = name,
        activity = ActivityType.valueOf(activity),
    )
}
