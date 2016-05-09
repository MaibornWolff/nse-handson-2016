package de.maibornwolff.microservices.account.model;

/**
 * @author Daniel Seidewitz <daniel@seidewitz.com>
 */
public class Account {

    private String badgeNumber;
    private String firstName;
    private String lastName;
    private String company;
    private String email;


    public Account() {
    }


    public Account(String badgeNumber, String firstName, String lastName, String company, String email) {
        this.badgeNumber = badgeNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.email = email;
    }


    public String getBadgeNumber() {
        return badgeNumber;
    }


    public void setBadgeNumber(String badgeNumber) {
        this.badgeNumber = badgeNumber;
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


    public String getCompany() {
        return company;
    }


    public void setCompany(String company) {
        this.company = company;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "Account{" +
                "badgeNumber='" + badgeNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", company='" + company + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
