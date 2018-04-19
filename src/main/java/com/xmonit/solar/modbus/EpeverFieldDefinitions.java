package com.xmonit.solar.modbus;

import com.xmonit.solar.modbus.field.*;
import com.xmonit.solar.modbus.units.HexCodes;

import java.util.function.BiFunction;

import static com.xmonit.solar.modbus.units.Unit.*;


public enum EpeverFieldDefinitions {


    CE_RATED_INPUT_VOLTAGE( 0x3000, (cc, addr) -> new VoltageField(addr, "Charging Equipment Rated Input VoltageField", "PV array rated voltage")),
    CE_RATED_INPUT_CURRENT( 0x3001, (cc, addr) -> new CurrentField(addr, "Charging Equipment Rated Input CurrentField", "PV array rated current")),
    CE_RATED_INPUT_POWER( 0x3002, (cc, addr) -> new PowerField(addr, "Charging equipment rated input power", "PV array rated power")),
    CE_RATED_OUTPUT_VOLTAGE( 0x3004, (cc, addr) -> new VoltageField(addr, "Charging equipment rated output voltage", "Battery voltage") ),
    CE_RATED_OUTPUT_CURRENT( 0x3005, (cc, addr) -> new CurrentField(addr, "Charging equipment rated output current", "Rated charging current to battery")),
    CE_RATED_OUTPUT_POWER( 0x3006, (cc, addr) -> new PowerField(addr, "Charging equipment rated output power", "Rated charging power to battery")),
    CHARGING_MODE( 0x3008, (cc, addr) -> new CodesField(addr, 1, HexCodes.chargingMode)),
    LOAD_RATED_OUTPUT_CURRENT( 0x300E, (cc, addr) -> new CurrentField(addr, "Rated output current of load", "")),

    CE_INPUT_VOLTAGE( 0x3100, (cc, addr) -> new VoltageField(addr, "Charging equipment input voltage", "Solar charge controller--PV array voltage")),
    CE_INPUT_CURRENT( 0x3101, (cc, addr) -> new CurrentField(addr, "Charging equipment input current", "Solar charge controller--PV array current")),
    CE_INPUT_POWER( 0x3102, (cc, addr) -> new PowerField(addr, "Charging equipment input power", "Solar charge controller--PV array power")),
    CE_OUTPUT_VOLTAGE( 0x3104, (cc, addr) -> new VoltageField(addr, "Charging equipment output voltage", "Battery voltage")),
    CE_OUTPUT_CURRENT( 0x3105, (cc, addr) -> new CurrentField(addr, "Charging equipment output current", "Battery charging current")),
    CE_OUTPUT_POWER( 0x3106, (cc, addr) -> new PowerField(addr, "Charging equipment output power", "Battery charging power")),

    LOAD_OUTPUT_VOLTAGE( 0x310C, (cc, addr) -> new VoltageField(addr, "Discharging equipment output voltage", "Load voltage")),
    LOAD_OUTPUT_CURRENT( 0x310D, (cc, addr) -> new CurrentField(addr, "Discharging equipment output current", "Load current")),
    LOAD_OUTPUT_POWER( 0x310E, (cc, addr) -> new PowerField(addr, "Discharging equipment output power", "Load power")),
    BATTERY_TEMPERATURE( 0x3110, (cc, addr) -> new TemperatureField(addr, "Battery TemperatureField", "Battery TemperatureField")),
    CE_TEMPERATURE( 0x3111, (cc, addr) -> new TemperatureField(addr, "TemperatureField inside equipment", "TemperatureField inside case")),
    CE_SURFACE_TEMPERATURE( 0x3112, (cc, addr) -> new TemperatureField(addr, "PowerField components temperature", "Heat sink surface temperature of equipments' power components")),
    BATTERY_SOC( 0x311A, (cc, addr) -> new PercentageField(addr, "Battery SOC", "The percentage of battery's remaining capacity")),
    REMOTE_BATTERY_TEMPERATURE( 0x311B, (cc, addr) -> new TemperatureField(addr, "Remote battery temperature", "The battery tempeture measured by remote temperature sensor")),
    BATTERY_REAL_RATED_POWER( 0x311D, (cc, addr) -> new PowerField(addr, "Battery's real rated power", "CurrentField system rated votlage. 1200, 2400 represent 12V, 24V").setRegCnt(1)),

    // Status
    BATTERY_STATUS( 0x3200, (cc, addr) -> new CodesField(addr, 1, HexCodes.batteryStatus)),
    CHARGING_EQUIPMENT_STATUS(0x3201, (cc, addr) -> new CodesField(addr, 1, HexCodes.chargingEquipmentStatus)),

