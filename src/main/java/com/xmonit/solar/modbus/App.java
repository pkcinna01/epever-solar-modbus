package com.xmonit.solar.modbus;


import com.intelligt.modbus.jlibmodbus.data.mei.ReadDeviceIdentificationInterface;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.msg.base.mei.MEIReadDeviceIdentification;
import com.intelligt.modbus.jlibmodbus.msg.base.mei.ReadDeviceIdentificationCode;
import com.intelligt.modbus.jlibmodbus.serial.*;
//import jssc.SerialPortList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.CommandLinePropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import purejavacomm.CommPortIdentifier;

import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Optional;

@SpringBootApplication
@EnableAutoConfiguration
public class App {

    @Autowired
    AppConfig cfg;

    public static void main(String[] args) {

        SpringApplication.run(App.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {

        return args -> {

            try {
                CommandLinePropertySource propSrc = new SimpleCommandLinePropertySource(args);

                //Optional.ofNullable(propSrc.getProperty("cmd")).ifPresent(str -> cfg.cmd = str);
                // Optional.ofNullable(propSrc.getProperty("commPortRegEx")).ifPresent(str -> cfg.commPortRegEx = str);
                //Optional.ofNullable(propSrc.getProperty("remoteHostRegEx")).ifPresent(str -> cfg.remoteHostRegEx = str);

                //System.out.println(cfg);
            /*String[] dev_list = SerialPortList.getPortNames();
            for( String dev : dev_list ) {
                System.out.println(dev);
            }
            */
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        };
    }

}