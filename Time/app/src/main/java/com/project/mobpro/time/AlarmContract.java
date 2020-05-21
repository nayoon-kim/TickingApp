package com.project.mobpro.time;

import android.provider.BaseColumns;

public class AlarmContract {
    private AlarmContract(){}

    public static class AlarmEntry implements BaseColumns {
        public static final String TABLE_NAME="alarm";
        public static final String COLUMN_NAME_TIME="time";
        public static final String COLUMN_NAME_DAY="day";
        public static final String COLUMN_NAME_SOUND="sound";
        public static final String COLUMN_NAME_VIB="vib";
    }
}