    // Statistics
    MAXIMUM_INPUT_VOLT_PV_TODAY( 0x3300, (cc, addr) -> new VoltageField(addr, "Maximum input volt (PV) today", "00: 00 Refresh every day")),
    MINIMUM_INPUT_VOLT_PV_TODAY(0x3301 , (cc, addr) -> new VoltageField(addr, "Minimum input volt (PV) today", "00: 00 Refresh every day")),
    MAXIMUM_BATTERY_VOLT_TODAY( 0x3302, (cc, addr) -> new VoltageField(addr, "Maximum battery volt today", "00: 00 Refresh every day")),
    MINIMUM_BATTERY_VOLT_TODAY( 0x3303, (cc, addr) -> new VoltageField(addr, "Minimum battery volt today", "00: 00 Refresh every day")),
    CONSUMED_ENERGY_TODAY( 0x3304, (cc, addr) -> new FloatField(addr, KWH, "Consumed energy today", "00: 00 Clear every day", 100, 2)),
    CONSUMED_ENERGY_THIS_MONTH( 0x3306, (cc, addr) -> new FloatField(addr, KWH, "Consumed energy this month", "00: 00 Clear on the first day of month", 100, 2)),
    CONSUMED_ENERGY_THIS_YEAR( 0x3308, (cc, addr) -> new FloatField(addr, KWH, "Consumed energy this year", "00: 00 Clear on 1, Jan.", 100, 2)),
    TOTAL_CONSUMED_ENERGY( 0x330A, (cc, addr) -> new FloatField(addr, KWH, "Total consumed energy", "Total consumed energy", 100, 2)),
    GENERATED_ENERGY_TODAY(0x330C , (cc, addr) -> new FloatField(addr, KWH, "Generated energy today", "00: 00 Clear every day.", 100, 2)),
    GENERATED_ENERGY_THIS_MONTH( 0x330E, (cc, addr) -> new FloatField(addr, KWH, "Generated energy this month", "00: 00 Clear on the first day of month.", 100, 2)),
    GENERATED_ENERGY_THIS_YEAR( 0x3310, (cc, addr) -> new FloatField(addr, KWH, "Generated energy this year", "00: 00 Clear on 1, Jan.", 100, 2)),
    TOTAL_GENERATED_ENERGY( 0x3312, (cc, addr) -> new FloatField(addr, KWH, "Total generated energy", "Total generated energy", 100, 2)),
    CARBON_DIOXIDE_REDUCTION( 0x3314, (cc, addr) -> new FloatField(addr, Ton, "Carbon dioxide reduction", "Saving 1 Kilowatt=Reduction 0.997KG''Carbon dioxide ''=Reduction 0.272KG''Carton''", 100, 2)),
    BATTERY_CURRENT( 0x331B, (cc, addr) -> new CurrentField(addr, "Battery CurrentField", "Net battery current (charging minus discharging).", 100, 2)),
    BATTERY_TEMP( 0x331D, (cc, addr) -> new TemperatureField(addr, "Battery Temp", "Battery Temp")),
    AMBIENT_TEMP( 0x331E, (cc, addr) -> new TemperatureField(addr, "Ambient Temp", "Ambient Temp")),

