package edu.umb.cs681.hw19;

public class NASAData {
    private String year;
    private String doy;
    private double prectotcorr;
    private double rh2m;
    private double gwetprof;
    private double gwetroot;
    private double gwettop;
    private double t2m;
    private double ws10m;
    private double allsky_sfc_sw_dwn;

    public NASAData(String year, String doy, double prectotcorr, double rh2m, double gwetprof, double gwetroot, double gwettop, double t2m, double ws10m, double allsky_sfc_sw_dwn) {
        this.year = year;
        this.doy = doy;
        this.prectotcorr = prectotcorr;
        this.rh2m = rh2m;
        this.gwetprof = gwetprof;
        this.gwetroot = gwetroot;
        this.gwettop = gwettop;
        this.t2m = t2m;
        this.ws10m = ws10m;
        this.allsky_sfc_sw_dwn = allsky_sfc_sw_dwn;
    }

    public String getYear() {
        return year;
    }

    public String getDoy() {
        return doy;
    }

    public double getPrectotcorr() {
        return prectotcorr;
    }

    public double getRh2m() {
        return rh2m;
    }

    public double getGwetprof() {
        return gwetprof;
    }

    public double getGwetroot() {
        return gwetroot;
    }

    public double getGwettop() {
        return gwettop;
    }

    public double getT2m() {
        return t2m;
    }

    public double getWs10m() {
        return ws10m;
    }

    public double getAllskySfcSwDwn() {
        return allsky_sfc_sw_dwn;
    }
}