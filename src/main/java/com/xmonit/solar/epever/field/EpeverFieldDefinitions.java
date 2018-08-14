package com.xmonit.solar.epever.field;

import com.xmonit.solar.epever.SolarCharger;
import com.xmonit.solar.epever.field.*;
import com.xmonit.solar.epever.units.HexCodes;

import java.util.function.BiFunction;

import static com.xmonit.solar.epever.units.Unit.*;


public enum EpeverFieldDefinitions {


    RATED_INPUT_VOLTAGE( 0x3000, (cc, addr) -> new VoltageField(addr, "Rated Input Voltage", "PV array terminal rated input voltage")),
    RATED_INPUT_CURRENT( 0x3001, (cc, addr) -> new CurrentField(addr, "Rated Input Current", "PV array termianl rated input current")),
    RATED_INPUT_POWER( 0x3002, (cc, addr) -> new PowerField(addr, "Rated Input Power", "PV array terminal rated input power").setCount(2)),
    RATED_OUTPUT_VOLTAGE( 0x3004, (cc, addr) -> new VoltageField(addr, "Rated Output Voltage", "Battery terminal rated output voltage") ),
    RATED_OUTPUT_CURRENT( 0x3005, (cc, addr) -> new CurrentField(addr, "Rated Output Current", "Battery terminal rated output current")),
    RATED_OUTPUT_POWER( 0x3006, (cc, addr) -> new PowerField(addr, "Rated Output Power", "Battery terminal rated output power").setCount(2)),
    CHARGING_MODE( 0x3008, (cc, addr) -> new CodeField(addr, 1, "Charging mode used by this controller", HexCodes.chargingMode)),
    RATED_OUTPUT_LOAD_CURRENT( 0x300E, (cc, addr) -> new CurrentField(addr, "Rated Output Load Current", "Load terminal rated output current")),

    INPUT_VOLTAGE( 0x3100, (cc, addr) -> new VoltageField(addr, "Input Voltage", "PV array terminal present input voltage")),
    INPUT_CURRENT( 0x3101, (cc, addr) -> new CurrentField(addr, "Input Current", "PV array terminal present input current")),
    INPUT_POWER( 0x3102, (cc, addr) -> new PowerField(addr, "Input Power", "PV array terminal present input power").setCount(2)),
    OUTPUT_VOLTAGE( 0x3104, (cc, addr) -> new VoltageField(addr, "Output Voltage", "Battery terminal present output voltage")),
    OUTPUT_CURRENT( 0x3105, (cc, addr) -> new CurrentField(addr, "Output Current", "Battery terminal present output current")),
    OUTPUT_POWER( 0x3106, (cc, addr) -> new PowerField(addr, "Output Power", "Battery terminal present output power").setCount(2)),

    OUTPUT_LOAD_VOLTAGE( 0x310C, (cc, addr) -> new VoltageField(addr, "Output Load Voltage", "Load terminal present output voltage")),
    OUTPUT_LOAD_CURRENT( 0x310D, (cc, addr) -> new CurrentField(addr, "Output Load Current", "Load terminal present output current")),
    OUTPUT_LOAD_POWER( 0x310E, (cc, addr) -> new PowerField(addr, "Output Load Power", "Load terminal present output power").setCount(2)),
    BATTERY_TEMPERATURE( 0x3110, (cc, addr) -> new TemperatureField(addr, "Battery Temperature", "Battery temperature")),
    TEMPERATURE( 0x3111, (cc, addr) -> new TemperatureField(addr, "Internal Temperature", "Internal device temperature")),
    SURFACE_TEMPERATURE( 0x3112, (cc, addr) -> new TemperatureField(addr, "External Temperature", "External device temperature (heat sink surface)")),
    BATTERY_SOC( 0x311A, (cc, addr) -> new PercentageField(addr, "Battery SOC", "The percentage of battery's remaining capacity")),
    REMOTE_BATTERY_TEMPERATURE( 0x311B, (cc, addr) -> new TemperatureField(addr, "Battery Probe Temperature", "Battery probe temperature input (thermistor)")),
    BATTERY_REAL_RATED_POWER( 0x311D, (cc, addr) -> new PowerField(addr, "Rated Battery Power", "Assigned/Detected battery bank voltage (12V or 24V)")),

    // Status
    BATTERY_STATUS( 0x3200, (cc, addr) -> new CodeField(addr, 1, "Various battery health indicators", HexCodes.batteryStatus)),
    CHARGING_EQUIPMENT_STATUS(0x3201, (cc, addr) -> new CodeField(addr, 1, "Various charge controller health indicators", HexCodes.chargingEquipmentStatus)),

