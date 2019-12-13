package net.orekyuu.coinchest.point

data class UsageCategoryId(val value: String)

data class UsageCategoryName(val value: String)

/**
 * 利用区分
 */
class UsageCategory(val id: UsageCategoryId, val name: UsageCategoryName)