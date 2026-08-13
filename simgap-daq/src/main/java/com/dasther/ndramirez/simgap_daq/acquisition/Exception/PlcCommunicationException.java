package com.dasther.ndramirez.simgap_daq.acquisition.Exception;

public class PlcCommunicationException extends RuntimeException{
    
    public PlcCommunicationException(String messeger){
        super(messeger);
    }
}
