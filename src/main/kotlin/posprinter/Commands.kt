package com.wd.posprinter

object Commands {
    // Feed control sequences
    val CTL_LF = byteArrayOf(0x0a) // Print and line feed

    // Beeper
    val BEEPER = byteArrayOf(0x1b, 0x42, 0x05, 0x09) // Beeps 5 times for 9*50ms each time

    // Line Spacing
    val LINE_SPACE_24 = byteArrayOf(0x1b, 0x33, 24) // Set the line spacing at 24
    val LINE_SPACE_30 = byteArrayOf(0x1b, 0x33, 30) // Set the line spacing at 30

    //Image
    val SELECT_BIT_IMAGE_MODE = byteArrayOf(0x1B, 0x2A, 33)

    // Printer hardware
    val HW_INIT = byteArrayOf(0x1b, 0x40) // Clear data in buffer and reset modes

    // Cash Drawer
    val CD_KICK_2 = byteArrayOf(0x1b, 0x70, 0x00) // Sends a pulse to pin 2 []
    val CD_KICK_5 = byteArrayOf(0x1b, 0x70, 0x01) // Sends a pulse to pin 5 []

    // Paper
    val PAPER_FULL_CUT = byteArrayOf(0x1d, 0x56, 0x00) // Full cut paper
    val PAPER_PART_CUT = byteArrayOf(0x1d, 0x56, 0x01) // Partial cut paper

    // Text format
    val TXT_NORMAL = byteArrayOf(0x1b, 0x21, 0x00) // Normal text
    val TXT_2HEIGHT = byteArrayOf(0x1b, 0x21, 0x10) // Double height text
    val TXT_2WIDTH = byteArrayOf(0x1b, 0x21, 0x20) // Double width text
    val TXT_4SQUARE = byteArrayOf(0x1b, 0x21, 0x30) // Quad area text
    val TXT_UNDERL_OFF = byteArrayOf(0x1b, 0x2d, 0x00) // Underline font OFF
    val TXT_UNDERL_ON = byteArrayOf(0x1b, 0x2d, 0x01) // Underline font 1-dot ON
    val TXT_UNDERL2_ON = byteArrayOf(0x1b, 0x2d, 0x02) // Underline font 2-dot ON
    val TXT_BOLD_OFF = byteArrayOf(0x1b, 0x45, 0x00) // Bold font OFF
    val TXT_BOLD_ON = byteArrayOf(0x1b, 0x45, 0x01) // Bold font ON
    val TXT_FONT_A = byteArrayOf(0x1b, 0x4d, 0x48) // Font type A
    val TXT_FONT_B = byteArrayOf(0x1b, 0x4d, 0x01) // Font type B
    val TXT_ALIGN_LT = byteArrayOf(0x1b, 0x61, 0x00) // Left justification
    val TXT_ALIGN_CT = byteArrayOf(0x1b, 0x61, 0x01) // Centering
    val TXT_ALIGN_RT = byteArrayOf(0x1b, 0x61, 0x02) // Right justification

