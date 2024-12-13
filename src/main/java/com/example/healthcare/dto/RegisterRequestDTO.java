package com.example.healthcare.dto;

public class RegisterRequestDTO {
	 private String username;
	    private String password;
	    private String role; // "doctor" or "patient"
	    private String firstName;
	    private String lastName;
	    private String contactNumber;
	    private String specialization; // For doctors
	    private String email; // For patients
	    private String workingHoursStart; // For doctors
	    private String workingHoursEnd; // For doctors

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public String getWorkingHoursStart() {
		return workingHoursStart;
	}

	public void setWorkingHoursStart(String workingHoursStart) {
		this.workingHoursStart = workingHoursStart;
	}

	public String getWorkingHoursEnd() {
		return workingHoursEnd;
	}

	public void setWorkingHoursEnd(String workingHoursEnd) {
		this.workingHoursEnd = workingHoursEnd;
	}
}
