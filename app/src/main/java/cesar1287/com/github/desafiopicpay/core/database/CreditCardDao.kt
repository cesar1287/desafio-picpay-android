package cesar1287.com.github.desafiopicpay.core.database

import androidx.room.*
import cesar1287.com.github.desafiopicpay.core.model.CreditCard

@Dao
interface CreditCardDao {

    @Query("SELECT * FROM credit_card")
    fun getAll(): List<CreditCard>

    @Query("SELECT * FROM credit_card WHERE id IN (:creditCardIds)")
    fun loadAllByIds(creditCardIds: IntArray): List<CreditCard>

    @Query("SELECT * FROM credit_card WHERE id = :creditCardId")
    fun loadById(creditCardId: Int): List<CreditCard>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(creditCard: CreditCard)

    @Delete
    fun delete(creditCard: CreditCard)
}