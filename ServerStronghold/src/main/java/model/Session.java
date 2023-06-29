package model;

public class Session {
    private final int hour;
    private final int minute;
    private final int day;
    private final int month;
    private final int year;

    public Session(int hour, int minute, int day, int month, int year) {
        this.hour = hour;
        this.minute = minute;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
