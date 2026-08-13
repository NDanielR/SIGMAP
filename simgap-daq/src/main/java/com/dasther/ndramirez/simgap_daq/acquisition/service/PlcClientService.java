package com.dasther.ndramirez.simgap_daq.acquisition.service;

public interface PlcClientService {
    void connect();

    byte[] readDbPlc(Integer dbNumber, Integer initDb, Integer size);

    Boolean isConnected();

    void disconnect();
}
