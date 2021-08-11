package com.simple.bz.service;

import com.simple.bz.dao.ContextQuery;
import com.simple.bz.dao.DeviceClusterRepository;
import com.simple.bz.dao.DeviceRepository;
import com.simple.bz.dto.*;
import com.simple.bz.model.DeviceClusterModel;
import com.simple.bz.model.DeviceModel;
import com.simple.common.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DeviceService {
    private final ModelMapper modelMapper;
    
    private final DeviceRepository dao;
    private final DeviceClusterRepository clusterDao;
    private final ContextQuery contextQuery;

    public DeviceModel convertToModel(DeviceDto dto){
        return this.modelMapper.map(dto, DeviceModel.class);
    }
    public List<DeviceModel> convertToModels(List<DeviceDto> dtos){
        List<DeviceModel> resultList = new ArrayList<DeviceModel>();
        for (int i=0; i < dtos.size(); i++){
            resultList.add(this.convertToModel(dtos.get(i)));
        }
        return resultList;
    }
    public DeviceDto convertToDto(DeviceModel model){
        return this.modelMapper.map(model, DeviceDto.class);
    }

    public List<DeviceDto> convertToDtos(List<DeviceModel> models){
        List<DeviceDto> resultList = new ArrayList<DeviceDto>();
        for (int i=0; i < models.size(); i++){
            resultList.add(this.convertToDto(models.get(i)));
        }
        return resultList;

    }
    public List<DeviceDto> findAll(){

        List<DeviceModel> list =   dao.findAll();
        return  this.convertToDtos(list);
    }


    public DeviceDto findById(Long id){
        DeviceModel model =  dao.findById(id).get();
        if(null != model){
            DeviceDto resultDto = this.convertToDto(model);
            DeviceClusterAttrDto clusterAttribute = this.querySupportClusterAttribute(id);
            resultDto.setClusterAttributes(clusterAttribute);
            return resultDto;
        }else {
            return this.convertToDto(model);
        }
    }


    public List<DeviceDto> queryAll(){
        List<DeviceDto> list = contextQuery.findList("select * from tbl_device", DeviceDto.class);
        return  list;
    }
    public List<DeviceDto> queryPage(int pageIndex, int pageSize){
        List<DeviceDto> listPage = contextQuery.findPage("select * from tbl_device", pageIndex,pageSize, DeviceDto.class);
        return  listPage;
    }
    public DeviceDto save(Long gatewayId,String ieee, String shortAddress){
        DeviceModel oldModel =  dao.findOneByGatewayIdAndIeee(gatewayId,ieee);
        if (null == oldModel){
            DeviceModel model = DeviceModel.builder().gatewayId(gatewayId).ieee(ieee).shortAddress(shortAddress).createTime(DateUtil.getDateToday()).build();
            DeviceModel newModel = dao.save(model);
            return this.convertToDto(newModel);
        }else{
            oldModel.setShortAddress(shortAddress);
            DeviceModel newModel = dao.save(oldModel);
            return this.convertToDto(newModel);
        }
    }

    public boolean bindToRoom(DeviceNewRequest request){
        DeviceModel model = dao.findById(request.getDeviceId()).orElse(null);
        if (null == model ){return false;}
        model.setRoomId(request.getRoomId());
        model.setNickName(request.getNickName());
        model.setDescription(request.getDescription());
        System.out.println("Successful to bind device Id ==>" + String.valueOf(request.getDeviceId()) + " To Room Id ==>" +  String.valueOf(request.getRoomId()));
        this.dao.save(model);
        return true;
    }

    public List<DeviceDto> queryByGatewayId(Long gatewayId){
        List<DeviceDto> list = contextQuery.findList("select d.*,m.description from tbl_device d left join zig_meta_device_id m on m.device_id=d.deviceId where gatewayId=" + String.valueOf(gatewayId), DeviceDto.class);
        Iterator iter = list.iterator();
        while (iter.hasNext()){
            DeviceDto deviceInfo = (DeviceDto)iter.next();
            DeviceClusterAttrDto clusterAttribute = this.querySupportClusterAttribute(deviceInfo.getId());
            deviceInfo.setClusterAttributes(clusterAttribute);
        }
        return  list;
    }
    public List<DeviceDto> queryByRoomId(Long roomId){
        List<DeviceDto> list = contextQuery.findList("select d.*,m.description as deviceDefine from tbl_device d left join zig_meta_device_id m on m.device_id=d.deviceId where roomId=" + String.valueOf(roomId), DeviceDto.class);
        Iterator iter = list.iterator();
        while (iter.hasNext()){
            DeviceDto deviceInfo = (DeviceDto)iter.next();
            DeviceClusterAttrDto clusterAttribute = this.querySupportClusterAttribute(deviceInfo.getId());
            deviceInfo.setClusterAttributes(clusterAttribute);
        }
        return  list;
    }
    public DeviceClusterAttrDto querySupportClusterAttribute(Long deviceId){
        List<DeviceClusterModel> clusters =  clusterDao.findByDeviceId(deviceId);
        Iterator iter = clusters.iterator();
        DeviceClusterAttrDto response = DeviceClusterAttrDto.builder().deviceId(deviceId).build();
        List<DeviceClusterDto>  clusterStatusList = new ArrayList<DeviceClusterDto>();
        List<DeviceClusterDto>  clusterCommandList = new ArrayList<DeviceClusterDto>();
        while (iter.hasNext()) {
            DeviceClusterModel cluster = (DeviceClusterModel)iter.next();
            String queryClusterAttr = "select * from tbl_meta_cluster_attribute where cluster='" + cluster.getCluster() + "'";
            List<ClusterAttributeDto> listAttribute = contextQuery.findList(queryClusterAttr, ClusterAttributeDto.class);
            System.out.println("cluster->" + cluster.getCluster() + "values===>" + listAttribute.toString());
            DeviceClusterDto clusterDto = DeviceClusterDto.builder().deviceId(deviceId).cluster(cluster.getCluster()).inOrOut(cluster.getInOrOut()).endpoint(cluster.getEndpoint()).build();
            clusterDto.setClusterAttributes(listAttribute);
            if(cluster.getInOrOut().equalsIgnoreCase("in")){
                clusterStatusList.add(clusterDto);
            }else{
                clusterCommandList.add(clusterDto);
            }
        } // end of while
        response.setCommandCluster(clusterCommandList);
        response.setStatusCluster(clusterStatusList);
        System.out.println(response.toString());
        return  response;
    }
    public DeviceDto update(DeviceDto item){
        Long id = item.getId();
        DeviceModel model = dao.findById(id).get();
        if (null == model ){return null;}
        this.modelMapper.map(item, model);
        System.out.println("Iot device model info ");
        System.out.println(model.toString());
        this.dao.save(model);
        return item;
    }

    public void remove(Long id){
        this.dao.deleteById(id);
    }

}