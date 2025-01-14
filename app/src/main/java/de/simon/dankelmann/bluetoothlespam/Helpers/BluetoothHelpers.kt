package de.simon.dankelmann.bluetoothlespam.Helpers

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import androidx.preference.PreferenceManager
import de.simon.dankelmann.bluetoothlespam.AppContext.AppContext
import de.simon.dankelmann.bluetoothlespam.Interfaces.Services.IAdvertisementService
import de.simon.dankelmann.bluetoothlespam.Interfaces.Services.IBluetoothLeScanService
import de.simon.dankelmann.bluetoothlespam.R
import de.simon.dankelmann.bluetoothlespam.Services.BluetoothLeScanService
import de.simon.dankelmann.bluetoothlespam.Services.LegacyAdvertisementService
import de.simon.dankelmann.bluetoothlespam.Services.ModernAdvertisementService

class BluetoothHelpers {
    companion object {

        fun isBluetooth5Supported(context: Context): Boolean {
            val bluetoothAdapter = context.bluetoothAdapter()
            if (bluetoothAdapter != null) {
                if (bluetoothAdapter.isLe2MPhySupported
                    && bluetoothAdapter.isLeCodedPhySupported
                    && bluetoothAdapter.isLeExtendedAdvertisingSupported
                    && bluetoothAdapter.isLePeriodicAdvertisingSupported
                ) {
                    return true
                }
            }
            return false
        }

        fun getAdvertisementService(): IAdvertisementService {
            var useLegacyAdvertisementService = true // <-- DEFAULT

            // Get from Settings, if present
            val preferences =
                PreferenceManager.getDefaultSharedPreferences(AppContext.getContext()).all
            preferences.forEach {
                if (it.key == AppContext.getActivity().resources.getString(R.string.preference_key_use_legacy_advertising)) {
                    useLegacyAdvertisementService = it.value as Boolean
                }
            }

            val advertisementService = when (useLegacyAdvertisementService) {
                true -> LegacyAdvertisementService()
                else -> {
                    ModernAdvertisementService()
                }
            }

            return advertisementService
        }

        fun getBluetoothLeScanService(): IBluetoothLeScanService {
            return BluetoothLeScanService()
        }
    }
}

fun Context.bluetoothManager(): BluetoothManager? =
    (this.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager)

fun Context.bluetoothAdapter(): BluetoothAdapter? = this.bluetoothManager()?.adapter
