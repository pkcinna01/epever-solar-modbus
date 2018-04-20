package com.xmonit.solar.modbus.units;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

public class HexCodes extends Unit {

    class BitRangeContext {
        String name;
        int start = 0, end = 15;
        List<Pair<Integer,String>> flags = new LinkedList();
        public String valueToString(int val) {
            for ( Pair<Integer,String> flag : flags ) {
                if ( flag.getKey().equals(val) ) {
                    return flag.getValue();
                }
            }
            return "Undefined Code";
        }
    }

    List<BitRangeContext> bitRangeContextList = new LinkedList();

    public static HexCodes batteryStatus = new HexCodes("Battery Status")
            .bitRange(0,3, "VoltageField").add(0x00,"Normal").add(0x01,"Over Volt").add(0x02,"Under Volt").add(0x03,"Low Volt Disconnect").add(0x04,"Fault")
            .bitRange(4,7, "TemperatureField").add(0x00,"Normal").add(0x01,"Over Temp.(Higher than the warning settings)").add(0x02,"Low Temp.(Lower than the warning settings)")
            .bitRange(8,8,"Battery Internal Resistance").add(0x00,"OK").add(0x01,"Abnormal")
            .bitRange(15,15,"Identification for Rated VoltageField").add(0x00,"OK").add(0x01,"Invalid");

    public static HexCodes chargingMode = new HexCodes("Charging Mode")
            .add(0x00,"Connect/disconnect").add(0x01,"PWM").add(0x02,"MPPT");

    public static HexCodes loadControl = new HexCodes("Load Control Mode")
            .add( 0x00, "Manual Control").add(0x01, "Light ON/OFF").add(0x02, "Light ON+ Timer").add(0x03,"Time Control");

    public static HexCodes batteryType = new HexCodes("Battery Type")
            .add( 0x01, "Sealed").add(0x02, "GEL").add(0x03, "Flooded").add(0x00, "User defined");

    public static HexCodes chargingEquipmentStatus = new HexCodes( "Charging Equipment Status")
            .bitRange(14,15,"Input VoltageField").add( 0x00, "OK").add(0x01,"No PowerField").add(0x03,"Error")
            .bitRange(13,13,"Charging MOSFET Short").add(0x00,"No").add(0x01,"Yes")
            .bitRange(12,12,"Charging or Anti-reverse MOSFET Short").add(0x00,"No").add(0x01,"Yes")
            .bitRange(11,11,"Anti-reverse MOSFET Short").add(0x00,"No").add(0x01,"Yes")
            .bitRange(10,10,"Input CurrentField Threshold").add(0x00,"OK").add(0x01,"Exceeded")
            .bitRange(9,9,"Load CurrentField Threshold").add(0x00,"OK").add(0x01,"Exceeded")
            .bitRange(8,8,"Load Short").add(0x00,"No").add(0x01,"Yes")
            .bitRange(7,7,"Load MOSFET Short").add(0x00,"No").add(0x01,"Yes")
            .bitRange(4,4,"PV Input Short").add(0x00,"No").add(0x01,"Yes")
            .bitRange(2,3,"Charging Status").add(0x00,"Idle").add(0x01,"FloatField").add(0x02,"Boost").add(0x03,"Equalization")
            .bitRange(1,1,"Fault").add(0x00,"No").add(0x01,"Yes")
            .bitRange(0,0,"Status").add(0x00,"Running").add(0x01,"Standby");

    public static HexCodes batteryRatedVoltage = new HexCodes( "Battery Rated VoltageField Mode")
            .add(0x00,"Auto Recognize").add(0x01,"12V").add(0x02,"24V");

    public static HexCodes loadTimingControlMode = new HexCodes( "Load Timing Mode" )
            .add(0x00,"Using One Timer").add(0x01,"Using Two Timer");

    public static class OnOff extends HexCodes {
        public OnOff(String name) {
            super(name);
            add( 0x00, "OFF" ).add( 0x01, "ON");
        }
    }

    public HexCodes(String name) {
        super(name, name);
        //bitRangeContextList.add( new BitRangeContext() );
    }

    public HexCodes bitRange(int start, int end) {
        return bitRange(start,end,"");
    }

    public HexCodes bitRange(int start, int end, String desc) {
        BitRangeContext brc = new BitRangeContext();
        brc.name = desc;
        brc.start = start;
        brc.end = end;
        bitRangeContextList.add(brc);
        return this;
    }

    public HexCodes add(int value, String desc) {
        if ( bitRangeContextList.isEmpty() ) {
            bitRangeContextList.add( new BitRangeContext() );
        }
        bitRangeContextList.get(bitRangeContextList.size() -1).flags.add( Pair.of(value,desc) );
        return this;
    }

    public int findByName(String codeName ) throws Exception {
        if ( bitRangeContextList.size() > 1 ) {
            throw new Exception(this.name + " has multiple bit ranges defined.  The bit range name must also be specified (use findByBitRangeAndName)");
        }
        BitRangeContext brc = bitRangeContextList.get(0);
        for( Pair<Integer,String> pair : brc.flags) {
            if ( pair.getValue().equalsIgnoreCase(codeName) ) {
                return pair.getKey();
            }
        }
        throw new Exception("'"+codeName+"' not a valid choice.  " + getDescription());
    }

    public int findByBitRangeAndName(String bitRangeName, String codeName) throws Exception {
        throw new Exception("Not implemented... need to test setting multiple bit ranges within a register");
    }

    public String asString(Integer val ) {
        return asString(BigInteger.valueOf(val));
    }

    @Override
    public String asString(Object val) {

        BigInteger bigEndianInt = (BigInteger) val;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < bitRangeContextList.size(); i++ ) {
            if ( i != 0 ) {
                sb.append(", ");
            }
            BitRangeContext brc = bitRangeContextList.get(i);
            BigInteger mask = BigInteger.ONE.shiftLeft(brc.end).subtract(BigInteger.ONE);
            BigInteger codeValue = bigEndianInt.shiftRight(brc.start).and( mask );
            if( !StringUtils.isEmpty(brc.name)) {
                sb.append(brc.name).append(": " );
            }
            sb.append(String.format("0x%02X (%s)", codeValue.longValue(), brc.valueToString(codeValue.intValue())));
        }
        return sb.toString();
    }

    @Override
    public String getDescription() {
        StringBuilder sb = new StringBuilder();

        if (!StringUtils.isEmpty(name)) {
            sb.append(name);
            if ( bitRangeContextList.size() != 1 ) {
                sb.append(" -");
            }
            sb.append(" ");
        }

        for (int i = 0; i < bitRangeContextList.size(); i++ ) {
            BitRangeContext bitRange = bitRangeContextList.get(i);
            if ( !bitRange.flags.isEmpty() ) {
                if ( i != 0 ) {
                    sb.append(", ");
                }
                if ( !StringUtils.isEmpty(bitRange.name) ) {
                    sb.append(bitRange.name).append(" ");
                }
                sb.append("(D").append(bitRange.start);
                if ( bitRange.start != bitRange.end) {
                    sb.append("-").append(bitRange.end);
                }
                sb.append("): [");
                for( int j = 0; j < bitRange.flags.size(); j++ ) {
                    Pair flag = bitRange.flags.get(j);
                    if ( j > 0 ) {
                        sb.append(", ");
                    }
                    sb.append(String.format("0x%02X: %s", flag.getLeft(), flag.getRight()));

                }
                sb.append("]");
            }
        }
        return sb.toString();
    }


}
