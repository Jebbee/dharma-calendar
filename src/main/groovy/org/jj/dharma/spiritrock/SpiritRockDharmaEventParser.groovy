package org.jj.dharma.spiritrock

import groovy.util.slurpersupport.GPathResult
import groovy.util.slurpersupport.NodeChild
import org.jj.dharma.calendar.DharmaEvent
import org.jj.dharma.calendar.DharmaEventParser

import static org.jj.dharma.util.XmlSlurperHelper.findAll

class SpiritRockDharmaEventParser implements DharmaEventParser {

    public static final String SPIRIT_ROCK_LOCATION_NAME = 'Spirit Rock'
    public static final String SPIRIT_ROCK_HOME_URL_STRING = "https://spiritrock.org"
    public static final String SPIRIT_ROCK_ALL_EVENTS_CALENDAR_URL_STRING = SPIRIT_ROCK_HOME_URL_STRING + "/all-events-calendar"

    List<DharmaEvent> parse() {
        List<DharmaEvent> dharmaEvents = []

        getEventNodes().eachWithIndex { NodeChild eventNode, int index ->
            String eventNodeText = eventNode.text()

            if (isNewEvent(index)) {
                dharmaEvents << createNewDharmaEvent(eventNode, eventNodeText)
            }
        }

        return dharmaEvents
    }

    private static DharmaEvent createNewDharmaEvent(NodeChild eventNode, String eventNodeText) {
        URI eventDetailsUri = new URI(SPIRIT_ROCK_HOME_URL_STRING + eventNode.@href.toString())

        DharmaEvent dharmaEvent = new DharmaEvent(
                date: Date.parse('M/d/yyyy', eventNodeText),
                location: SPIRIT_ROCK_LOCATION_NAME,
                link: eventDetailsUri)

        populateEventDetails(dharmaEvent, eventDetailsUri)

        return dharmaEvent
    }

    private static void populateEventDetails(DharmaEvent dharmaEvent, URI eventDetailsUri) {
        getEventDetailNodes(eventDetailsUri).each { NodeChild tableNode ->
            GPathResult tableChildren = tableNode.children()

            dharmaEvent.title = tableChildren[0]
            dharmaEvent.teachers = tableChildren[1]
            dharmaEvent.dateDescription = tableChildren[2]
            dharmaEvent.locationDescription = tableChildren[3]
            dharmaEvent.cost = tableChildren[4]
            dharmaEvent.details = tableChildren[5]
        }
    }

    private static Collection<NodeChild> getEventDetailNodes(URI eventDetailsUri) {
        return findAll(eventDetailsUri.toString()) {
            it.name() == 'table' && it.@class.toString() == 'formborders'
        }
    }

    private static Collection<NodeChild> getEventNodes() {
        return findAll(SPIRIT_ROCK_ALL_EVENTS_CALENDAR_URL_STRING) {
            it.name() == 'a' && it.@href.toString().startsWith('/calendarDetails?EventID=')
        }
    }

    private static boolean isNewEvent(int index) {
        return index % 2 == 0
    }

}
