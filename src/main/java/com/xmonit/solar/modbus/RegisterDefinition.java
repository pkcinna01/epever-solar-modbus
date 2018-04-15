package com.xmonit.solar.modbus;

import static com.xmonit.solar.modbus.RegisterUnit.*;


public enum RegisterDefinition {


    CE_RATED_INPUT_VOLTAGE  (0x3000, Volts,  "Charging equipment rated input voltage","PV array rated voltage"),
    CE_RATED_INPUT_CURRENT  (0x3001, Amps,   "Charging equipment rated input current","PV array rated current"),
    CE_RATED_INPUT_POWER    (0x3002, Watts,  "Charging equipment rated input power","PV array rated power", 100, 2),
    CE_RATED_OUTPUT_VOLTAGE (0x3004, Volts,  "Charging equipment rated output voltage","Battery voltage"),
    CE_RATED_OUTPUT_CURRENT (0x3005, Amps,   "Charging equipment rated output current","Rated charging current to battery"),
    CE_RATED_OUTPUT_POWER   (0x3006, Watts,  "Charging equipment rated output power","Rated charging power to battery",100, 2),
    CHARGING_MODE           (0x3008, Int,    "Charging mode","R0001H-PWM",1),
    LOAD_RATED_OUTPUT_CURRENT (0x300E, Amps,   "Rated output current of load",""),

    CE_INPUT_VOLTAGE        (0x3100, Volts,  "Charging equipment input voltage", "Solar charge controller--PV array voltage"),
    CE_INPUT_CURRENT        (0x3101, Amps,   "Charging equipment input current", "Solar charge controller--PV array current"),
    CE_INPUT_POWER          (0x3102, Watts,  "Charging equipment input power","Solar charge controller--PV array power", 100, 2),
    CE_OUTPUT_VOLTAGE       (0x3104, Volts,  "Charging equipment output voltage","Battery voltage"),
    CE_OUTPUT_CURRENT       (0x3105, Amps,   "Charging equipment output current","Battery charging current"),
    CE_OUTPUT_POWER         (0x3106, Watts,  "Charging equipment output power","Battery charging power",100, 2),

    LOAD_OUTPUT_VOLTAGE     (0x310C, Volts, "Discharging equipment output voltage", "Load voltage"),
    LOAD_OUTPUT_CURRENT     (0x310D, Amps, "Discharging equipment output current","Load current"),
    LOAD_OUTPUT_POWER       (0x310E, Watts, "Discharging equipment output power","Load power",100,2),
    BATTERY_TEMPERATURE     (0x3110, Celcius, "Battery Temperature", "Battery Temperature"),
    CE_TEMPERATURE          (0x3111, Celcius, "Temperature inside equipment", "Temperature inside case"),
    CE_SURFACE_TEMPERATURE  (0x3112, Celcius,"Power components temperature","Heat sink surface temperature of equipments' power components",100 ),
    BATTERY_SOC             (0x311A,Percent,"Battery SOC","The percentage of battery's remaining capacity",1 ),
    REMOTE_BATTERY_TEMPERATURE  (0x311B, Celcius, "Remote battery temperature", "The battery tempeture measured by remote temperature sensor", 100),
    BATTERY_REAL_RATED_POWER    (0x311D, Volts, "Battery's real rated power", "Current system rated votlage. 1200, 2400 represent 12V, 24V", 100 ),

    // Real-time status (read-only)
    BATTERY_STATUS              (0x3200, Amps, "Battery status", "D3-D0: 01H Overvolt , 00H Normal , 02H Under Volt, 03H Low Volt Disconnect, 04H Fault D7-D4: 00H Normal, 01H Over Temp.(Higher than the warning settings), 02H Low Temp.( Lower than the warning settings), D8: Battery inerternal resistance abnormal 1, normal 0 D15: 1-Wrong identification for rated voltage", 1 ),
    CHARGING_EQUIPMENT_STATUS   (0x3201, Amps, "Charging equipment status", "D15-D14: Input volt status. 00 normal, 01 no power connected, 02H Higher volt input, 03H Input volt error. D13: Charging MOSFET is short. D12: Charging or Anti-reverse MOSFET is short. D11: Anti-reverse MOSFET is short. D10: Input is over current. D9: The load is Over current. D8: The load is short. D7: Load MOSFET is short. D4: PV Input is short. D3-2: Charging status. 00 No charging,01 Float,02 Boost,03 Equlization. D1: 0 Normal, 1 Fault. D0: 1 Running, 0 Standby.", 1),

