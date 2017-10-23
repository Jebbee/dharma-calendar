package org.jj.dharma.spiritrock

import groovy.util.slurpersupport.GPathResult
import groovy.util.slurpersupport.NodeChild
import org.jj.dharma.calendar.DharmaEvent
import org.jj.dharma.calendar.DharmaEventParser

import static org.jj.dharma.util.XmlSlurperHelper.find
import static org.jj.dharma.util.XmlSlurperHelper.findAll

class SpiritRockDharmaEventParser implements DharmaEventParser {

    private static final String LOCATION_NAME = 'Spirit Rock'
    private static final String HOME_URL_STRING = "https://spiritrock.org"
    private static final String ALL_EVENTS_CALENDAR_URL_STRING = HOME_URL_STRING + "/all-events-calendar"
    private static final String ALL_EVENTS_CALENDAR_PAGE_N_URL_STRING_PREFIX = ALL_EVENTS_CALENDAR_URL_STRING + '?CalendarPage='

    List<DharmaEvent> parse() {
        List<DharmaEvent> dharmaEvents = []

        int numberOfCalendarPages = getNumberOfCalendarPages()

        for (int pageNumber = 0; pageNumber < numberOfCalendarPages; pageNumber++) {
            addDharmaEventsFromPage(dharmaEvents, pageNumber)
        }

        return dharmaEvents
    }

    private static void addDharmaEventsFromPage(List<DharmaEvent> dharmaEvents, int pageNumber) {
        getEventNodesFromPage(pageNumber).eachWithIndex { NodeChild eventNode, int index ->
            String eventNodeText = eventNode.text()

            if (isNewEvent(index)) {
                dharmaEvents << createNewDharmaEvent(eventNode, eventNodeText)
            }
        }
    }

    private static DharmaEvent createNewDharmaEvent(NodeChild eventNode, String eventNodeText) {
        URI eventDetailsUri = new URI(HOME_URL_STRING + eventNode.@href.toString())

        DharmaEvent dharmaEvent = new DharmaEvent(
                date: Date.parse('M/d/yyyy', eventNodeText),
                location: LOCATION_NAME,
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
            dharmaEvent.details = tableChildren[5].toString().trim()
        }
    }

    private static Collection<NodeChild> getEventDetailNodes(URI eventDetailsUri) {
        return findAll(eventDetailsUri.toString()) {
            it.name() == 'table' && it.@class.toString() == 'formborders'
        }
    }

    private static Collection<NodeChild> getEventNodesFromPage(int pageNumber) {
        return findAll(ALL_EVENTS_CALENDAR_PAGE_N_URL_STRING_PREFIX + pageNumber) {
            it.name() == 'a' && it.@href.toString().startsWith('/calendarDetails?EventID=')
        }
    }

    private static int getNumberOfCalendarPages() {
        NodeChild nodeChild = find(ALL_EVENTS_CALENDAR_URL_STRING) {
            it.name() == 'select' && it.@id.toString() == 'PC12156_ctl00_DropDownList1'
        }

        return nodeChild.children().size()
    }

    private static boolean isNewEvent(int index) {
        return index % 2 == 0
    }

}
