package posprinter.printers

import com.wd.posprinter.IPrinter
import org.usb4java.javax.Services
import java.awt.print.PrinterIOException
import java.io.IOException
import javax.usb.UsbDevice
import javax.usb.UsbEndpoint
import javax.usb.UsbPipe

class PosBankPrinter : IPrinter {
    private var pipe: UsbPipe? = null

    companion object {
        private const val DEVICE_NAME = "POSBANK"
    }

    override fun open() {
        val device = findDevice() ?: throw PrinterIOException(IOException("Printer not found"))
        val configuration = device.activeUsbConfiguration
        val iFace = configuration.getUsbInterface(0)
        iFace.claim()
        val endpoint: UsbEndpoint = iFace.usbEndpoints[0] as UsbEndpoint
        pipe = endpoint.usbPipe
        pipe?.open()
    }

    override fun close() {
        pipe?.close()
    }

    override fun write(data: ByteArray): Int = pipe?.syncSubmit(data) ?: -1

    private fun findDevice() =
        Services().rootUsbHub
            ?.attachedUsbDevices
            ?.filterIsInstance<UsbDevice>()
            ?.firstOrNull { it.manufacturerString == DEVICE_NAME }

}