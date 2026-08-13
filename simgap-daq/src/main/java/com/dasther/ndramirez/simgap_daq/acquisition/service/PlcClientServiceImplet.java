package com.dasther.ndramirez.simgap_daq.acquisition.service;

import com.dasther.ndramirez.simgap_daq.acquisition.Exception.PlcCommunicationException;
import com.dasther.ndramirez.simgap_daq.acquisition.connection.S7;
import com.dasther.ndramirez.simgap_daq.acquisition.connection.S7Client;

public class PlcClientServiceImplet implements PlcClientService{

    private final String address;
    private final Integer rack;
    private final Integer slot;
    private final S7Client client;

    public PlcClientServiceImplet(String address, Integer rack, Integer slot){
        
        this.address = address;
        this.rack = rack;
        this.slot = slot;
        this.client = new S7Client();
    }

    @Override
    public void connect() {
        
        Integer result = client.ConnectTo(address, rack, slot);

        if (result != 0){
            throw new PlcCommunicationException(
                "No fue posible conectar con " + address
                + ": " + S7Client.ErrorText(result)
            );
        }

    }

    @Override
    public byte[] readDbPlc(Integer dbNumber, Integer initDb, Integer size) {
        
        byte[] buffer = new byte[size];

        Integer result = client.ReadArea(
            S7.S7AreaDB,
            dbNumber,
            initDb,
            size,
            buffer
        );

        if (result != 0) {
            throw new PlcCommunicationException("Error en lectura del DB" 
                            + dbNumber + ": " + S7Client.ErrorText(result)); 
        }

        return buffer;
    }

    @Override
    public Boolean isConnected() {
        return client.Connected;
    }

    @Override
    public void disconnect() {
        client.Disconnect();
    }

    
}