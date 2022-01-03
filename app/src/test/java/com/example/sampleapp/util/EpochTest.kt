package com.example.sampleapp.util

import com.example.sampleapp.util.Epoch.differenceBetweenTimestampsInDays
import org.junit.Assert.assertEquals
import org.junit.Test

class EpochTest {

    @Test(expected = IllegalArgumentException::class)
    fun differenceBetweenTimestampsInDays_minGreaterThanMax_throwsError() {
        val maxTs = 1641231195L
        val minTs = 1641231232L

        differenceBetweenTimestampsInDays(
            maxTime = maxTs,
            minTime = minTs
        )
    }

    @Test
    fun differenceBetweenTimestampsInDays_maxAndMin_returnsZero() {
        val maxTs = 1641231195L //Monday, 3. January 2022 17:33:15
        val minTs = 1641166395L //Sunday, 2. January 2022 23:33:15

        val expected = 0
        val actual = differenceBetweenTimestampsInDays(
            maxTime = maxTs,
            minTime = minTs
        )

        assertEquals(expected, actual)
    }

    @Test
    fun differenceBetweenTimestampsInDays_maxAndMin_returnsOne() {
        val maxTs = 1641231195L //Monday, 3. January 2022 17:33:15
        val minTs = 1641144795L //Sunday, 2. January 2022 17:33:15

        val expected = 1
        val actual = differenceBetweenTimestampsInDays(
            maxTime = maxTs,
            minTime = minTs
        )

        assertEquals(expected, actual)
    }

    @Test
    fun differenceBetweenTimestampsInDays_maxAndMin_returnsForty() {
        val maxTs = 1644687195L //Saturday, 12. February 2022 17:33:15
        val minTs = 1641231195L //Monday, 3. January 2022 17:33:15

        val expected = 40
        val actual = differenceBetweenTimestampsInDays(
            maxTime = maxTs,
            minTime = minTs
        )

        assertEquals(expected, actual)
    }
}