    // Statistics
    MAXIMUM_INPUT_VOLT_PV_TODAY( 0x3300, (cc, addr) -> new VoltageField(addr, "Maximum Input Voltage - Today", "PV array terminal maximum voltage since midnight")),
    MINIMUM_INPUT_VOLT_PV_TODAY(0x3301 , (cc, addr) -> new VoltageField(addr, "Minimum Input Voltage - Today", "PV array terminal minimum voltage since midnight")),
    MAXIMUM_BATTERY_VOLT_TODAY( 0x3302, (cc, addr) -> new VoltageField(addr, "Maximum Output Voltage - Today", "Battery terminal maximum voltage since midnight")),
    MINIMUM_BATTERY_VOLT_TODAY( 0x3303, (cc, addr) -> new VoltageField(addr, "Minimum Output Voltage - Today", "Battery terminal minimum voltage since midnight")),
    CONSUMED_ENERGY_TODAY( 0x3304, (cc, addr) -> new FloatField(addr, KWH, "Consumed Energy - Today", "Energy consumed since midnight", 100, 1)),
    CONSUMED_ENERGY_THIS_MONTH( 0x3306, (cc, addr) -> new FloatField(addr, KWH, "Consumed Energy - Month", "Energy consumed since first day of month", 100, 1)),
    CONSUMED_ENERGY_THIS_YEAR( 0x3308, (cc, addr) -> new FloatField(addr, KWH, "Consumed Energy - Year", "Energy consumed since January 1st", 100, 1)),
    TOTAL_CONSUMED_ENERGY( 0x330A, (cc, addr) -> new FloatField(addr, KWH, "Consumed Energy", "Total consumed energy for device", 100, 1)),
    GENERATED_ENERGY_TODAY(0x330C , (cc, addr) -> new FloatField(addr, KWH, "Generated Energy - Today", "Energy generated since midnight", 100, 1)),
    GENERATED_ENERGY_THIS_MONTH( 0x330E, (cc, addr) -> new FloatField(addr, KWH, "Generated Energy - Month", "Energy generated since first day of month.", 100, 1)),
    GENERATED_ENERGY_THIS_YEAR( 0x3310, (cc, addr) -> new FloatField(addr, KWH, "Generated Energy - Year", "Energy generated since January 1st", 100, 1)),
    TOTAL_GENERATED_ENERGY( 0x3312, (cc, addr) -> new FloatField(addr, KWH, "Generated Energy", "Total generated energy for device", 100, 1)),
    CARBON_DIOXIDE_REDUCTION( 0x3314, (cc, addr) -> new FloatField(addr, Ton, "Carbon Dioxide Reduction", "Saving 1 Kilowatt=Reduction 0.997KG''Carbon dioxide ''=Reduction 0.272KG''Carton''", 100, 1)),

    BATTERY_VOLTAGE( 0x331A, (cc, addr) -> new VoltageField(addr, "Battery Voltage", "Battery Voltage")),
    BATTERY_CURRENT( 0x331B, (cc, addr) -> new CurrentField(addr, "Battery Current", "Net battery current (charging minus discharging).", 100, 1)),
    BATTERY_TEMP( 0x331D, (cc, addr) -> new TemperatureField(addr, "Battery Temperature", "Battery Temperature")),
    AMBIENT_TEMP( 0x331E, (cc, addr) -> new TemperatureField(addr, "Ambient Temperature", "Ambient Temperature")),

