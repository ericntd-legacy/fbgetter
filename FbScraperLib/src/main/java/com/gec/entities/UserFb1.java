package com.gec.entities;

/**
 * Created by eric on 27/1/15.
 */
public class UserFb1 extends UserBase {
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public byte getAgeRangeMin() {
        return ageRangeMin;
    }

    public void setAgeRangeMin(byte ageRangeMin) {
        this.ageRangeMin = ageRangeMin;
    }

    public byte getAgeRangeMax() {
        return ageRangeMax;
    }

    public void setAgeRangeMax(byte ageRangeMax) {
        this.ageRangeMax = ageRangeMax;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    private String firstName;
    private String lastName;
    private String link;
    private String gender;
    private byte ageRangeMin;
    private byte ageRangeMax;
    private String id;
    private String email;
    private String locale;
}
