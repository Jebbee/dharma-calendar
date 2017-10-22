package org.jj.dharma.calendar

class DharmaEvent {
    Date date
    String title
    String instructor
    String location
    URL link


    @Override
    String toString() {
        return "DharmaEvent{" +
                "date=" + date +
                ", title='" + title + '\'' +
                ", instructor='" + instructor + '\'' +
                ", location='" + location + '\'' +
                ", link=" + link +
                '}'
    }
}
