package posprinter.image

import java.awt.image.BufferedImage
import kotlin.experimental.or


class Image {
    fun getPixelsSlow(image: BufferedImage): Array<IntArray> {
        val width = image.width
        val height = image.height
        val result = Array(height) { IntArray(width) }
        for (row in 0 until height) {
            for (col in 0 until width) {
                result[row][col] = image.getRGB(col, row)
            }
        }
        return result
    }

    fun recollectSlice(y: Int, x: Int, img: Array<IntArray>): ByteArray {
        val slices = byteArrayOf(0, 0, 0)
        var yy = y
        var i = 0
        while (yy < y + 24 && i < 3) {
            var slice: Byte = 0
            for (b in 0..7) {
                val yyy = yy + b
                if (yyy >= img.size) {
                    continue
                }
                val col = img[yyy][x]
                val v = shouldPrintColor(col)
                slice = slice or ((if (v) 1 else 0) shl 7 - b).toByte()
            }
            slices[i] = slice
            yy += 8
            i++
        }
        return slices
    }

    private fun shouldPrintColor(col: Int): Boolean {
        val threshold = 127
        val a: Int
        val r: Int
        val g: Int
        val b: Int
        val luminance: Int
        a = col shr 24 and 0xff
        if (a != 0xff) { // Ignore transparencies
            return false
        }
        r = col shr 16 and 0xff
        g = col shr 8 and 0xff
        b = col and 0xff
        luminance = (0.299 * r + 0.587 * g + 0.114 * b).toInt()
        return luminance < threshold
    }
}
