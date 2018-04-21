package com.xmonit.solar.epever;

import org.junit.Test;

import java.util.List;
import java.util.regex.Pattern;

public class EpeverSolarChargerTest extends SolarChargerDependentTest {

  @Test
  public void findDeviceNames() {
    List<String> deviceNames = EpeverSolarCharger.findSerialNames(Pattern.compile("ttyX.*"));
    for(String deviceName:deviceNames) {
      System.out.println(deviceName);
    }
  }

  @Test
  public void getDeviceInfo() throws Exception {
    List<String> infoList = solarCharger.getDeviceInfo();
    for(String info: infoList) {
      System.out.println(info);
    }
  }
}