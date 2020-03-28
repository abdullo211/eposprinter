package com.wd.posprinter

interface IPrinter {
    fun open()
    fun close()
    fun write(data: ByteArray): Int
}