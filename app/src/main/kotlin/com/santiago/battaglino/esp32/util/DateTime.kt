package com.santiago.battaglino.esp32.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.stream.Collectors

private const val tag = "DateTime"

fun groupByDay() {
    val loginTimes: List<LocalDateTime> = listOf(
        LocalDateTime.of(2018, 5, 7, 8, 10),
        LocalDateTime.of(2018, 5, 7, 9, 15, 20), LocalDateTime.of(2018, 6, 22, 7, 40, 30)
    )
    val loginCountByDate: Map<LocalDate, Long> = loginTimes.stream()
        .collect(Collectors.groupingBy(LocalDateTime::toLocalDate, Collectors.counting()))
}