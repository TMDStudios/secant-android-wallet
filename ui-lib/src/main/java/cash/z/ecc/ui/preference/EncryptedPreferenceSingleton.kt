package cash.z.ecc.ui.preference

import android.content.Context
import cash.z.ecc.ui.util.Lazy
import co.electriccoin.zcash.preference.AndroidPreferenceProvider
import co.electriccoin.zcash.preference.api.PreferenceProvider

object EncryptedPreferenceSingleton {

    private const val PREF_FILENAME = "co.electriccoin.zcash.encrypted"

    private val lazy = Lazy<Context, PreferenceProvider> { AndroidPreferenceProvider.newEncrypted(it, PREF_FILENAME) }

    suspend fun getInstance(context: Context) = lazy.getInstance(context)
}
