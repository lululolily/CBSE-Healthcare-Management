package com.example.healthcare.interfaces;

import com.example.healthcare.model.User;

public interface IAuthenticateUser {
    User registerUser(String username, String password, String role, String firstName, String lastName,
                      String contactNumber, String specialization, String email,
                      String workingHoursStart, String workingHoursEnd);

    User authenticateUser(String username, String password);
}
