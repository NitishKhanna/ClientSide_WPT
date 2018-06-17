/**
 * Created by Nitish on 6/17/2018.
 */
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;

public class ClientSide_Metrics {
    WebDriver driver = null;
    public ClientSide_Metrics(WebDriver driver){
        this.driver = driver;
    }

    public HashMap<String, String> hm = new HashMap<String, String>();
    public String firstPaint = null;

    private void webTimings(){

        JavascriptExecutor js = (JavascriptExecutor) driver;

        Object webTiming   = ((JavascriptExecutor)driver).executeScript("var timings = performance.timing || {};"+
                "return timings;");

        Object webPaint = ((JavascriptExecutor)driver).executeScript("var timings = performance.getEntriesByType(\"paint\") || {};"+
                "return timings[0].startTime;");

        String TimingArray = webTiming.toString();
        firstPaint = webPaint.toString();
        for (String timingval : TimingArray.split(",")) {
            int index =0;
            index = timingval.indexOf("=");
            hm.put(timingval.substring(0,index).replaceAll("[^A-Za-z0-9]", ""), timingval.substring(index+1).replaceAll("[^A-Za-z0-9]", ""));
        }
    }

    private double pageLoadTime(){

        return (double)(Long.parseLong(hm.get("loadEventEnd")) - Long.parseLong(hm.get("navigationStart")))/1000;
    }

    private double dNS(){

        return (double)(Long.parseLong(hm.get("domainLookupEnd")) - Long.parseLong(hm.get("domainLookupStart")))/1000;

    }

    private double tcpConnect(){

        return (double)(Long.parseLong(hm.get("connectEnd")) - Long.parseLong(hm.get("connectStart")))/1000;
    }

    private double responseTransferTime(){

        return (double)(Long.parseLong(hm.get("responseEnd")) - Long.parseLong(hm.get("responseStart")))/1000;
    }

    private double networkLatency(){

        return (double)(Long.parseLong(hm.get("requestStart")) - Long.parseLong(hm.get("fetchStart")))/1000;
    }

    private double onLoadTime(){

        return (double)(Long.parseLong(hm.get("loadEventEnd")) - Long.parseLong(hm.get("loadEventStart")))/1000;
    }

    private double requestOnServer(){

        return Math.abs((double)(Long.parseLong(hm.get("responseStart")) - Long.parseLong(hm.get("requestStart")))/1000);
    }

    private double onBrowser(){

        return (double)(Long.parseLong(hm.get("loadEventEnd")) - Long.parseLong(hm.get("domLoading")))/1000;
    }

    private double domComplete(){

        return (double)(Long.parseLong(hm.get("domComplete")) - Long.parseLong(hm.get("domLoading")))/1000;
    }

    private double domProcesstingToInteractive(){

        return (double)(Long.parseLong(hm.get("domInteractive")) - Long.parseLong(hm.get("domLoading")))/1000;
    }

    private double domInteractiveToComplete(){

        return (double)(Long.parseLong(hm.get("domComplete")) - Long.parseLong(hm.get("domInteractive")))/1000;
    }

    private double pageRendering(){

        return (double)(Long.parseLong(hm.get("loadEventEnd")) - Long.parseLong(hm.get("responseStart")))/1000;
    }

    private double navigationTime(){

        return (double)(Long.parseLong(hm.get("fetchStart")) - Long.parseLong(hm.get("navigationStart")))/1000;
    }

    private double responseTime(){

        return (double)(Long.parseLong(hm.get("responseEnd")) - Long.parseLong(hm.get("navigationStart")))/1000;
    }

    private double ttfb () {

        return (double)(Long.parseLong(hm.get("responseStart")) - Long.parseLong(hm.get("fetchStart")))/1000;
    }

    private String firstPaint () {
        return firstPaint.substring(0, firstPaint.indexOf(".") + 3);
    }

    public void calculateAllMetrices(){

        webTimings();
        System.out.println(driver.getCurrentUrl());
        System.out.println("========================================================");
        System.out.println("Page Load Time: "+pageLoadTime() + " seconds");
        System.out.println("DNS: "+dNS());
        System.out.println("TCP Connect: "+tcpConnect());
        System.out.println("Response Transfer Time: "+responseTransferTime());
        System.out.println("Net Latency: "+networkLatency());
        System.out.println("On Load Time: "+onLoadTime());
        System.out.println("Request on Server: "+requestOnServer());
        System.out.println("DOM Loadig: "+onBrowser());
        System.out.println("domProcesstingToInteractive: "+domProcesstingToInteractive());
        System.out.println("domInteractiveToComplete: "+domInteractiveToComplete());
        System.out.println("DOM Complete Time "+domComplete());
        System.out.println("Page Rendering Time: "+pageRendering());
        System.out.println("Navigation time: "+navigationTime());
        System.out.println("Response Time: "+responseTime());
        System.out.println("TTFB: " + ttfb());
        System.out.println("First Paint: "+ firstPaint());
        System.out.println("========================================================");
        System.out.println("   ");
    }
}