package vo

import java.math.BigDecimal

class CurrenciesQuotesVo (
    val success: Boolean,
    val quotes: Map<String, BigDecimal>
)