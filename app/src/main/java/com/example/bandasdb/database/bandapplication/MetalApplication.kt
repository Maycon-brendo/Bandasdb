package com.example.bandasdb.database.bandapplication

import android.app.Application
import com.example.bandasdb.database.MetalRepositoty

class MetalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MetalRepositoty.initialize(this)
    }
}