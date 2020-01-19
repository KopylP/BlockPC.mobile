package com.kopyl.blockpc.extensions

import com.google.zxing.integration.android.IntentIntegrator
import com.kopyl.blockpc.CaptureActivityPortrait

fun IntentIntegrator.ownInitScan() {
    this.setPrompt("Scan a qr code")
    this.setOrientationLocked(false)
    this.setBeepEnabled(false)
    this.captureActivity = CaptureActivityPortrait::class.java
    this.initiateScan()
}