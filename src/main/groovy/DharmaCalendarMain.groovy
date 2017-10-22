import org.jj.dharma.calendar.DharmaEvent
import org.jj.dharma.spiritrock.SpiritRockDharmaEventParser

List<DharmaEvent> spiritRockDharmaEvents = new SpiritRockDharmaEventParser().parse()

spiritRockDharmaEvents.each {
    println it
}
