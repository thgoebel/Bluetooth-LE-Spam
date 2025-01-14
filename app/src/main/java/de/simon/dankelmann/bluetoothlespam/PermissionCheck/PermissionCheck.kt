package de.simon.dankelmann.bluetoothlespam.PermissionCheck

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

class PermissionCheck() {
    companion object {

        private const val _logTag = "PermissionCheck"

        fun checkPermission(
            permission: String,
            context: Context,
        ): Boolean {
            // Permissions introduced in SDK 31
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S
                && (permission == Manifest.permission.BLUETOOTH_ADVERTISE
                        || permission == Manifest.permission.BLUETOOTH_CONNECT
                        || permission == Manifest.permission.BLUETOOTH_SCAN)
            ) {
                return true
            }

            // Permissions last available in SDK 30
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R
                && (permission == Manifest.permission.BLUETOOTH || permission == Manifest.permission.BLUETOOTH_ADMIN)
            ) {
                return true
            }

            // Permissions introduced in SDK 29
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q
                && (permission == Manifest.permission.FOREGROUND_SERVICE_LOCATION
                        || permission == Manifest.permission.ACCESS_BACKGROUND_LOCATION
                        || permission == Manifest.permission.FOREGROUND_SERVICE_SPECIAL_USE)
            ) {
                return true
            }

            val isGranted = ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED

            return isGranted
        }
    }
}