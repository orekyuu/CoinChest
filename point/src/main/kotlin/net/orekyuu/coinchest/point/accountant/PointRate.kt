package net.orekyuu.coinchest.point.accountant

import java.math.BigDecimal

/**
 * ポイントレート
 *
 *　前受金/有償ポイント発行数で求められる推定ポイント単価
 */
class PointRate(val value: BigDecimal)