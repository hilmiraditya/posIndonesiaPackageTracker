package com.example.coba.posindonesia.Model;

public class DetailPaket {

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getYour_packet() {
        return your_packet;
    }

    public String getTotal_packet() {
        return total_packet;
    }

    public String getSum_packet_delivered() {
        return sum_packet_delivered;
    }

    public Double getEstimation_time() {
        return estimation_time;
    }

    private Double latitude;
    private Double longitude;
    private String your_packet;
    private String total_packet;
    private String sum_packet_delivered;
    private Double estimation_time;

}
