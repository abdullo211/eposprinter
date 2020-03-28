import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import posprinter.exceptions.QRCodeException
import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.util.*

class QRCodeGenerator {
    @JvmOverloads
    @Throws(QRCodeException::class)
    fun generate(textValue: String, size: Int = 150): BufferedImage {
        return try {
            val hintMap = setEncodingbehavior()
            val bm = getByteMatrix(textValue, size, hintMap)
            getImage(bm)
        } catch (e: WriterException) {
            throw QRCodeException("QRCode generation error", e)
        }
    }

    private fun setEncodingbehavior(): Map<EncodeHintType, Any?> {
        val hintMap: MutableMap<EncodeHintType, Any?> = EnumMap(
            EncodeHintType::class.java
        )
        hintMap[EncodeHintType.CHARACTER_SET] = "UTF-8"
        hintMap[EncodeHintType.MARGIN] = 1
        hintMap[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.L
        return hintMap
    }

    @Throws(WriterException::class)
    private fun getByteMatrix(
        textValue: String,
        size: Int,
        hintMap: Map<EncodeHintType, Any?>
    ): BitMatrix {
        val qrCodeWriter = QRCodeWriter()
        return qrCodeWriter.encode(textValue, BarcodeFormat.QR_CODE, size, size, hintMap)
    }

    private fun getImage(bm: BitMatrix): BufferedImage {
        val image = BufferedImage(bm.width, bm.width, BufferedImage.TYPE_INT_RGB)
        image.createGraphics()
        val graphics = image.graphics as Graphics2D
        graphics.color = Color.WHITE
        graphics.fillRect(0, 0, bm.width, bm.width)
        graphics.color = Color.BLACK
        for (i in 0 until bm.width) {
            for (j in 0 until bm.width) {
                if (bm[i, j]) {
                    graphics.fillRect(i, j, 1, 1)
                }
            }
        }
        return image
    }
}

