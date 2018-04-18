package com.xmonit.solar.modbus;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.regex.Pattern;

public class EpeverChargeControllerTest extends ChargeControllerDependentTest {

  @Test
  public void findDeviceNames() {
    List<String> deviceNames = EpeverChargeController.findDeviceNames(Pattern.compile("ttyX.*"));
    for(String deviceName:deviceNames) {
      System.out.println(deviceName);
    }
  }

  @Test
  public void getDeviceInfo() throws Exception {
    List<String> infoList = chargeController.getDeviceInfo();
    for(String info: infoList) {
      System.out.println(info);
    }
  }
}