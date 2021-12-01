package com.envision.core.utils

import android.net.Uri

class TestUriBuilderWrapper {
    fun uriCreator(): Uri = Uri.Builder().path("testpath").build()
}