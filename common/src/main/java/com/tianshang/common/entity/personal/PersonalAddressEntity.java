package com.tianshang.common.entity.personal;

import android.os.Parcel;
import android.os.Parcelable;

public class PersonalAddressEntity implements Parcelable {

    private String name;

    private String phone;

    private String addressCity;

    private String addressDes;

    private boolean isDefault;

    public PersonalAddressEntity(String name, String phone, String addressCity, String addressDes, boolean isDefault) {
        this.name = name;
        this.phone = phone;
        this.addressCity = addressCity;
        this.addressDes = addressDes;
        this.isDefault = isDefault;
    }

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

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressDes() {
        return addressDes;
    }

    public void setAddressDes(String addressDes) {
        this.addressDes = addressDes;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.addressCity);
        dest.writeString(this.addressDes);
        dest.writeByte(this.isDefault ? (byte) 1 : (byte) 0);
    }

    public PersonalAddressEntity() {
    }

    protected PersonalAddressEntity(Parcel in) {
        this.name = in.readString();
        this.phone = in.readString();
        this.addressCity = in.readString();
        this.addressDes = in.readString();
        this.isDefault = in.readByte() != 0;
    }

    public static final Parcelable.Creator<PersonalAddressEntity> CREATOR = new Parcelable.Creator<PersonalAddressEntity>() {
        @Override
        public PersonalAddressEntity createFromParcel(Parcel source) {
            return new PersonalAddressEntity(source);
        }

        @Override
        public PersonalAddressEntity[] newArray(int size) {
            return new PersonalAddressEntity[size];
        }
    };
}
