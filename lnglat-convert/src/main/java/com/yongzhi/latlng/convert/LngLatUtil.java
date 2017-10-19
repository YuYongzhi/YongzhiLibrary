package com.yongzhi.latlng.convert;

/**
 * <p>
 * - Created by: yongzhi.
 * <br>
 * -       Date: 17-10-19.
 * lng = 128.543
 * lat = 37.065
 * result1 = gcj02ToBd09(lng, lat)#火星坐标系->百度坐标系
 * result2 = bd09ToGcj02(lng, lat)#百度坐标系->火星坐标系
 * result3 = wgs84ToGcj02(lng, lat)#WGS84坐标系->火星坐标系
 * result4 = gcj02ToWgs84(lng, lat)#火星坐标系->WGS84坐标系
 * result5 = bd09ToWgs84(lng, lat)#百度坐标系->WGS84坐标系
 * result6 = wgs84ToBd09(lng, lat)#WGS84坐标系->百度坐标系
 */

public class LngLatUtil {

    public static final double X_PI = 3.14159265358979324 * 3000.0d / 180.0d;
    public static final double PI = 3.1415926535897932384626; // π
    public static final double A = 6378245.0;  // 长半轴
    public static final double EE = 0.00669342162296594323;//  扁率

    /**
     * 火星坐标系(GCJ-02)转百度坐标系(BD-09)
     * 谷歌、高德——>百度
     *
     * @param longitude 火星坐标经度
     * @param latitude  火星坐标纬度
     * @return 百度坐标
     */
    public static LngLat gcj02ToBd09(double longitude, double latitude) {
        double z = Math.sqrt(longitude * longitude + latitude * latitude) + 0.00002 * Math.sin(latitude * X_PI);
        double theta = Math.atan2(latitude, longitude) + 0.000003 * Math.cos(longitude * X_PI);
        double bd_lng = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        return new LngLat(bd_lng, bd_lat);
    }

    /**
     * 百度坐标系(BD-09)转火星坐标系(GCJ-02)
     * 百度——>谷歌、高德
     *
     * @param longitude 百度坐标纬度
     * @param latitude  百度坐标经度
     * @return 火星坐标
     */
    public static LngLat bd09ToGcj02(double longitude, double latitude) {
        double x = longitude - 0.0065;
        double y = latitude - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * X_PI);
        double gg_lng = z * Math.cos(theta);
        double gg_lat = z * Math.sin(theta);
        return new LngLat(gg_lng, gg_lat);
    }

    /**
     * WGS84转GCJ02(火星坐标系)
     *
     * @param longitude WGS84坐标系的经度
     * @param latitude  WGS84坐标系的经度
     * @return 火星坐标系
     */
    public static LngLat wgs84ToGcj02(double longitude, double latitude) {
        // # 判断是否在国内
        if (outOfChina(longitude, latitude)) {
            return new LngLat(longitude, latitude);
        }

        double dlat = _transformlat(longitude - 105.0, latitude - 35.0);
        double dlng = _transformlng(longitude - 105.0, latitude - 35.0);
        double radlat = latitude / 180.0 * PI;
        double magic = Math.sin(radlat);
        magic = 1 - EE * magic * magic;
        double sqrtmagic = Math.sqrt(magic);
        dlat = (dlat * 180.0) / ((A * (1 - EE)) / (magic * sqrtmagic) * PI);
        dlng = (dlng * 180.0) / (A / sqrtmagic * Math.cos(radlat) * PI);
        double mglat = latitude + dlat;
        double mglng = longitude + dlng;
        return new LngLat(mglng, mglat);
    }

    /**
     * GCJ02(火星坐标系)转GPS84
     *
     * @param longitude 火星坐标系的经度
     * @param latitude  火星坐标系纬度
     * @return GPS84坐标
     */
    public static LngLat gcj02ToWgs84(double longitude, double latitude) {
        if (outOfChina(longitude, latitude)) {
            return new LngLat(longitude, latitude);
        }
        double dlat = _transformlat(longitude - 105.0, latitude - 35.0);
        double dlng = _transformlng(longitude - 105.0, latitude - 35.0);
        double radlat = latitude / 180.0 * PI;
        double magic = Math.sin(radlat);
        magic = 1 - EE * magic * magic;
        double sqrtmagic = Math.sqrt(magic);
        dlat = (dlat * 180.0) / ((A * (1 - EE)) / (magic * sqrtmagic) * PI);
        dlng = (dlng * 180.0) / (A / sqrtmagic * Math.cos(radlat) * PI);
        double mglat = latitude + dlat;
        double mglng = longitude + dlng;
        return new LngLat(longitude * 2 - mglng, latitude * 2 - mglat);
    }

    /**
     * 百度坐标系->WGS84坐标系
     *
     * @param longitude 百度坐标纬度
     * @param latitude  百度坐标经度
     * @return WGS84坐标
     */
    public static LngLat bd09ToWgs84(double longitude, double latitude) {
        LngLat lngLat = bd09ToGcj02(longitude, latitude);
        return gcj02ToWgs84(lngLat.getLongitude(), lngLat.getLatitude());
    }

    /**
     * WGS84坐标系->百度坐标系
     *
     * @param longitude WGS84坐标系的经度
     * @param latitude  WGS84坐标系的经度
     * @return 百度坐标
     */
    public static LngLat wgs84ToBd09(double longitude, double latitude) {
        LngLat lngLat = wgs84ToGcj02(longitude, latitude);
        return gcj02ToBd09(lngLat.getLongitude(), lngLat.getLatitude());
    }

    private static double _transformlat(double lng, double lat) {
        double ret = -100.0 + 2.0 * lng + 3.0 * lat + 0.2 * lat * lat + 0.1 * lng * lat + 0.2 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 *
                Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lat * PI) + 40.0 *
                Math.sin(lat / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(lat / 12.0 * PI) + 320 *
                Math.sin(lat * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double _transformlng(double lng, double lat) {
        double ret = 300.0 + lng + 2.0 * lat + 0.1 * lng * lng + 0.1 * lng * lat + 0.1 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 *
                Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lng * PI) + 40.0 *
                Math.sin(lng / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(lng / 12.0 * PI) + 300.0 *
                Math.sin(lng / 30.0 * PI)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * 判断是否在国内，不在国内不做偏移
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @return 是否在国内
     */
    public static boolean outOfChina(double longitude, double latitude) {
        return !(longitude > 73.66 && longitude < 135.05 && latitude > 3.86 && latitude < 53.55);
    }
}
