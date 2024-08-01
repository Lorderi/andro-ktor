package ru.lorderi.androktor

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.lorderi.androktor.util.handlePhone

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun handlePhoneCase1() {
        assertEquals("+7 953 123-12-31", "79531231231".handlePhone())
    }
    @Test
    fun handlePhoneCase2() {
        assertEquals("+7 953 123-12-31", "89531231231".handlePhone())
    }
    @Test
    fun handlePhoneCase3() {
        assertEquals("+7 953 123-12-31", "+79531231231".handlePhone())
    }
}