package com.xmonit.solar.modbus;

import org.junit.After;
import org.junit.Before;

public abstract class ChargeControllerDependentTest {

  ChargeController chargeController = new EpeverChargeController();
  //ChargeController chargeController = new MockChargeController();

  @Before
  public void setUp() throws Exception {
    chargeController.init("ttyXRUSB1");
    chargeController.connect();
  }

  @After
  public void tearDown() throws Exception {
    chargeController.disconnect();
  }


}