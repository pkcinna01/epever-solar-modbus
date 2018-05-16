package com.xmonit.solar.epever.units;

import com.xmonit.solar.epever.EpeverException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class HexCodes extends Unit {

    JsonNodeFactory factory = JsonNodeFactory.instance;

    class BitRangeContext {
        class Flags extends LinkedList<Pair<Integer,String>>{}

        String name = "";
        int start = 0, end = 15;
        Flags flags = new Flags();

        public ObjectNode asJson(){
            ObjectNode n = factory.objectNode();
            n.put("name", factory.textNode(name));
            ArrayNode choicesNode = factory.arrayNode();
            for( Pair<Integer,String> flag : flags ){
                ObjectNode choiceNode = factory.objectNode();
                choiceNode.put("value",factory.numberNode(flag.getKey()));
                choiceNode.put("text",factory.textNode(flag.getValue()));
                choicesNode.add(choiceNode);
            }
            n.put("choices", choicesNode);
            return n;
        }

        public String valueToString(int val) {
            for ( Pair<Integer,String> flag : flags ) {
                if ( flag.getKey().equals(val) ) {
                    return flag.getValue();
                }
            }
            return "Undefined Code";
        }

        BigInteger getCodeValue(BigInteger bigEndianInt){
            if ( bigEndianInt == null ) {
                return null;
            }
            BigInteger mask = BigInteger.ONE.shiftLeft(end).subtract(BigInteger.ONE);
            return bigEndianInt.shiftRight(start).and( mask );
        }

        class Value {
            int value;
            Value(int value){
                this.value = value;
            }
            String getName() { return BitRangeContext.this.name; }
            String getTextValue() { return valueToString(this.value);}
            ObjectNode asJson() {
                ObjectNode n = factory.objectNode();
                n.put("name",factory.textNode(getName()));
                n.put("value",factory.numberNode(value));
                n.put("text",factory.textNode(getTextValue()));
                return n;
            }
        }

        Value getValue(BigInteger bigEndianInt){
            BigInteger v = getCodeValue(bigEndianInt);
            int intVal = v != null ? v.intValue() : -1;
            return new Value(intVal);
        }
    }

    public static HexCodes batteryStatus = new HexCodes("Battery Status")
            .bitRange(0,3, "Voltage").add(0x00,"Okay").add(0x01,"Over Volt").add(0x02,"Under Volt").add(0x03,"Low Volt Disconnect").add(0x04,"Fault")
            .bitRange(4,7, "Temperature").add(0x00,"Okay").add(0x01,"Over Temp.(Higher than the warning settings)").add(0x02,"Low Temp.(Lower than the warning settings)")
            .bitRange(8,8,"Battery Internal Resistance").add(0x00,"Okay").add(0x01,"Abnormal")
            .bitRange(15,15,"Identification for Rated Voltage").add(0x00,"Success").add(0x01,"Failed");

    public static HexCodes chargingMode = new HexCodes("Charging Mode")
            .add(0x00,"Connect/disconnect").add(0x01,"PWM").add(0x02,"MPPT");

    public static HexCodes loadControl = new HexCodes("Load Control Mode")
            .add( 0x00, "Manual Control").add(0x01, "Light ON/OFF").add(0x02, "Light ON+ Timer").add(0x03,"Time Control");

    public static HexCodes batteryType = new HexCodes("Battery Type")
            .add( 0x01, "Sealed").add(0x02, "GEL").add(0x03, "Flooded").add(0x00, "User defined");

    public static HexCodes chargingEquipmentStatus = new HexCodes( "Charging Equipment Status")
            .bitRange(14,15,"Input Voltage").add( 0x00, "Okay").add(0x01,"No Power").add(0x02,"High").add(0x03,"Error")
            .bitRange(13,13,"Charging MOSFET Short").add(0x00,"No").add(0x01,"Yes")
            .bitRange(12,12,"Charging or Anti-reverse MOSFET Short").add(0x00,"No").add(0x01,"Yes")
            .bitRange(11,11,"Anti-reverse MOSFET Short").add(0x00,"No").add(0x01,"Yes")
            .bitRange(10,10,"Input Current Threshold").add(0x00,"Okay").add(0x01,"Exceeded")
            .bitRange(9,9,"Load Current Threshold").add(0x00,"Okay").add(0x01,"Exceeded")
            .bitRange(8,8,"Load Short").add(0x00,"No").add(0x01,"Yes")
            .bitRange(7,7,"Load MOSFET Short").add(0x00,"No").add(0x01,"Yes")
            .bitRange(4,4,"PV Input Short").add(0x00,"No").add(0x01,"Yes")
            .bitRange(2,3,"Charging Status").add(0x00,"Idle").add(0x01,"Float").add(0x02,"Boost").add(0x03,"Equalization")
            .bitRange(1,1,"Fault").add(0x00,"No").add(0x01,"Yes") // why is this always Yes?
            .bitRange(0,0,"Status").add(0x00,"Running").add(0x01,"Standby");

    public static HexCodes batteryRatedVoltage = new HexCodes( "Battery Rated Voltage Mode")
            .add(0x00,"Auto Recognize").add(0x01,"12V").add(0x02,"24V");

    public static HexCodes loadTimingControlMode = new HexCodes( "Load Timing Mode" )
            .add(0x00,"Using One Timer").add(0x01,"Using Two Timer");


    public static class OnOff extends HexCodes {
        public OnOff(String name) {
            super(name);
            add( 0x00, "OFF" ).add( 0x01, "ON");
        }
    }

    List<BitRangeContext> bitRangeContextList = new LinkedList();


    public HexCodes(String name) {
        super(name, "choice");
    }


    public HexCodes add(int value, String desc) {
        if ( bitRangeContextList.isEmpty() ) {
            bitRangeContextList.add( new BitRangeContext() );
        }
        bitRangeContextList.get(bitRangeContextList.size() -1).flags.add( Pair.of(value,desc) );
        return this;
    }


    @Override
    public ObjectNode asJson(){
        ObjectNode n = super.asJson();
        ArrayNode bitRanges = factory.arrayNode();
        for(BitRangeContext brc: bitRangeContextList) {
            bitRanges.add(brc.asJson());
        }
        n.put("bitRanges", bitRanges);
        return n;
    }


    public ArrayNode asJson(BigInteger codes) {
        ArrayNode n = factory.arrayNode();
        for (BitRangeContext.Value v : valueToCodes((BigInteger) codes)) {
            n.add(v.asJson());
        }
        return n;
    }


    @Override
    public String asString(Object val) {
        StringBuilder sb = new StringBuilder();
        if ( val != null ) {
            for (BitRangeContext.Value v : valueToCodes((BigInteger) val)) {
                if (sb.length() != 0) {
                    sb.append(", ");
                }
                String name = v.getName();
                if (name != null && !name.isEmpty()) {
                    // null if single bit range context for entire bit set of value registers
                    sb.append(name).append(": ");
                }
                //sb.append(String.format("0x%02X (%s)", v.value, v.getTextValue()));
                sb.append(v.getTextValue());
            }
        }
        return sb.toString();
    }


    //public HexCodes bitRange(int start, int end) {
    //    return bitRange(start,end,"");
    //}


    public HexCodes bitRange(int start, int end, String desc) {
        BitRangeContext brc = new BitRangeContext();
        brc.name = desc;
        brc.start = start;
        brc.end = end;
        bitRangeContextList.add(brc);
        return this;
    }


    public Map<String,Integer> codesByName() {
        return codesByName("");
    }


    public Map<String,Integer> codesByName(String bitRangeName) {
        return bitRangeContextList.stream().filter(brc->bitRangeName.equals(brc.name)).findFirst().map(brc->brc.flags).get().stream().collect(Collectors.toMap(Pair::getValue,Pair::getKey));
    }


    public int findById(Object id) throws EpeverException {
        int code = Integer.parseInt(id.toString());
        return findByFilter( code, (idAndName) -> idAndName.getKey().intValue() == code );
    }

    public int findByName(final String codeName ) throws EpeverException {

        return findByFilter( codeName, (idAndName) -> idAndName.getValue().equalsIgnoreCase(codeName.trim()) );
    }

    public int findByFilter(Object key, Predicate<Pair<Integer,String>> filter ) throws EpeverException {
        if ( key == null ) {
            throw new EpeverException("Code lookup attempted with null key for '" + this.name + "'");
        } else if ( bitRangeContextList.size() > 1 ) {
            throw new EpeverException(this.name + " has multiple bit ranges defined.  The bit range name must also be specified (use findByBitRangeAndName)");
        }
        BitRangeContext brc = bitRangeContextList.get(0);
        for( Pair<Integer,String> pair : brc.flags) {
            if ( filter.test(pair) ) {
                return pair.getKey();
            }
        }
        throw new EpeverException("Invalid choice: " + key + ".  " + getDescription());
    }


    public int findByBitRangeAndName(String bitRangeName, String codeName) throws Exception {
        throw new Exception("Not implemented... need to test setting multiple bit ranges within a register");
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

    private List<BitRangeContext.Value> valueToCodes(BigInteger val) {
        List<BitRangeContext.Value> codeValues = new LinkedList<>();
        for(int i = 0; i < bitRangeContextList.size(); i++ ) {
            BitRangeContext brc = bitRangeContextList.get(i);
            codeValues.add( brc.getValue(val) );
        }
        return codeValues;
    }

}