    //# Setting Parameter (read-write) holding register
    BATTERY_TYPE( 0x9000, (cc, addr) -> new CodeField(addr, 1, "Configurable battery type (default is lead acid sealed)", HexCodes.batteryType)),
    BATTERY_CAPACITY( 0x9001, (cc, addr) -> new FloatField(addr, AmpHours, "Battery Capacity", "Rated capacity of the battery", 1, 1)),
    TEMP_COMPENSATION_COEFFICIENT( 0x9002, (cc, addr) -> new CurrentField(addr, "Temperature Compensation Coefficient", "Range 0-9 mV/C/2V")),
    HIGH_VOLT_DISCONNECT( 0x9003, (cc, addr) -> new VoltageField(addr, "High Voltage Disconnect", "High voltage disconnect")),
    CHARGING_LIMIT_VOLTAGE( 0x9004, (cc, addr) -> new VoltageField(addr, "Charging Voltage Limit", "Charging voltage limit")),
    OVER_VOLTAGE_RECONNECT( 0x9005, (cc, addr) -> new VoltageField(addr, "High Voltage Reconnect", "High voltage reconnect after disconnect triggered")),
    EQUALIZATION_VOLTAGE( 0x9006, (cc, addr) -> new VoltageField(addr, "Equalization Voltage", "Equalization voltage")),
    BOOST_VOLTAGE( 0x9007, (cc, addr) -> new VoltageField(addr, "Boost Voltage", "Boost voltage")),
    FLOAT_VOLTAGE( 0x9008, (cc, addr) -> new VoltageField(addr, "Float Voltage", "Float voltage")),
    BOOST_RECONNECT_VOLTAGE( 0x9009, (cc, addr) -> new VoltageField(addr, "Boost Reconnect Voltage", "Boost reconnect voltage")),
    LOW_VOLTAGE_RECONNECT( 0x900A, (cc, addr) -> new VoltageField(addr, "Low Voltage Reconnect", "Low voltage reconnect after disconnect")),
    UNDER_VOLTAGE_RECOVER( 0x900B, (cc, addr) -> new VoltageField(addr, "Under Voltage Recover", "Under voltage recover")),
    UNDER_VOLTAGE_WARNING( 0x900C, (cc, addr) -> new VoltageField(addr, "Under Voltage Warning", "Under voltage warning")),
    LOW_VOLTAGE_DISCONNECT( 0x900D, (cc, addr) -> new VoltageField(addr, "Low Voltage Disconnect", "Low voltage disconnect")),
    DISCHARGING_LIMIT_VOLTAGE( 0x900E, (cc, addr) -> new VoltageField(addr, "Discharge Voltage Limit", "Discharge voltage limit")),

    REAL_TIME_CLOCK( 0x9013, (cc, addr) -> new DateTimeField(addr, "Time", "Register 0: D7-0 Sec, D15-8 Min, Register 1: D7-0 Hour, D15-8 Day, Register 2: D7-0 Month, D15-8 Year")),
    EQUALIZATION_CHARGING_CYCLE( 0x9016, (cc, addr) -> new FloatField(addr, Int, "Equalization Charge Cycle", "Interval days of auto equalization charging in cycle Day", 1, 1)),
    BATTERY_TEMPERATURE_WARNING_UPPER_LIMIT( 0x9017, (cc, addr) -> new TemperatureField(addr, "Battery High Temperature Warning Limit", "Battery temperature warning upper limit")),
    BATTERY_TEMPERATURE_WARNING_LOWER_LIMIT( 0x9018, (cc, addr) -> new TemperatureField(addr, "Battery Low Temperature Warning Limit", "Battery temperature warning lower limit")),
    CONTROLLER_INNER_TEMPERATURE_UPPER_LIMIT( 0x9019, (cc, addr) -> new TemperatureField(addr, "Internal Temperature Upper Limit", "Controller inner temperature upper limit")),
    CONTROLLER_INNER_TEMPERATURE_UPPER_LIMIT_RECOVER( 0x901A, (cc, addr) -> new TemperatureField(addr, "Interal Temperature Upper Limit Recover", "After Over temperature, system recover once it drop to lower than this value")),
    POWER_COMPONENT_TEMPERATURE_UPPER_LIMIT( 0x901B, (cc, addr) -> new TemperatureField(addr, "External Temperature Upper Limit", "Warn when surface temperature exceeds this limit and stop charging and discharging")),
    POWER_COMPONENT_TEMPERATURE_UPPER_LIMIT_RECOVER( 0x901C, (cc, addr) -> new TemperatureField(addr, "External Temperature Upper Limit Recover", "Recover once surface temperature is below this value")),
    LINE_IMPEDANCE( 0x901D, (cc, addr) -> new FloatField(addr, mOHM, "Line Impedance", "The resistance of the connectted wires", 100,1)),
    NIGHT_TIMETHRESHOLD_VOLT_NTTV( 0x901E, (cc, addr) -> new VoltageField(addr, "Night Threshold Voltage", "NTTV - PV threshold voltage used to determine sundown")),
    LIGHT_SIGNAL_STARTUP_NIGHT_DELAY_TIME( 0x901F, (cc, addr) -> new FloatField(addr, Minutes, "Night Start Delay", "Enable load terminal for night lights after NTTV detected and this delay in minutes", 1, 1)),
    DAY_TIME_THRESHOLD_VOLT_DTTV( 0x9020, (cc, addr) -> new VoltageField(addr, "Day Threshold Voltage", "DTTV - PV threshold voltage used to determine sunrise")),
    LIGHT_SIGNAL_TURN_OFF_DAY_DELAY_TIME( 0x9021, (cc, addr) -> new FloatField(addr, Minutes, "Day Start Delay", "Disable load terminal for night lights after DTTV detected and this delay in minutes", 1, 1)),

