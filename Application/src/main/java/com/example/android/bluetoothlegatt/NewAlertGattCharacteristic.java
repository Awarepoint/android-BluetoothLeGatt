package com.example.android.bluetoothlegatt;

import android.bluetooth.BluetoothGattCharacteristic;

import java.util.Arrays;
import java.util.UUID;

/**
 * Created by jlubawy on 3/12/2018.
 */

public class NewAlertGattCharacteristic extends BluetoothGattCharacteristic {
    private int mCategoryID;
    private int mNumOfNewAlerts;
    private String mText;

    public final static UUID CHAR_UUID =
            UUID.fromString("00002a46-0000-1000-8000-00805f9b34fb");

    public NewAlertGattCharacteristic(int categoryID, int numOfNewAlerts) {
        this(categoryID, numOfNewAlerts, "");
    }

    public NewAlertGattCharacteristic(int categoryID, int numOfNewAlerts, String text) {
        super(CHAR_UUID, PROPERTY_NOTIFY, 0);

        this.mCategoryID = categoryID;
        this.mNumOfNewAlerts = numOfNewAlerts;
        this.mText = text;
    }

    @Override
    public String toString() {
        return "NewAlertGattCharacteristic{" +
                "mCategoryID=" + mCategoryID +
                ", mNumOfNewAlerts=" + mNumOfNewAlerts +
                ", mText='" + mText + '\'' +
                '}';
    }

    public static NewAlertGattCharacteristic fromData(byte[] data) throws Exception {
        if (data.length < 2) {
            throw new Exception(String.format("data is too short %d", data.length));
        }
        if (data.length > 20) {
            throw new Exception(String.format("data is too long %d", data.length));
        }

        final int categoryID = (int)data[0];
        final int numOfNewAlerts = (int)data[1];
        if (data.length == 2) {
            return new NewAlertGattCharacteristic(categoryID, numOfNewAlerts);
        }

        final String text = new String(Arrays.copyOfRange(data, 2, data.length));
        return new NewAlertGattCharacteristic(categoryID, numOfNewAlerts, text);
    }
}
