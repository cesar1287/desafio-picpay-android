package cesar1287.com.github.desafiopicpay.core.api

data class Resource(val status: Status, val message: String?, val data: Any?) {

    companion object {
        fun error(msg: String): Resource {
            return Resource(Status.ERROR, msg, null)
        }

        fun success(data: Any?): Resource {
            return Resource(Status.SUCCESS, null, data)
        }
    }
}