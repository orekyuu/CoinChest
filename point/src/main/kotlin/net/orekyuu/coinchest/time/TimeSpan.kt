package net.orekyuu.coinchest.time

import java.time.Clock
import java.time.LocalDateTime

private val MAX_TIME = LocalDateTime.of(9999, 12, 31, 23, 59, 59)

/**
 * 期間
 */
sealed class TimeSpan(val start: LocalDateTime, val end: LocalDateTime) {
}

/**
 * ユーザー時間
 */
class UserTimePeriod(val value: LocalDateTime) {
    companion object {
        val MAX = UserTimePeriod(MAX_TIME)

        fun now(clock: Clock) = UserTimePeriod(LocalDateTime.now(clock))
    }
}

/**
 * ユーザー視点の期間
 */
class UserTimeSpan(start: UserTimePeriod, end: UserTimePeriod): TimeSpan(start.value, end.value) {
    companion object {
        /**
         * 現在からずっと未来まで有効なユーザー時間を返します
         */
        fun nowToInfinity(clock: Clock) = UserTimeSpan(UserTimePeriod.now(clock), UserTimePeriod.MAX)
    }
}

/**
 * ユーザー時間
 */
class SystemTimePeriod(val value: LocalDateTime) {
    companion object {
        val MAX = SystemTimePeriod(MAX_TIME)

        fun now(clock: Clock) = SystemTimePeriod(LocalDateTime.now(clock))
    }
}


/**
 * システム視点の期間
 */
class SystemTimeSpan(start: SystemTimePeriod, end: SystemTimePeriod): TimeSpan(start.value, end.value) {
    companion object {
        /**
         * 現在からずっと未来まで有効なシステム時間を返します
         */
        fun nowToInfinity(clock: Clock) = SystemTimeSpan(SystemTimePeriod.now(clock), SystemTimePeriod.MAX)
    }
}