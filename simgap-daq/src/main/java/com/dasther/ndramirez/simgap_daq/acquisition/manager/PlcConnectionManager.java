package com.dasther.ndramirez.simgap_daq.acquisition.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.dasther.ndramirez.simgap_daq.acquisition.service.PlcClientService;
import com.dasther.ndramirez.simgap_daq.acquisition.service.PlcClientServiceImplet;
import com.dasther.ndramirez.simgap_daq.api.model.entity.device.Device;

import jakarta.annotation.PreDestroy;

@Component
public class PlcConnectionManager  {

    private final Map <Long, PlcClientService> connections = new ConcurrentHashMap<>(); 

    public PlcClientService getConnection(Device device){

        return connections.computeIfAbsent(
            device.getIdDevice(), 
            id -> createClient(device)
        );
    }

    private PlcClientService createClient(Device device){
        return new PlcClientServiceImplet(
            device.getAddressIp(),
            device.getRack(),
            device.getSlot()
        );
    }

    @PreDestroy
    public void disconnectAll (){
        connections.values().forEach(PlcClientService :: disconnect);
        connections.clear();
    }  
}   
