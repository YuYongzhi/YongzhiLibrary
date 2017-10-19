package com.yongzhi.latlng.convert;

/**
 * <p>
 * - Created by: yongzhi.
 * <br>
 * -       Date: 17-10-19.
 */

public class LngLat {

    //纬度
    private final double latitude;

    //经度
    private final double longitude;

    private final double latitudeE6;

    private final double longitudeE6;

    public LngLat(double longitude, double latitude) {
        this.latitude = latitude;
        this.longitude = longitude;

        this.latitudeE6 = latitude * 1e6;
        this.longitudeE6 = longitude * 1e6;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitudeE6() {
        return latitudeE6;
    }

    public double getLongitudeE6() {
        return longitudeE6;
    }

    @Override
    public String toString() {
        return "LngLat{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
