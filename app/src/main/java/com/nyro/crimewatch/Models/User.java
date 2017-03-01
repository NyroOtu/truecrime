package com.nyro.crimewatch.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by smsgh on 10/01/2017.
 */

@Table(name = "User")
public class User extends Model {

    @Column(name = "name")
    public String name ;

    @Column(name = "phone")
    public String phone ;


    @Column(name = "keenOne")
    public String keenOne ;

    @Column(name = "keenOnePhone")
    public String keenOnePhone ;


    @Column(name = "keenTwo")
    public String keenTwo ;

    @Column(name = "keenTwoPhone")
    public String keenTwoPhone ;






    @Column(name = "keenThree")
    public String keenThree ;

    @Column(name = "keenThreePhone")
    public String keenThreePhone ;


    @Column(name = "keenFour")
    public String keenFour ;

    @Column(name = "keenFourPhone")
    public String keenFourPhone ;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getKeenOne() {
        return keenOne;
    }

    public void setKeenOne(String keenOne) {
        this.keenOne = keenOne;
    }

    public String getKeenOnePhone() {
        return keenOnePhone;
    }

    public void setKeenOnePhone(String keenOnePhone) {
        this.keenOnePhone = keenOnePhone;
    }

    public String getKeenTwo() {
        return keenTwo;
    }

    public void setKeenTwo(String keenTwo) {
        this.keenTwo = keenTwo;
    }

    public String getKeenTwoPhone() {
        return keenTwoPhone;
    }

    public void setKeenTwoPhone(String keenTwoPhone) {
        this.keenTwoPhone = keenTwoPhone;
    }

    public String getKeenThree() {
        return keenThree;
    }

    public void setKeenThree(String keenThree) {
        this.keenThree = keenThree;
    }

    public String getKeenThreePhone() {
        return keenThreePhone;
    }

    public void setKeenThreePhone(String keenThreePhone) {
        this.keenThreePhone = keenThreePhone;
    }

    public String getKeenFour() {
        return keenFour;
    }

    public void setKeenFour(String keenFour) {
        this.keenFour = keenFour;
    }

    public String getKeenFourPhone() {
        return keenFourPhone;
    }

    public void setKeenFourPhone(String keenFourPhone) {
        this.keenFourPhone = keenFourPhone;
    }
}