    //# Setting Parameter (read-write) holding register
    BATTERY_TYPE( 0x9000, (cc, addr) -> new CodesField(addr, 1, HexCodes.batteryType)),
    BATTERY_CAPACITY( 0x9001, (cc, addr) -> new FloatField(addr, AmpHours, "Battery Capacity", "Rated capacity of the battery", 1, 1)),
    TEMP_COMPENSATION_COEFFICIENT( 0x9002, (cc, addr) -> new CurrentField(addr, "TemperatureField compensation coefficient", "Range 0-9 mV/C/2V")),
    HIGH_VOLT_DISCONNECT( 0x9003, (cc, addr) -> new VoltageField(addr, "High Volt.disconnect", "High Volt.disconnect")),
    CHARGING_LIMIT_VOLTAGE( 0x9004, (cc, addr) -> new VoltageField(addr, "Charging limit voltage", "Charging limit voltage")),
    OVER_VOLTAGE_RECONNECT( 0x9005, (cc, addr) -> new VoltageField(addr, "Over voltage reconnect", "Over voltage reconnect")),
    EQUALIZATION_VOLTAGE( 0x9006, (cc, addr) -> new VoltageField(addr, "Equalization voltage", "Equalization voltage")),
    BOOST_VOLTAGE( 0x9007, (cc, addr) -> new VoltageField(addr, "Boost voltage", "Boost voltage")),
    FLOAT_VOLTAGE( 0x9008, (cc, addr) -> new VoltageField(addr, "FloatField voltage", "FloatField voltage")),
    BOOST_RECONNECT_VOLTAGE( 0x9009, (cc, addr) -> new VoltageField(addr, "Boost reconnect voltage", "Boost reconnect voltage")),
    LOW_VOLTAGE_RECONNECT( 0x900A, (cc, addr) -> new VoltageField(addr, "Low voltage reconnect", "Low voltage reconnect")),
    UNDER_VOLTAGE_RECOVER( 0x900B, (cc, addr) -> new VoltageField(addr, "Under voltage recover", "Under voltage recover")),
    UNDER_VOLTAGE_WARNING( 0x900C, (cc, addr) -> new VoltageField(addr, "Under voltage warning", "Under voltage warning")),
    LOW_VOLTAGE_DISCONNECT( 0x900D, (cc, addr) -> new VoltageField(addr, "Low voltage disconnect", "Low voltage disconnect")),
    DISCHARGING_LIMIT_VOLTAGE( 0x900E, (cc, addr) -> new VoltageField(addr, "Discharging limit voltage", "Discharging limit voltage")),
    REAL_TIME_CLOCK( 0x9013, (cc, addr) -> new DateTimeField(addr, "Real time clock", "Register 0: D7-0 Sec, D15-8 Min, Register 1: D7-0 Hour, D15-8 Day, Register 2: D7-0 Month, D15-8 Year")),
    EQUALIZATION_CHARGING_CYCLE( 0x9016, (cc, addr) -> new FloatField(addr, Int, "Equalization charging cycle", "Interval days of auto equalization charging in cycle Day", 1, 1)),
    BATTERY_TEMPERATURE_WARNING_UPPER_LIMIT( 0x9017, (cc, addr) -> new TemperatureField(addr, "Battery temperature warning upper limit", "Battery temperature warning upper limit")),
    BATTERY_TEMPERATURE_WARNING_LOWER_LIMIT( 0x9018, (cc, addr) -> new TemperatureField(addr, "Battery temperature warning lower limit", "Battery temperature warning lower limit")),
    CONTROLLER_INNER_TEMPERATURE_UPPER_LIMIT( 0x9019, (cc, addr) -> new TemperatureField(addr, "Controller inner temperature upper limit", "Controller inner temperature upper limit")),
    CONTROLLER_INNER_TEMPERATURE_UPPER_LIMIT_RECOVER( 0x901A, (cc, addr) -> new TemperatureField(addr, "Controller inner temperature upper limit recover", "After Over TemperatureField, system recover once it drop to lower than this value")),
    POWER_COMPONENT_TEMPERATURE_UPPER_LIMIT( 0x901B, (cc, addr) -> new TemperatureField(addr, "PowerField component temperature upper limit", "Warning when surface temperature of power components higher than this value, and charging and discharging stop")),
    POWER_COMPONENT_TEMPERATURE_UPPER_LIMIT_RECOVER( 0x901C, (cc, addr) -> new TemperatureField(addr, "PowerField component temperature upper limit recover", "Recover once power components temperature lower than this value")),
    LINE_IMPEDANCE( 0x901D, (cc, addr) -> new FloatField(addr, mOHM, "Line Impedance", "The resistance of the connectted wires.", 100,1)),
    NIGHT_TIMETHRESHOLD_VOLT_NTTV( 0x901E, (cc, addr) -> new VoltageField(addr, "Night TimeThreshold Volt.(NTTV)", " PV lower lower than this value, controller would detect it as sundown")),
    LIGHT_SIGNAL_STARTUP_NIGHT_DELAY_TIME( 0x901F, (cc, addr) -> new FloatField(addr, Minutes, "Light signal startup (night) delay time", "PV voltage lower than NTTV, and duration exceeds the Light signal startup (night) delay time, controller would detect it as night time.", 1, 1)),
    DAY_TIME_THRESHOLD_VOLT_DTTV( 0x9020, (cc, addr) -> new VoltageField(addr, "Day Time Threshold Volt.(DTTV)", "PV voltage higher than this value, controller would detect it as sunrise")),
    LIGHT_SIGNAL_TURN_OFF_DAY_DELAY_TIME( 0x9021, (cc, addr) -> new FloatField(addr, Minutes, "Light signal turn off(day) delay time", "PV voltage higher than DTTV, and duration exceeds Light signal turn off(day) delay time delay time, controller would detect it as daytime.", 1, 1)),
    LOAD_CONTROLING_MODES( 0x903D, (cc, addr) -> new CodesField(addr, 1, HexCodes.loadControl)),
    WORKING_TIME_LENGTH_1(0x903E, (cc,addr) -> new DurationField(addr,"Load Timer 1 - Duration", "Load terminal enabled for this duration (D15-D8: hour, D7-D0: minute)")),
    WORKING_TIME_LENGTH_2(0x903F, (cc,addr) -> new DurationField(addr,"Load Timer 2 - Duration", "Load terminal enabled for this duration (D15-D8: hour, D7-D0: minute)")),
    TURN_ON_TIMING_1(0x9042, (cc,addr) -> new TimeField(addr,"Load Time Range 1 - Start","Load terminal turned on (TODO-mode explanation here)")),
    TURN_OFF_TIMING_1(0x9045, (cc,addr) -> new TimeField(addr,"Load Time Range 1 - End","Load terminal turned off (TODO-mode explanation here)")),
    TURN_ON_TIMING_2(0x9048, (cc,addr) -> new TimeField(addr,"Load Time Range 2 - Start","Load terminal turned on (TODO-mode explanation here)")),
    TURN_OFF_TIMING_2(0x904B, (cc,addr) -> new TimeField(addr,"Load Time Range 2 - End","Load terminal turned off (TODO-mode explanation here)")),
    LENGTH_OF_NIGHT(0x9065, (cc,addr) -> new DurationField(addr,"Length of Night - Duration", "")),
    BATTERY_RATED_VOLTAGE_CODE( 0x9067, (cc, addr) -> new CodesField(addr, 1, HexCodes.batteryRatedVoltage)),
    LOAD_TIMING_CONTROL_SELECTION( 0x9069, (cc, addr) -> new CodesField(addr, 1, HexCodes.loadTimingControlMode)),
    DEFAULT_LOAD_ON_OFF_IN_MANUAL_MODE( 0x906A, (cc, addr) -> new CodesField(addr, 1, new HexCodes.OnOff("Manual Mode Load On/Off Default"))),
    EQUALIZE_DURATION(0x906B, (cc,addr) -> new FloatField(addr, Minutes, "Equalize duration", "Usually 60-120 minutes.", 1, 1)),
    BOOST_DURATION(0x906C, (cc,addr) -> new FloatField(addr, Minutes, "Boost duration", "Usually 60-120 minutes.", 1, 1)),
    DISCHARGING_PERCENTAGE(0x906D, (cc,addr) -> new PercentageField(addr, "Discharging percentage", "Usually 20%-80%. The percentage of battery's remaining capacity when stop charging")),
    CHARGING_PERCENTAGE(0x906E, (cc,addr) -> new PercentageField(addr, "Charging percentage", "Depth of charge, 20%-100%.")),
    MANAGEMENT_MODES_OF_BATTERY_CHARGING_AND_DISCHARGING(0x9070, (cc,addr) -> new CurrentField(addr, "Management modes of battery charging and discharging", "Management modes of battery charge and discharge, voltage compensation : 0 and SOC : 1.")),

    // Coils
    LOAD_CONTROL_MODE(0x0002, (cc,addr) -> new BooleanField(addr, "Manual Mode On")),
    LOAD_TEST_MODE(0x0005, (cc,addr) -> new BooleanField(addr, "Load Test Mode On")),
    LOAD_ENABLED(0x0006, (cc,addr) -> new BooleanField(addr, "Force Load On", "Temporarily enable load terminal")),

    // Discrete Inputs
    DEVICE_TEMPERATURE_THRESHOLD_EXCEEDED(0x2000, (cc,addr) -> new BooleanField(addr,"Internal temperature threshold exceeded")),
    IS_NIGHT(0x200C, (cc,addr) -> new BooleanField(addr, "Is Night", "True: Night, False: Day"));


    private BiFunction<ChargeController,Integer,ModbusField> fieldFactory;

    public final int registerAddress;

    EpeverFieldDefinitions(int registerAddress, BiFunction<ChargeController,Integer,ModbusField> fieldFactory ) {
        this.registerAddress = registerAddress;
        this.fieldFactory = fieldFactory;
    }

    public ModbusField create(ChargeController cc) {
        ModbusField field = fieldFactory.apply(cc,registerAddress);
        field.setChargeController(cc);
        return field;
    }

}