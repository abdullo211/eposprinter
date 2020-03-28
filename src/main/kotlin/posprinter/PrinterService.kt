package posprinter


import QRCodeGenerator
import com.wd.posprinter.Commands
import com.wd.posprinter.IPrinter
import posprinter.exceptions.BarcodeSizeError
import posprinter.exceptions.QRCodeException
import posprinter.image.Image
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

class PrinterService(private val printer: IPrinter) {

    fun print(text: String) = write(text.toByteArray())

    fun printLn(text: String) {
        print(text + CARRIAGE_RETURN)
    }

    @JvmOverloads
    fun lineBreak(nbLine: Int = 1) {
        for (i in 0 until nbLine) {
            write(Commands.CTL_LF)
        }
    }


    fun printQRCode(value: String?, size: Int = 150) {
        val q = QRCodeGenerator()
        printImage(q.generate(value!!, size))
    }

    fun setTextSizeNormal() {
        setTextSize(1, 1)
    }

    fun setTextSize2H() {
        setTextSize(1, 2)
    }

    fun setTextSize2W() {
        setTextSize(2, 1)
    }

    fun setText4Square() {
        setTextSize(2, 2)
    }

    private fun setTextSize(width: Int, height: Int) {
        if (height == 2 && width == 2) {
            write(Commands.TXT_NORMAL)
            write(Commands.TXT_4SQUARE)
        } else if (height == 2) {
            write(Commands.TXT_NORMAL)
            write(Commands.TXT_2HEIGHT)
        } else if (width == 2) {
            write(Commands.TXT_NORMAL)
            write(Commands.TXT_2WIDTH)
        } else {
            write(Commands.TXT_NORMAL)
        }
    }

    fun setTextTypeBold() {
        setTextType("B")
    }

    fun setTextTypeUnderline() {
        setTextType("U")
    }

    fun setTextType2Underline() {
        setTextType("U2")
    }

    fun setTextTypeBoldUnderline() {
        setTextType("BU")
    }

    fun setTextTypeBold2Underline() {
        setTextType("BU2")
    }

    fun setTextTypeNormal() {
        setTextType("NORMAL")
    }

    private fun setTextType(type: String) {
        when {
            type.equals("B", ignoreCase = true) -> {
                write(Commands.TXT_BOLD_ON)
                write(Commands.TXT_UNDERL_OFF)
            }
            type.equals("U", ignoreCase = true) -> {
                write(Commands.TXT_BOLD_OFF)
                write(Commands.TXT_UNDERL_ON)
            }
            type.equals("U2", ignoreCase = true) -> {
                write(Commands.TXT_BOLD_OFF)
                write(Commands.TXT_UNDERL2_ON)
            }
            type.equals("BU", ignoreCase = true) -> {
                write(Commands.TXT_BOLD_ON)
                write(Commands.TXT_UNDERL_ON)
            }
            type.equals("BU2", ignoreCase = true) -> {
                write(Commands.TXT_BOLD_ON)
                write(Commands.TXT_UNDERL2_ON)
            }
            type.equals("NORMAL", ignoreCase = true) -> {
                write(Commands.TXT_BOLD_OFF)
                write(Commands.TXT_UNDERL_OFF)
            }
        }
    }

    fun cutPart() {
        cut("PART")
    }

    fun cutFull() {
        cut("FULL")
    }

    private fun cut(mode: String) {
        for (i in 0..4) {
            write(Commands.CTL_LF)
        }
        if (mode.toUpperCase() == "PART") {
            write(Commands.PAPER_PART_CUT)
        } else {
            write(Commands.PAPER_FULL_CUT)
        }
    }

    @Throws(BarcodeSizeError::class)
    fun printBarcode(
        code: String,
        bc: String,
        width: Int,
        height: Int,
        pos: String,
        font: String
    ) {
        // Align Bar Code()
        write(Commands.TXT_ALIGN_CT)
        // Height
        if (height >= 2 || height <= 6) {
            write(Commands.BARCODE_HEIGHT)
        } else {
            throw BarcodeSizeError("Incorrect Height")
        }
        //Width
        if (width >= 1 || width <= 255) {
            write(Commands.BARCODE_WIDTH)
        } else {
            throw BarcodeSizeError("Incorrect Width")
        }
        //Font
        if (font.equals("B", ignoreCase = true)) {
            write(Commands.BARCODE_FONT_B)
        } else {
            write(Commands.BARCODE_FONT_A)
        }
        //Position
        if (pos.equals("OFF", ignoreCase = true)) {
            write(Commands.BARCODE_TXT_OFF)
        } else if (pos.equals("BOTH", ignoreCase = true)) {
            write(Commands.BARCODE_TXT_BTH)
        } else if (pos.equals("ABOVE", ignoreCase = true)) {
            write(Commands.BARCODE_TXT_ABV)
        } else {
            write(Commands.BARCODE_TXT_BLW)
        }
        when (bc.toUpperCase()) {
            "UPC-A" -> write(Commands.BARCODE_UPC_A)
            "UPC-E" -> write(Commands.BARCODE_UPC_E)
            "EAN13" -> write(Commands.BARCODE_EAN13)
            "EAN8" -> write(Commands.BARCODE_EAN8)
            "CODE39" -> write(Commands.BARCODE_CODE39)
            "ITF" -> write(Commands.BARCODE_ITF)
            "NW7" -> write(Commands.BARCODE_NW7)
            else -> write(Commands.BARCODE_EAN13)
        }
        //Print Code
        if (code != "") {
            write(code.toByteArray())
            write(Commands.CTL_LF)
        } else {
            throw BarcodeSizeError("Incorrect Value")
        }
    }

