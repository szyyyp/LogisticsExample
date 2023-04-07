package org.example.service.impl;

import org.example.dao.CanMeterDao;
import org.example.dao.ScaleDao;
import org.example.pojo.device.CanMeter;
import org.example.pojo.device.Scale;
import org.example.service.device.CanMeterService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class CanMeterServiceImpl extends BaseServiceImpl<CanMeter,Integer> implements CanMeterService {
    @Resource(name = "canMeterDaoImpl")
    CanMeterDao canMeterDao;

    @Resource(name = "scaleDaoImpl")
    ScaleDao scaleDao;

    @Resource(name = "canMeterDaoImpl")
    public void setBaseDao(CanMeterDao dao) {
        super.setBaseDao(dao);
    }

    public boolean edit(CanMeter meter,Integer scaleId){
        try {
            CanMeter can = canMeterDao.find(meter.getId());     //找出原来的对象
            Scale scale = scaleDao.find(scaleId);
            if (can!=null ) {
                if (can.getScale() == null || !can.getScale().getId().equals(scaleId)) {
                    can.setScale(scale);
                }
                BeanUtils.copyProperties(meter, can, "scale");    //把页面传来的值，拷贝回原来的对象
            }else{
                can = meter;
                can.setScale(scale);
            }
            canMeterDao.persist(can);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
