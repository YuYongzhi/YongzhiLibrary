package com.yongzhi.library;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.yongzhi.latlng.convert.LngLat;
import com.yongzhi.latlng.convert.LngLatUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LngLat lngLat = new LngLat(121.431595, 31.276325);
        LngLat wgs84ToBd09 = LngLatUtil.wgs84ToBd09(lngLat.getLongitude(), lngLat.getLatitude());
        LngLat wgs84ToGcj02 = LngLatUtil.wgs84ToGcj02(lngLat.getLongitude(), lngLat.getLatitude());
        LngLat gcj02ToBd09 = LngLatUtil.gcj02ToBd09(lngLat.getLongitude(), lngLat.getLatitude());
        LngLat gcj02ToWgs84 = LngLatUtil.gcj02ToWgs84(lngLat.getLongitude(), lngLat.getLatitude());
        LngLat bd09ToGcj02 = LngLatUtil.bd09ToGcj02(lngLat.getLongitude(), lngLat.getLatitude());
        LngLat bd09ToWgs84 = LngLatUtil.bd09ToWgs84(lngLat.getLongitude(), lngLat.getLatitude());

        TextView textView = (TextView) findViewById(R.id.text);
        String text = "";
        text += "wgs84ToBd09:\n" + wgs84ToBd09 + "\n";
        text += "wgs84ToGcj02:\n" + wgs84ToGcj02 + "\n";
        text += "gcj02ToBd09:\n" + gcj02ToBd09 + "\n";
        text += "gcj02ToWgs84:\n" + gcj02ToWgs84 + "\n";
        text += "bd09ToGcj02:\n" + bd09ToGcj02 + "\n";
        text += "bd09ToWgs84:\n" + bd09ToWgs84 + "\n";
        textView.setText(text);
    }
}
