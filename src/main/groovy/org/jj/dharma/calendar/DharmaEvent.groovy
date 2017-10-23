package org.jj.dharma.calendar

class DharmaEvent {
    Date date
    String dateDescription
    String title
    String teachers
    String location
    String locationDescription
    String cost
    String details
    URI link

    // TODO Remove temp toString()
    @Override
    String toString() {
        return "DharmaEvent{" +
                "date=" + date +
                "\n\tdateDescription='" + dateDescription + '\'' +
                "\n\ttitle='" + title + '\'' +
                "\n\tteachers='" + teachers + '\'' +
                "\n\tlocation='" + location + '\'' +
                "\n\tlocationDescription='" + locationDescription + '\'' +
                "\n\tcost='" + cost + '\'' +
                "\n\tdetails='" + details + '\'' +
                "\n\tlink=" + link +
                '}'
    }
}
