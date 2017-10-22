package org.jj.dharma.spiritrock

import groovy.util.slurpersupport.NodeChild
import org.ccil.cowan.tagsoup.Parser
import org.jj.dharma.calendar.DharmaEvent
import org.jj.dharma.calendar.DharmaEventParser

import java.text.SimpleDateFormat

class SpiritRockDharmaEventParser implements DharmaEventParser {

    public static final String SPIRIT_ROCK_HOME_URL_STRING = "https://spiritrock.org"
    public static final String SPIRIT_ROCK_ALL_EVENTS_CALENDAR_URL_STRING = SPIRIT_ROCK_HOME_URL_STRING + "/all-events-calendar"

    List<DharmaEvent> parse() {
        List<DharmaEvent> dharmaEvents = []

        Collection<NodeChild> eventNodes = getEventNodes()

        eventNodes.eachWithIndex { NodeChild eventNode, int index ->
            String eventNodeText = eventNode.text()

            if (index % 2 == 0) {
                dharmaEvents << new DharmaEvent(
                        date: new SimpleDateFormat('M/d/yyyy').parse(eventNodeText),
                        location: 'Spirit Rock',
                        link: new URI(SPIRIT_ROCK_HOME_URL_STRING + eventNode.@href.toString()))
            } else {
                dharmaEvents.last().title = eventNodeText
            }
        }

        dharmaEvents
    }

    private static Collection<NodeChild> getEventNodes() {
        def htmlParser = new XmlSlurper(new Parser()).parse(SPIRIT_ROCK_ALL_EVENTS_CALENDAR_URL_STRING)

        htmlParser.'**'.findAll {
            it.name() == 'a' && it.@href.toString().startsWith('/calendarDetails?EventID=')
        }
    }

}
