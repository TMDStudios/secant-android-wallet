package co.electriccoin.zcash.preference.test.fixture

import co.electriccoin.zcash.preference.model.entry.BooleanPreferenceDefault
import co.electriccoin.zcash.preference.model.entry.Key

object BooleanPreferenceDefaultFixture {
    val KEY = Key("some_boolean_key") // $NON-NLS
    fun newTrue() = BooleanPreferenceDefault(KEY, true)
    fun newFalse() = BooleanPreferenceDefault(KEY, false)
}
