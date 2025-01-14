package de.simon.dankelmann.bluetoothlespam.ui.start

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.simon.dankelmann.bluetoothlespam.AppContext.AppContext
import de.simon.dankelmann.bluetoothlespam.Helpers.BluetoothHelpers

class StartViewModel : ViewModel() {

    val isSeeding = MutableLiveData<Boolean>(false)

    val appVersion = MutableLiveData<String>("0.0.0")
    val androidVersion = MutableLiveData<String>(android.os.Build.VERSION.RELEASE)
    val sdkVersion = MutableLiveData<String>(android.os.Build.VERSION.SDK_INT.toString())

    val isBluetooth5Supported = MutableLiveData<Boolean>(false)

    val allPermissionsGranted = MutableLiveData<Boolean>(false)

    val bluetoothAdapterIsReady = MutableLiveData<Boolean>(false)

    val advertisementServiceIsReady = MutableLiveData<Boolean>(false)

    val databaseIsReady = MutableLiveData<Boolean>(false)

    val missingRequirements = MutableLiveData<MutableList<String>>(mutableListOf())

    fun initWithContext(context: Context) {
        val packageInfo = context.packageManager.getPackageInfo(AppContext.getContext().packageName, 0)
        appVersion.value = packageInfo.versionName

        isBluetooth5Supported.value = BluetoothHelpers.isBluetooth5Supported(context)
    }
}
