package ua.com.crosp.solutions.hearthealthmonitor.module.report.infrastructure.transport.valueobject;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Alexander Molochko on 12/20/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class Recipient {
    private String mAddress;

    public Recipient(String address) {
        mAddress = address;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public static class List extends ArrayList<Recipient> {
        public List(int initialCapacity) {
            super(initialCapacity);
        }

        public List() {
        }

        public List(@NonNull Collection<? extends Recipient> c) {
            super(c);
        }


        public String[] toStringList() {
            String[] addresses = new String[this.size()];
            for (int i = 0; i < size(); i++) {
                addresses[i] = this.get(i).getAddress();
            }
            return addresses;
        }
    }
}