    // Statistical parameter (read only)
    MAXIMUM_INPUT_VOLT_PV_TODAY (0x3300, Volts, "Maximum input volt (PV) today", "00: 00 Refresh every day", 100 ),
    MINIMUM_INPUT_VOLT_PV_TODAY (0x3301, Volts, "Minimum input volt (PV) today", "00: 00 Refresh every day", 100 ),
    MAXIMUM_BATTERY_VOLT_TODAY  (0x3302, Volts, "Maximum battery volt today", "00: 00 Refresh every day", 100 ),
    MINIMUM_BATTERY_VOLT_TODAY  (0x3303, Volts, "Minimum battery volt today", "00: 00 Refresh every day", 100 ),
    CONSUMED_ENERGY_TODAY       (0x3304, KWH, "Consumed energy today", "00: 00 Clear every day", 100, 2 ),
    CONSUMED_ENERGY_THIS_MONTH  (0x3306, KWH, "Consumed energy this month", "00: 00 Clear on the first day of month", 100, 2 ),
    CONSUMED_ENERGY_THIS_YEAR   (0x3308, KWH, "Consumed energy this year", "00: 00 Clear on 1, Jan.", 100, 2 ),
    TOTAL_CONSUMED_ENERGY       (0x330A, KWH, "Total consumed energy", "Total consumed energy", 100, 2 ),
    GENERATED_ENERGY_TODAY      (0x330C, KWH, "Generated energy today", "00: 00 Clear every day.", 100, 2 ),
    GENERATED_ENERGY_THIS_MONTH (0x330E, KWH, "Generated energy this month", "00: 00 Clear on the first day of month.", 100, 2 ),
    GENERATED_ENERGY_THIS_YEAR  (0x3310, KWH, "Generated energy this year", "00: 00 Clear on 1, Jan.", 100, 2 ),
    TOTAL_GENERATED_ENERGY      (0x3312, KWH, "Total generated energy", "Total generated energy", 100, 2 ),
    CARBON_DIOXIDE_REDUCTION    (0x3314, Ton, "Carbon dioxide reduction", "Saving 1 Kilowatt=Reduction 0.997KG''Carbon dioxide ''=Reduction 0.272KG''Carton''", 100, 2 ),
    BATTERY_CURRENT             (0x331B, Amps, "Battery Current", "The net battery current,charging current minus the discharging one. The positive value represents charging and negative, discharging.", 100, 2 ),
    BATTERY_TEMP                (0x331D, Celcius, "Battery Temp.", "Battery Temp.", 100 ),
    AMBIENT_TEMP                (0x331E, Celcius, "Ambient Temp.", "Ambient Temp.", 100 ),

