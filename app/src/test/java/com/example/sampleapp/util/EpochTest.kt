package com.example.sampleapp.util

import com.example.sampleapp.util.Epoch.calcMoney
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
        val maxTs = 1641231195000L //Monday, 3. January 2022 17:33:15
        val minTs = 1641144795000L //Sunday, 2. January 2022 17:33:15

        val expected = 1
        val actual = differenceBetweenTimestampsInDays(
            maxTime = maxTs,
            minTime = minTs
        )

        assertEquals(expected, actual)
    }

    @Test
    fun differenceBetweenTimestampsInDays_maxAndMin_returnsForty() {
        val maxTs = 1644687195000L //Saturday, 12. February 2022 17:33:15
        val minTs = 1641231195000L //Monday, 3. January 2022 17:33:15

        val expected = 40
        val actual = differenceBetweenTimestampsInDays(
            maxTime = maxTs,
            minTime = minTs
        )

        assertEquals(expected, actual)
    }

    @Test
    fun differenceBetweenTimestampsInDays_maxAndMin_returnsEight() {
        val maxTs = 1641726145517L
        val minTs = 1640980800000L

        val expected = 8
        val actual = differenceBetweenTimestampsInDays(
            maxTime = maxTs,
            minTime = minTs
        )

        assertEquals(expected, actual)
    }

    /**
     * 1 -> one day
     * 10 -> cigarettes per day
     * 20 -> cigarettes in pack
     * 5 -> price
     */
    @Test
    fun calcMoney_1_10_20_5_returnsTwoPointFive() {
        val days = 1
        val perDay = 10
        val inPack = 20
        val price = 5f

        val expected = 2.5f
        val actual = calcMoney(days, perDay, inPack, price)

        assertEquals(expected, actual)
    }
}