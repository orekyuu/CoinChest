package net.orekyuu.coinchest.infrastructure.doma.entity

import net.orekyuu.coinchest.time.SystemTimePeriod
import net.orekyuu.coinchest.time.SystemTimeSpan
import net.orekyuu.coinchest.time.UserTimePeriod
import net.orekyuu.coinchest.time.UserTimeSpan
import org.seasar.doma.Embeddable
import java.time.LocalDateTime

@Embeddable
class SystemTimeSpanColumn(val systemIn: LocalDateTime, val systemOut: LocalDateTime) {

    constructor(span: SystemTimeSpan): this(span.start, span.end)

    fun toDomain() = SystemTimeSpan(SystemTimePeriod(systemIn), SystemTimePeriod(systemOut))
}

@Embeddable
class UserTimeSpanColumn(val userIn: LocalDateTime, val userOut: LocalDateTime) {

    constructor(span: UserTimeSpan): this(span.start, span.end)

    fun toDomain() = UserTimeSpan(UserTimePeriod(userIn), UserTimePeriod(userOut))
}