    fun setTextFontA() {
        setTextFont("A")
    }

    fun setTextFontB() {
        setTextFont("B")
    }

    private fun setTextFont(font: String) {
        if (font.equals("B", ignoreCase = true)) {
            write(Commands.TXT_FONT_B)
        } else {
            write(Commands.TXT_FONT_A)
        }
    }

    fun setTextAlignCenter() {
        setTextAlign("CENTER")
    }

    fun setTextAlignRight() {
        setTextAlign("RIGHT")
    }

    fun setTextAlignLeft() {
        setTextAlign("LEFT")
    }

    private fun setTextAlign(align: String) {
        when {
            align.equals("CENTER", ignoreCase = true) -> {
                write(Commands.TXT_ALIGN_CT)
            }
            align.equals("RIGHT", ignoreCase = true) -> {
                write(Commands.TXT_ALIGN_RT)
            }
            else -> {
                write(Commands.TXT_ALIGN_LT)
            }
        }
    }

    fun setTextDensity(density: Int) {
        when (density) {
            0 -> write(Commands.PD_N50)
            1 -> write(Commands.PD_N37)
            2 -> write(Commands.PD_N25)
            3 -> write(Commands.PD_N12)
            4 -> write(Commands.PD_0)
            5 -> write(Commands.PD_P12)
            6 -> write(Commands.PD_P25)
            7 -> write(Commands.PD_P37)
            8 -> write(Commands.PD_P50)
        }
    }

    fun setTextNormal() {
        setTextProperties("LEFT", "A", "NORMAL", 1, 1, 9)
    }

    fun setTextProperties(
        align: String,
        font: String,
        type: String,
        width: Int,
        height: Int,
        density: Int
    ) {
        setTextAlign(align)
        setTextFont(font)
        setTextType(type)
        setTextSize(width, height)
        setTextDensity(density)
    }

    @Throws(IOException::class)
    fun printImage(filePath: String) {
        val img = File(filePath)
        printImage(ImageIO.read(img))
    }

    fun printImage(image: BufferedImage?) {
        val img = Image()
        val pixels = img.getPixelsSlow(image!!)
        var y = 0
        while (y < pixels.size) {
            write(Commands.LINE_SPACE_24)
            write(Commands.SELECT_BIT_IMAGE_MODE)
            write(
                byteArrayOf(
                    (0x00ff and pixels[y].size).toByte(),
                    (0xff00 and pixels[y].size shr 8).toByte()
                )
            )
            for (x in pixels[y].indices) {
                write(img.recollectSlice(y, x, pixels))
            }
            write(Commands.CTL_LF)
            y += 24
        }
    }

    fun setCharCode(code: String?) {
        when (code) {
            "USA" -> write(Commands.CHARCODE_PC437)
            "JIS" -> write(Commands.CHARCODE_JIS)
            "MULTILINGUAL" -> write(Commands.CHARCODE_PC850)
            "PORTUGUESE" -> write(Commands.CHARCODE_PC860)
            "CA_FRENCH" -> write(Commands.CHARCODE_PC863)
            "NORDIC" -> write(Commands.CHARCODE_PC865)
            "WEST_EUROPE" -> write(Commands.CHARCODE_WEU)
            "GREEK" -> write(Commands.CHARCODE_GREEK)
            "HEBREW" -> write(Commands.CHARCODE_HEBREW)
            "WPC1252" -> write(Commands.CHARCODE_PC1252)
            "CIRILLIC2" -> write(Commands.CHARCODE_PC866)
            "LATIN2" -> write(Commands.CHARCODE_PC852)
            "EURO" -> write(Commands.CHARCODE_PC858)
            "THAI42" -> write(Commands.CHARCODE_THAI42)
            "THAI11" -> write(Commands.CHARCODE_THAI11)
            "THAI13" -> write(Commands.CHARCODE_THAI13)
            "THAI14" -> write(Commands.CHARCODE_THAI14)
            "THAI16" -> write(Commands.CHARCODE_THAI16)
            "THAI17" -> write(Commands.CHARCODE_THAI17)
            "THAI18" -> write(Commands.CHARCODE_THAI18)
            else -> write(Commands.CHARCODE_PC865)
        }
    }

    fun init() {
        write(Commands.HW_INIT)
    }

    fun openCashDrawerPin2() {
        write(Commands.CD_KICK_2)
    }

    fun openCashDrawerPin5() {
        write(Commands.CD_KICK_5)
    }

    fun open() {
        printer.open()
    }

    fun close() {
        printer.close()
    }

    fun beep() = write(Commands.BEEPER)

    fun write(command: ByteArray): Int = printer.write(command)

    companion object {
        private val CARRIAGE_RETURN = System.getProperty("line.separator")
    }

    init {
        open()
    }
}