    LOAD_CONTROLING_MODES( 0x903D, (cc, addr) -> new CodeField(addr, 1, "Scheduling method used to enable the load terminal", HexCodes.loadControl)),
    WORKING_TIME_LENGTH_1(0x903E, (cc,addr) -> new DurationField(addr,"Load Timer 1 - Duration", "Load terminal enabled for this duration (D15-D8: hour, D7-D0: minute)")),
    WORKING_TIME_LENGTH_2(0x903F, (cc,addr) -> new DurationField(addr,"Load Timer 2 - Duration", "Load terminal enabled for this duration (D15-D8: hour, D7-D0: minute)")),
    TURN_ON_TIMING_1(0x9042, (cc,addr) -> new TimeField(addr,"Load Time Range 1 - Start","Load terminal turned on")),
    TURN_OFF_TIMING_1(0x9045, (cc,addr) -> new TimeField(addr,"Load Time Range 1 - End","Load terminal turned off")),
    TURN_ON_TIMING_2(0x9048, (cc,addr) -> new TimeField(addr,"Load Time Range 2 - Start","Load terminal turned on")),
    TURN_OFF_TIMING_2(0x904B, (cc,addr) -> new TimeField(addr,"Load Time Range 2 - End","Load terminal turned off")),
    LENGTH_OF_NIGHT(0x9065, (cc,addr) -> new DurationField(addr,"Length of Night - Duration", "")),
    BATTERY_RATED_VOLTAGE_CODE( 0x9067, (cc, addr) -> new CodeField(addr, 1, "Auto detect battery voltage or explicitly assign it", HexCodes.batteryRatedVoltage)),
    LOAD_TIMING_CONTROL_SELECTION( 0x9069, (cc, addr) -> new CodeField(addr, 1, "Enable one or both timer time ranges when in 'Time Control' mode", HexCodes.loadTimingControlMode)),
    DEFAULT_LOAD_ON_OFF_IN_MANUAL_MODE( 0x906A, (cc, addr) -> new CodeField(addr, 1, "Load terminal on/off initial state when in 'Manual Control' mode", new HexCodes.OnOff("Manual Mode Load On/Off Default"))),
    EQUALIZE_DURATION(0x906B, (cc,addr) -> new FloatField(addr, Minutes, "Equalize Duration", "Usually 60-120 minutes.", 1, 1)),
    BOOST_DURATION(0x906C, (cc,addr) -> new FloatField(addr, Minutes, "Boost Duration", "Usually 60-120 minutes.", 1, 1)),
    DISCHARGING_PERCENTAGE(0x906D, (cc,addr) -> new PercentageField(addr, "Discharge Threshold", "Usually 20%-80%. The percentage of battery's remaining capacity when stop charging")),
    CHARGING_PERCENTAGE(0x906E, (cc,addr) -> new PercentageField(addr, "Charge Threshold", "Depth of charge, 20%-100%.")),
    MANAGEMENT_MODES_OF_BATTERY_CHARGING_AND_DISCHARGING(0x9070, (cc,addr) -> new CodeField(addr, 1, "Voltage Compensation or Saturation of Charge Battery Managment", new HexCodes("Battery Mode").add(0x00,"Voltage Compensation").add(0x01,"SOC"))),

    // Coils
    LOAD_CONTROL_MODE(0x0002, (cc,addr) -> new BooleanField(addr, "Manual Mode On")),
    LOAD_TEST_MODE(0x0005, (cc,addr) -> new BooleanField(addr, "Load Test Mode On")),
    LOAD_ENABLED(0x0006, (cc,addr) -> new BooleanField(addr, "Force Load On", "Temporarily enable load terminal")),

    // Discrete Inputs
    DEVICE_TEMPERATURE_THRESHOLD_EXCEEDED(0x2000, (cc,addr) -> new BooleanField(addr,"Internal Temperature Threshold Exceeded")),
    IS_NIGHT(0x200C, (cc,addr) -> new BooleanField(addr, "Is Night", "True: Night, False: Day"));


    private BiFunction<SolarCharger,Integer,EpeverField> fieldFactory;

    public final int registerAddress;

    EpeverFieldDefinitions(int registerAddress, BiFunction<SolarCharger,Integer,EpeverField> fieldFactory ) {
        this.registerAddress = registerAddress;
        this.fieldFactory = fieldFactory;
    }

    public EpeverField create(SolarCharger cc) {
        EpeverField field = fieldFactory.apply(cc,registerAddress);
        field.setSolarCharger(cc);
        return field;
    }

}