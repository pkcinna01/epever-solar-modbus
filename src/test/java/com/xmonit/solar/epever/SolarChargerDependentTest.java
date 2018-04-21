package com.xmonit.solar.epever;

import org.junit.After;
import org.junit.Before;

public abstract class SolarChargerDependentTest {

  public SolarCharger solarCharger = new EpeverSolarCharger();
  //public SolarCharger solarCharger = new MockSolarCharger();

  @Before
  public void setUp() throws Exception {
    solarCharger.init("ttyXRUSB1");
    //solarCharger.init("ttyACM0");
    solarCharger.connect();
  }

  @After
  public void tearDown() throws Exception {
    solarCharger.disconnect();
  }


}