    //# Setting Parameter (read-write) holding register
    BATTERY_TYPE                (0x9000, Amps, "Battery Type", "0001H- Sealed , 0002H- GEL, 0003H- Flooded, 0000H- User defined", 1 ),
    BATTERY_CAPACITY            (0x9001, AmpHours, "Battery Capacity", "Rated capacity of the battery", 1 ),
    TEMP_COMPENSATION_COEFFICIENT(0x9002, Amps, "Temperature compensation coefficient", "Range 0-9 mV/C/2V", 100 ),
    HIGH_VOLT_DISCONNECT        (0x9003, Volts, "High Volt.disconnect", "High Volt.disconnect", 100 ),
    CHARGING_LIMIT_VOLTAGE      (0x9004, Volts, "Charging limit voltage", "Charging limit voltage", 100 ),
    OVER_VOLTAGE_RECONNECT      (0x9005, Volts, "Over voltage reconnect", "Over voltage reconnect", 100 ),
    EQUALIZATION_VOLTAGE        (0x9006, Volts, "Equalization voltage", "Equalization voltage", 100 ),
    BOOST_VOLTAGE               (0x9007, Volts, "Boost voltage", "Boost voltage", 100 ),
    FLOAT_VOLTAGE               (0x9008, Volts, "Float voltage", "Float voltage", 100 ),
    BOOST_RECONNECT_VOLTAGE     (0x9009, Volts, "Boost reconnect voltage", "Boost reconnect voltage", 100 ),
    LOW_VOLTAGE_RECONNECT       (0x900A, Volts, "Low voltage reconnect", "Low voltage reconnect", 100 ),
    UNDER_VOLTAGE_RECOVER       (0x900B, Volts, "Under voltage recover", "Under voltage recover", 100 ),
    UNDER_VOLTAGE_WARNING       (0x900C, Volts, "Under voltage warning", "Under voltage warning", 100 ),
    LOW_VOLTAGE_DISCONNECT      (0x900D, Volts, "Low voltage disconnect", "Low voltage disconnect", 100 ),
    DISCHARGING_LIMIT_VOLTAGE   (0x900E, Volts, "Discharging limit voltage", "Discharging limit voltage", 100 ),
    REAL_TIME_CLOCK             (0x9013, DateTime, "Real time clock 1", "D7-0 Sec, D15-8 Min.(Year,Month,Day,Min,Sec.should be writed simultaneously)", 1, 3 ),
    //REAL_TIME_CLOCK_2           (0x9014, Amps, "Real time clock 2", "D7-0 Hour, D15-8 Day", 1 ),
    //REAL_TIME_CLOCK_3           (0x9015, Amps, "Real time clock 3", "D7-0 Month, D15-8 Year", 1 ),
    EQUALIZATION_CHARGING_CYCLE(0x9016, Amps, "Equalization charging cycle", "Interval days of auto equalization charging in cycle Day", 1 ),
    BATTERY_TEMPERATURE_WARNING_UPPER_LIMIT(0x9017, Celcius, "Battery temperature warning upper limit", "Battery temperature warning upper limit", 100 ),
    BATTERY_TEMPERATURE_WARNING_LOWER_LIMIT(0x9018, Celcius, "Battery temperature warning lower limit", "Battery temperature warning lower limit", 100 ),
    CONTROLLER_INNER_TEMPERATURE_UPPER_LIMIT(0x9019, Celcius, "Controller inner temperature upper limit", "Controller inner temperature upper limit", 100 ),
    CONTROLLER_INNER_TEMPERATURE_UPPER_LIMIT_RECOVER(0x901A, Celcius, "Controller inner temperature upper limit recover", "After Over Temperature, system recover once it drop to lower than this value", 100 ),
    POWER_COMPONENT_TEMPERATURE_UPPER_LIMIT(0x901B, Celcius, "Power component temperature upper limit", "Warning when surface temperature of power components higher than this value, and charging and discharging stop", 100 ),
    POWER_COMPONENT_TEMPERATURE_UPPER_LIMIT_RECOVER(0x901C, Celcius, "Power component temperature upper limit recover", "Recover once power components temperature lower than this value", 100 ),
    LINE_IMPEDANCE(0x901D, mOHM, "Line Impedance", "The resistance of the connectted wires.", 100 ),
    NIGHT_TIMETHRESHOLD_VOLT_NTTV(0x901E, Volts, "Night TimeThreshold Volt.(NTTV)", " PV lower lower than this value, controller would detect it as sundown", 100 ),
    LIGHT_SIGNAL_STARTUP_NIGHT_DELAY_TIME(0x901F, Minutes, "Light signal startup (night) delay time", "PV voltage lower than NTTV, and duration exceeds the Light signal startup (night) delay time, controller would detect it as night time.", 1 ),
    DAY_TIME_THRESHOLD_VOLT_DTTV(0x9020, Volts, "Day Time Threshold Volt.(DTTV)", "PV voltage higher than this value, controller would detect it as sunrise", 100 ),
    LIGHT_SIGNAL_TURN_OFF_DAY_DELAY_TIME(0x9021, Minutes, "Light signal turn off(day) delay time", "PV voltage higher than DTTV, and duration exceeds Light signal turn off(day) delay time delay time, controller would detect it as daytime.", 1 ),
    LOAD_CONTROLING_MODES(0x903D, Amps, "Load controling modes", "0000H Manual Control, 0001H Light ON/OFF, 0002H Light ON+ Timer/, 0003H Time Control", 1 ),
    WORKING_TIME_LENGTH_1(0x903E, Amps, "Working time length 1", "The length of load output timer1, D15-D8,hour, D7-D0, minute", 1 ),
    WORKING_TIME_LENGTH_2(0x903F, Amps, "Working time length 2", "The length of load output timer2, D15-D8, hour, D7-D0, minute", 1 ),
    TURN_ON_TIMING_1_SEC(0x9042, Seconds, "Turn on timing 1 sec", "Turn on timing 1 sec", 1),
    TURN_ON_TIMING_1_MIN(0x9043, Minutes, "Turn on timing 1 min", "Turn on timing 1 min", 1),
    TURN_ON_TIMING_1_HOUR(0x9044, Hours, "Turn on timing 1 hour", "Turn on timing 1 hour", 1),
    TURN_OFF_TIMING_1_SEC(0x9045, Seconds, "Turn off timing 1 sec", "Turn off timing 1 sec", 1),
    TURN_OFF_TIMING_1_MIN(0x9046, Minutes, "Turn off timing 1 min", "Turn off timing 1 min", 1 ),
    TURN_OFF_TIMING__HOUR(0x9047, Hours, "Turn off timing 1 hour", "Turn off timing 1 hour", 1 ),
    TURN_ON_TIMING_2_SEC(0x9048, Seconds, "Turn on timing 2 sec", "Turn on timing 2 sec", 1 ),
    TURN_ON_TIMING_2_MIN(0x9049, Minutes, "Turn on timing 2 min", "Turn on timing 2 min", 1 ),
    TURN_ON_TIMING_2_HOUR(0x904A, Hours, "Turn on timing 2 hour", "Turn on timing 2 hour", 1 ),
    TURN_OFF_TIMING_2_SEC(0x904B, Seconds, "Turn off timing 2 sec", "Turn off timing 2 sec", 1 ),
    TURN_OFF_TIMING_2_MIN(0x904C, Minutes, "Turn off timing 2 min", "Turn off timing 2 min", 1 ),
    TURN_OFF_TIMING_2_HOUR(0x904D, Hours, "Turn off timing 2 hour", "Turn off timing 2 hour", 1),
    LENGTH_OF_NIGHT(0x9065, Amps, "Length of night", "Set default values of the whole night length of time. D15-D8,hour, D7-D0, minute", 1 ),
    BATTERY_RATED_VOLTAGE_CODE(0x9067, Amps, "Battery rated voltage code", "0, auto recognize. 1-12V, 2-24V", 1 ),
    LOAD_TIMING_CONTROL_SELECTION(0x9069, Amps, "Load timing control selection", "Selected timeing period of the load.0, using one timer, 1-using two timer, likewise.", 1 ),
    DEFAULT_LOAD_ON_OFF_IN_MANUAL_MODE(0x906A, Amps, "Default Load On/Off in manual mode", "0-off, 1-on", 1 ),
    EQUALIZE_DURATION(0x906B, Minutes, "Equalize duration", "Usually 60-120 minutes.", 1 ),
    BOOST_DURATION(0x906C, Minutes, "Boost duration", "Usually 60-120 minutes.", 1 ),
    DISCHARGING_PERCENTAGE(0x906D, Percent, "Discharging percentage", "Usually 20%-80%. The percentage of battery's remaining capacity when stop charging", 1 ),
    CHARGING_PERCENTAGE(0x906E, Percent, "Charging percentage", "Depth of charge, 20%-100%.", 1 ),
    MANAGEMENT_MODES_OF_BATTERY_CHARGING_AND_DISCHARGING(0x9070, Amps, "Management modes of battery charging and discharging", "Management modes of battery charge and discharge, voltage compensation : 0 and SOC : 1.", 1 ),

