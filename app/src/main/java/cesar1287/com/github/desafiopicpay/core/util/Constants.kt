package cesar1287.com.github.desafiopicpay.core.util

object Api {
    const val BASE_URL = "http://careers.picpay.com/tests/mobdev/"
}

object Error {
    const val ERROR_DEFAULT = "Erro desconhecido, por favor, tente novamente"
    const val ERROR_TRANSACTION = "Transação não autorizada, tente novamente"
}

object CreditCard {
    const val KEY_HASH_CARD_NUMBER = "cardNumber"
    const val KEY_HASH_NAME = "name"
    const val KEY_HASH_EXPIRY_DATE = "expiryDate"
    const val KEY_HASH_CVV = "cvv"
    const val KEY_HASH_ID = "id"
    const val KEY_EXTRA_CREDIT_CARD_LIST = "creditCardList"
}

object Mask {
    const val MASK_CREDIT_CARD = "#### #### #### ####"
    const val MASK_EXPIRE_DATE = "##/##"
    const val MASK_CVV = "###"
}

object Home {
    const val KEY_EXTRA_USER = "user"
}

object Payment {
    const val API_DESTINATION_USER_ID = "destination_user_id"
    const val API_VALUE = "value"
    const val API_CARD_NUMBER = "card_number"
    const val API_EXPIRY_DATE = "expiry_date"
    const val API_CVV = "cvv"
    const val KEY_EXTRA_TRANSACTION = "transaction"
    const val KEY_EXTRA_CREDIT_CARD = "creditCard"
}

object Main {
    const val KEY_CODE_RECEIPT = 1
}