    // Char code table
    val CHARCODE_PC437 = byteArrayOf(0x1b, 0x74, 0x00) // USA){ Standard Europe
    val CHARCODE_JIS = byteArrayOf(0x1b, 0x74, 0x01) // Japanese Katakana
    val CHARCODE_PC850 = byteArrayOf(0x1b, 0x74, 0x02) // Multilingual
    val CHARCODE_PC860 = byteArrayOf(0x1b, 0x74, 0x03) // Portuguese
    val CHARCODE_PC863 = byteArrayOf(0x1b, 0x74, 0x04) // Canadian-French
    val CHARCODE_PC865 = byteArrayOf(0x1b, 0x74, 0x05) // Nordic
    val CHARCODE_WEU = byteArrayOf(0x1b, 0x74, 0x06) // Simplified Kanji, Hirakana
    val CHARCODE_GREEK = byteArrayOf(0x1b, 0x74, 0x07) // Simplified Kanji
    val CHARCODE_HEBREW = byteArrayOf(0x1b, 0x74, 0x08) // Simplified Kanji
    val CHARCODE_PC1252 = byteArrayOf(0x1b, 0x74, 0x10) // Western European Windows Code Set
    val CHARCODE_PC866 = byteArrayOf(0x1b, 0x74, 0x12) // Cirillic //2
    val CHARCODE_PC852 = byteArrayOf(0x1b, 0x74, 0x13) // Latin 2
    val CHARCODE_PC858 = byteArrayOf(0x1b, 0x74, 0x14) // Euro
    val CHARCODE_THAI42 = byteArrayOf(0x1b, 0x74, 0x15) // Thai character code 42
    val CHARCODE_THAI11 = byteArrayOf(0x1b, 0x74, 0x16) // Thai character code 11
    val CHARCODE_THAI13 = byteArrayOf(0x1b, 0x74, 0x17) // Thai character code 13
    val CHARCODE_THAI14 = byteArrayOf(0x1b, 0x74, 0x18) // Thai character code 14
    val CHARCODE_THAI16 = byteArrayOf(0x1b, 0x74, 0x19) // Thai character code 16
    val CHARCODE_THAI17 = byteArrayOf(0x1b, 0x74, 0x1a) // Thai character code 17
    val CHARCODE_THAI18 = byteArrayOf(0x1b, 0x74, 0x1b) // Thai character code 18

    // Barcode format
    val BARCODE_TXT_OFF = byteArrayOf(0x1d, 0x48, 0x00) // HRI printBarcode chars OFF
    val BARCODE_TXT_ABV = byteArrayOf(0x1d, 0x48, 0x01) // HRI printBarcode chars above
    val BARCODE_TXT_BLW = byteArrayOf(0x1d, 0x48, 0x02) // HRI printBarcode chars below
    val BARCODE_TXT_BTH = byteArrayOf(0x1d, 0x48, 0x03) // HRI printBarcode chars both above and below
    val BARCODE_FONT_A = byteArrayOf(0x1d, 0x66, 0x00) // Font type A for HRI printBarcode chars
    val BARCODE_FONT_B = byteArrayOf(0x1d, 0x66, 0x01) // Font type B for HRI printBarcode chars
    val BARCODE_HEIGHT = byteArrayOf(0x1d, 0x68, 0x64) // Barcode Height [1-255]
    val BARCODE_WIDTH = byteArrayOf(0x1d, 0x77, 0x03) // Barcode Width  [2-6]
    val BARCODE_UPC_A = byteArrayOf(0x1d, 0x6b, 0x00) // Barcode type UPC-A
    val BARCODE_UPC_E = byteArrayOf(0x1d, 0x6b, 0x01) // Barcode type UPC-E
    val BARCODE_EAN13 = byteArrayOf(0x1d, 0x6b, 0x02) // Barcode type EAN13
    val BARCODE_EAN8 = byteArrayOf(0x1d, 0x6b, 0x03) // Barcode type EAN8
    val BARCODE_CODE39 = byteArrayOf(0x1d, 0x6b, 0x04) // Barcode type CODE39
    val BARCODE_ITF = byteArrayOf(0x1d, 0x6b, 0x05) // Barcode type ITF
    val BARCODE_NW7 = byteArrayOf(0x1d, 0x6b, 0x06) // Barcode type NW7

    // Printing Density
    val PD_N50 = byteArrayOf(0x1d, 0x7c, 0x00) // Printing Density -50%
    val PD_N37 = byteArrayOf(0x1d, 0x7c, 0x01) // Printing Density -37.5%
    val PD_N25 = byteArrayOf(0x1d, 0x7c, 0x02) // Printing Density -25%
    val PD_N12 = byteArrayOf(0x1d, 0x7c, 0x03) // Printing Density -12.5%
    val PD_0 = byteArrayOf(0x1d, 0x7c, 0x04) // Printing Density  0%
    val PD_P50 = byteArrayOf(0x1d, 0x7c, 0x08) // Printing Density +50%
    val PD_P37 = byteArrayOf(0x1d, 0x7c, 0x07) // Printing Density +37.5%
    val PD_P25 = byteArrayOf(0x1d, 0x7c, 0x06) // Printing Density +25%
    val PD_P12 = byteArrayOf(0x1d, 0x7c, 0x05) // Printing Density +12.5%
}