    // Read Coils (0x01) and Write Single Coil(0x05)
    LOAD_CONTROL_MODE   (0x0002, Bool, "Manual control the load", "When the load is manual mode, 1-manual on, 0 -manual off"),
    LOAD_TEST_MODE      (0x0005, Bool, "Enable load test mode",  "1 Enable, 0 Disable(normal)"),
    LOAD_ENABLED        (0x0006, Bool, "Force the load on/off",  "1 Turn on, 0 Turn off (used for temporary test of the load)"),

    // Read Discrete Inputs (0x02)
    DEVICE_TEMPERATURE_THRESHOLD_EXCEEDED(0x2000,Bool,"Over temperature inside the device",
                 "1 The temperature inside the controller is higher than the over-temperature protection point. 0 Normal"),
    IS_NIGHT            (0x200C, Bool, "Day/Night", "1-Night, 0-Day");


    public class Parameters {
        RegisterUnit unit;
        int times;
        int size;
        String name;
        String description;
        public Parameters(RegisterUnit unit, int times, int size, String name, String description) {
            this.unit = unit;
            this.times = times;
            this.size = size;
            this.name = name;
            this.description = description;
        }
    }

    private int value;
    public Parameters params;

    public Register create() {
        if ( params.unit == Volts ) {
            return new VoltageRegister(value,params.name,params.description,params.size,params.times);
        }
        return null;
    }

    private RegisterDefinition(int value, RegisterUnit unit, String name, String description) {
        this.value = value;
        this.params = new Parameters(unit,100,1,name,description);
    }
    private RegisterDefinition(int value, RegisterUnit unit, String name, String description, int times, int size) {
        this.value = value;
        this.params = new Parameters(unit,times,size,name,description);
    }
    private RegisterDefinition(int value, RegisterUnit unit, String name, String description, int times) {
        this.value = value;
        this.params = new Parameters(unit,times,1,name,description);
    }
}