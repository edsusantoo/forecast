package com.edsusantoo.bismillah.forecast.utils

import kotlinx.coroutines.*

fun<T> lazyDeffered(block:suspend CoroutineScope.()->T):Lazy<Deferred<T>>{
    return lazy{
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}