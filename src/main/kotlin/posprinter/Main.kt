package posprinter

import posprinter.printers.PosBankPrinter
import java.lang.Exception

fun main(args: Array<String>) {
    print()
}

fun print(){
    try {
        val service = PrinterService(PosBankPrinter())
        service.beep()
        service.printLn("M.Ch.J `Bar Code Technologies` ")
        service.printLn("Toshkent sh. Afrosiyob, 16 ")
        service.printLn("+998911623501")
        service.setTextAlignLeft()
        service.print("SAVDO CHEKI: UZ170703100597")
        service.setTextAlignRight()
        service.print("KASSIR: Admin")
        service.printLn("")
        service.printLn("\n")
        service.printLn("----------------------------")
        service.printQRCode("https://ofd.soliq.uz/check?t=UZ170703100597&r=73&c=20200328194312&s=525079351301", 180)
        service.printLn("\n\n\n")
        service.cutFull()
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}
