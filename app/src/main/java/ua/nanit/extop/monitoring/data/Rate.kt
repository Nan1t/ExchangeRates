package ua.nanit.extop.monitoring.data

data class Rate(
    val exchanger: String,
    var amountIn: Float,
    var amountOut: Float,
    val minAmount: Int,
    val fund: Int,
    val link: String,
    val reviewsLink: String,
    val isManual: Boolean,
    val isMediator: Boolean,
    var active: Boolean = true
)