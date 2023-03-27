package org.example.controller;

import org.example.pageModel.Json;
import org.example.pageModel.Page;
import org.example.pageModel.Pageable;
import org.example.pojo.device.Scale;
import org.example.service.device.CanMeterService;
import org.example.service.device.ScaleService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
    称台参数设置及相关管理
    @Author szy
    @Date  2022-6-28
 */
@RestController
@RequestMapping("/scale")
public class ScaleInfoController {
    @Resource(name="scaleServiceImpl")
    ScaleService scaleService;

    @Resource(name="canMeterServiceImpl")
    CanMeterService canMeterService;

    // 获取称台参数
    @RequestMapping("/getScaleInfo")
    public Page<Scale> getScaleInfo(Pageable page, Scale scale){

        return scaleService.findPage(page,scale);
    }

    @RequestMapping("/scaleUp")
    public Json scaleUp(Integer id){
        Json json = new Json();
        try {
/*            PLCCmd cmd = plcCmdService.getSendToPlcCmd(id + 100, PlcCmdSendValue.SCALE_JACKING.getCode());
            plcClient.sendToPlc(cmd);*/
            json.setSuccess(true);
            json.setMsg("电钢顶升指令发送成功!");
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(true);
            json.setMsg("电钢顶升指令发送失败");
        }
        return json;
    }

    @RequestMapping("/scaleDown")
    public Json scaleDown(Integer id){
        Json json = new Json();
        try {
/*            PLCCmd cmd = plcCmdService.getSendToPlcCmd(id + 100, PlcCmdSendValue.SCALE_DOWN.getCode());
            plcClient.sendToPlc(cmd);*/
            json.setSuccess(true);
            json.setMsg("电钢下降指令发送成功!");
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(true);
            json.setMsg("电钢下降指令发送失败");
        }
        return json;
    }

    @RequestMapping("/removeFaultBell")
    public Json removeFaultBell(Integer id){
        Json json = new Json();
        try {
/*            PLCCmd cmd = plcCmdService.getSendToPlcCmd(id + 100, PlcCmdSendValue.REMOVE_ERROR.getCode());
            plcClient.sendToPlc(cmd);*/
            json.setSuccess(true);
            json.setMsg("取消故障告警指令发送成功!");
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(true);
            json.setMsg("取消故障告警指令发送失败");
        }
        return json;
    }

    @RequestMapping("/edit")
    public Json edit(Scale scale){
        Json json = new Json();
        try {
            Scale sc = scaleService.find(scale.getId());
            BeanUtils.copyProperties(scale,sc,"canMeter");
            scaleService.update(sc);
            json.setSuccess(true);
            json.setMsg("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(true);
            json.setMsg("修改失败");
        }
        return json;
    }

    @RequestMapping("/add")
    public Json add(Scale scale){
        Json json = new Json();
        try {
            scaleService.save(scale);
            json.setSuccess(true);
            json.setMsg("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(true);
            json.setMsg("添加失败");
        }
        return json;
    }

    @RequestMapping("/del")
    public Json del(Integer id){
        Json json = new Json();
        try {
            scaleService.delete(id);
            json.setSuccess(true);
            json.setMsg("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(true);
            json.setMsg("删除失败");
        }
        return json;
    }





/*    public static void main(String [] args){
        int k =(int) Math.ceil(0/2d);
        int j = 0/2;
        System.out.println(k);
        System.out.println(j);
    }*/

}
