package org.example.service.impl;

import org.example.dao.ScaleDao;
import org.example.pojo.device.Scale;
import org.example.service.device.ScaleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class ScaleServiceImpl extends BaseServiceImpl<Scale, Integer> implements ScaleService {

    @Resource(name = "scaleDaoImpl")
    public void setBaseDao(ScaleDao dao) {
        super.setBaseDao(dao);
    }

}
