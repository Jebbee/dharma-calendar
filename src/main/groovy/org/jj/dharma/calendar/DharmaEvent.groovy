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

    @Override
    String toString() {
        return "DharmaEvent{" +
                "date=" + date +
                ", dateDescription='" + dateDescription + '\'' +
                ", title='" + title + '\'' +
                ", teachers='" + teachers + '\'' +
                ", location='" + location + '\'' +
                ", locationDescription='" + locationDescription + '\'' +
                ", cost='" + cost + '\'' +
                ", details='" + details + '\'' +
                ", link=" + link +
                '}'
    }
}
