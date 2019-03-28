package com.lzq.usagestats;

import android.app.usage.ConfigurationStats;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ListView mLv;
    private BaseAdapter mAdapter;
    private final long mStartTime = 1553695200;
    private final long mEndTime = 1553698800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLv = findViewById(R.id.lv);
        mAdapter = new UsageAdapter();
        mLv.setAdapter(mAdapter);
    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onResume() {
        super.onResume();
        UsageStatsManager manager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        int appStandbyBucket = manager.getAppStandbyBucket();
        Log.d(TAG, "appStandbyBucket = " + appStandbyBucket);

        Map<String, UsageStats> stringUsageStatsMap = manager.queryAndAggregateUsageStats(Long.MIN_VALUE, Long.MAX_VALUE);
        for (Map.Entry<String, UsageStats> entry : stringUsageStatsMap.entrySet()){
            Log.d(TAG, "name = " + entry.getKey());
            Log.d(TAG, "time = " + entry.getValue().getTotalTimeInForeground());
        }
        List<ConfigurationStats> configurationStats = manager.queryConfigurations(UsageStatsManager.INTERVAL_DAILY,
                Long.MIN_VALUE, Long.MAX_VALUE);
        for (ConfigurationStats stats : configurationStats) {
            Log.d(TAG, "total = " + stats.getTotalTimeActive());
            Log.d(TAG, "firstTime =" + stats.getFirstTimeStamp());
        }
        UsageEvents usageEvents = manager.queryEvents(Long.MIN_VALUE, Long.MAX_VALUE);
        UsageEvents.Event event = new UsageEvents.Event();
        if (usageEvents.getNextEvent(event)) {
            Log.d(TAG, "class name = " + event.getClassName() + ", package name = " + event.getPackageName());
            Log.d(TAG, "event type = " + event.getEventType() + ", time stamp = " + event.getTimeStamp());
            Configuration configuration = event.getConfiguration();
        }
    }

    static private class UsageAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }


}
