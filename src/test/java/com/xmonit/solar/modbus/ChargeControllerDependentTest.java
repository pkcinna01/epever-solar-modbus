package com.xmonit.solar.modbus;

import org.junit.After;
import org.junit.Before;

public abstract class ChargeControllerDependentTest {

  public ChargeController chargeController = new EpeverChargeController();
  //public ChargeController chargeController = new MockChargeController();

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