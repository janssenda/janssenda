package com.dm.main;


import java.time.LocalDate;

public class Reservation {
    int ResID;
    int RoomNumber;
    LocalDate StartDate;
    LocalDate EndDate;

    public int getResID() {
        return ResID;
    }

    public void setResID(int resID) {
        ResID = resID;
    }

    public int getRoomNumber() {
        return RoomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        RoomNumber = roomNumber;
    }

    public LocalDate getStartDate() {
        return StartDate;
    }

    public void setStartDate(LocalDate startDate) {
        StartDate = startDate;
    }

    public LocalDate getEndDate() {
        return EndDate;
    }

    public void setEndDate(LocalDate endDate) {
        EndDate = endDate;
    }

}
