// IAddBookCallBack.aidl
package com.example.renlei.testaidl;

// Declare any non-default types here with import statements

interface IAddBookCallBack {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    void onSuccess(String msg);
    void onFail(String msg);
}
