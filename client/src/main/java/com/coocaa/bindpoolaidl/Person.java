package com.coocaa.bindpoolaidl;

import android.os.Parcel;
import android.os.Parcelable;


public class Person implements Parcelable {
    int id;
    String selfId;
    String sex;
    String date;
    String name;
    public Person(){

    }

    public Person(String self, String sex, String date, String name) {
        this.selfId = self;
        this.sex = sex;
        this.date = date;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSelfId() {
        return selfId;
    }

    public void setSelfId(String selfId) {
        this.selfId = selfId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.selfId);
        dest.writeString(this.sex);
        dest.writeString(this.date);
        dest.writeString(this.name);
    }

    protected Person(Parcel in) {
        this.id = in.readInt();
        this.selfId = in.readString();
        this.sex = in.readString();
        this.date = in.readString();
        this.name = in.readString();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    public void readFromParcel(Parcel in) {
        this.id = in.readInt();
        this.selfId = in.readString();
        this.sex = in.readString();
        this.date = in.readString();
        this.name = in.readString();
    }
}
