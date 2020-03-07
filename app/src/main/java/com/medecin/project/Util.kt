package com.medecin.project

import android.content.Context
import android.content.Intent
import android.net.Uri

class Util {
    fun openPage(ctx: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        ctx.startActivity(intent)
    }
}