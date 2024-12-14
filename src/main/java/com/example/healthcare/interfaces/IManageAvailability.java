package com.example.healthcare.interfaces;

import java.time.LocalDateTime;

public interface IManageAvailability {
    void addUnavailability(Long doctorId, LocalDateTime from, LocalDateTime to);
}
