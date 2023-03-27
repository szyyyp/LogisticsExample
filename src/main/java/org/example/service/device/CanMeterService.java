package org.example.service.device;


import org.example.pojo.device.CanMeter;
import org.example.service.BaseService;

public interface CanMeterService extends BaseService<CanMeter,Integer> {
    public boolean edit(CanMeter meter,Integer scaleId);

}
