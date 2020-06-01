package com.example.captiveportal.service

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledThreadPoolExecutor

object AppExecutor {

    private val networkIO: ScheduledExecutorService? = Executors.newScheduledThreadPool(3)

    fun networkIO():ScheduledExecutorService{
        return networkIO!!
    }
}