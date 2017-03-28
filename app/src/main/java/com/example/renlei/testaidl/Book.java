package com.example.renlei.testaidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Time 2017/3/27.
 * User renlei
 * Email renlei@xiaomi.com
 */

public class Book implements Parcelable {
    public int mBookId;
    public String mBookName;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mBookId);
        dest.writeString(this.mBookName);
    }

    @Override
    public String toString() {
        return "Book{" +
                "mBookId=" + mBookId +
                ", mBookName='" + mBookName + '\'' +
                '}';
    }

    public Book() {
    }

    public Book(int mBookId, String mBookName) {
        this.mBookId = mBookId;
        this.mBookName = mBookName;
    }

    protected Book(Parcel in) {
        this.mBookId = in.readInt();
        this.mBookName = in.readString();
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
