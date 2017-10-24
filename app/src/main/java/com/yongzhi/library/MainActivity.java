package com.yongzhi.library;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.kevin.tipview.TipItem;
import com.kevin.tipview.TipView;
import com.yongzhi.latlng.convert.LngLat;
import com.yongzhi.latlng.convert.LngLatUtil;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout mConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mConstraintLayout = (ConstraintLayout) findViewById(R.id.root);

        LngLat lngLat = new LngLat(121.431595, 31.276325);
        LngLat wgs84ToBd09 = LngLatUtil.wgs84ToBd09(lngLat.getLongitude(), lngLat.getLatitude());
        LngLat wgs84ToGcj02 = LngLatUtil.wgs84ToGcj02(lngLat.getLongitude(), lngLat.getLatitude());
        LngLat gcj02ToBd09 = LngLatUtil.gcj02ToBd09(lngLat.getLongitude(), lngLat.getLatitude());
        LngLat gcj02ToWgs84 = LngLatUtil.gcj02ToWgs84(lngLat.getLongitude(), lngLat.getLatitude());
        LngLat bd09ToGcj02 = LngLatUtil.bd09ToGcj02(lngLat.getLongitude(), lngLat.getLatitude());
        LngLat bd09ToWgs84 = LngLatUtil.bd09ToWgs84(lngLat.getLongitude(), lngLat.getLatitude());

        final TextView textView = (TextView) findViewById(R.id.text);
        String text = "";
        text += "wgs84ToBd09:\n" + wgs84ToBd09 + "\n";
        text += "wgs84ToGcj02:\n" + wgs84ToGcj02 + "\n";
        text += "gcj02ToBd09:\n" + gcj02ToBd09 + "\n";
        text += "gcj02ToWgs84:\n" + gcj02ToWgs84 + "\n";
        text += "bd09ToGcj02:\n" + bd09ToGcj02 + "\n";
        text += "bd09ToWgs84:\n" + bd09ToWgs84 + "\n";
        textView.setText(text);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TipView.Builder(MainActivity.this, mConstraintLayout, 1200, 0)
                        .autoSize(true)
                        .setTextSize(12)
                        .padding(20, 10, 20, 10)
                        .addItem(new TipItem("一卡通麦粒消耗顺序为购买麦粒、赠品麦粒"))
                        .setOnItemClickListener(new TipView.OnItemClickListener() {
                            @Override
                            public void onItemClick(String name, int position) {

                            }

                            @Override
                            public void dismiss() {

                            }
                        })
                        .create();
            }
        });
    }
}
