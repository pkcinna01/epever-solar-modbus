package com.xmonit.solar.epever;

import org.junit.Test;

import java.util.List;
import java.util.regex.Pattern;

public class EpeverSolarChargerTest extends SolarChargerDependentTest {

  @Test
  public void findDeviceNames() {
    List<String> deviceNames = EpeverSolarCharger.findSerialPortNames(Pattern.compile("ttyX.*"));
    for(String deviceName:deviceNames) {
      System.out.println(deviceName);
    }
  }

  @Test
  public void getDeviceInfo() throws Exception {
    SolarCharger.DeviceInfo deviceInfo = solarCharger.getDeviceInfo();
    System.out.println(deviceInfo);
  }
}