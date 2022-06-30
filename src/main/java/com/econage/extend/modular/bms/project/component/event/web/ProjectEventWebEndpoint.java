package com.econage.extend.modular.bms.project.component.event.web;

import com.econage.core.web.extension.response.BasicDataGridRows;
import com.econage.extend.modular.bms.project.component.event.entity.BmsProjectEventEntity;
import com.econage.extend.modular.bms.project.component.event.service.BmsProjectEventService;
import com.econage.extend.modular.bms.project.component.event.trivial.wherelogic.ProjectEventWhereLogic;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bms/project/event")
public class ProjectEventWebEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectEventWebEndpoint.class);
    private BmsProjectEventService bmsProjectEventService;
    @Autowired
    protected void setProjectEventService(BmsProjectEventService bmsProjectEventService) {
        this.bmsProjectEventService = bmsProjectEventService;
    }
    private final String[] defaultOrder = {"desc"};
    private final String[] defaultSort = {"order_seq_"};
    /*
     * 添加-post
     * */
    @PostMapping("")
    protected BmsProjectEventEntity newEntity(
            @RequestBody BmsProjectEventEntity bmsProjectEventEntity
    ){
        bmsProjectEventService.insert(bmsProjectEventEntity);
        return bmsProjectEventService.selectById(bmsProjectEventEntity.getId());
    }
    /*
     * 几乎全部字段-put
     * */
    @PutMapping("/{eventId}")
    protected BmsProjectEventEntity updateEntity(
            @PathVariable("eventId") String eventId,
            @RequestBody BmsProjectEventEntity bmsProjectEventEntity
    ){
        bmsProjectEventEntity.setId(eventId);
        bmsProjectEventService.updateById(bmsProjectEventEntity);
        return bmsProjectEventService.selectById(eventId);
    }
    /*
     * 查询-get
     * */
    @GetMapping("/search/{projectId}")
    protected BasicDataGridRows search( @PathVariable("projectId") String projectId,ProjectEventWhereLogic whereLogic ){
        whereLogic.setProjectId(projectId);
        if(ArrayUtils.isEmpty(whereLogic.getSort())){
            whereLogic.setSort(defaultSort);
        }
        if(ArrayUtils.isEmpty(whereLogic.getOrder())){
            whereLogic.setOrder(defaultOrder);
        }
        if(whereLogic.getPage()==0) whereLogic.setPage(1);
        if(whereLogic.getRows()==0) whereLogic.setRows(1000);
        whereLogic.setStatusTarget(true);
        return BasicDataGridRows.create()
                .withRows(bmsProjectEventService.selectListByWhereLogic(whereLogic,whereLogic.parsePagination()))
                .withTotal(bmsProjectEventService.selectCountByWhereLogic(whereLogic));
    }
    @GetMapping("/{eventId}")
    protected BmsProjectEventEntity getSingleEntity(
            @PathVariable("eventId") String eventId
    ){
        BmsProjectEventEntity entity = bmsProjectEventService.selectById(eventId);
        return entity;
    }
    /*
     * 少量字段-patch
     * */
    @PatchMapping("/{eventId}/status")

    protected void patchEntityStatus(
            @PathVariable("eventId") String eventId,
            @RequestParam("action") String action
    ){
        bmsProjectEventService.changeProjectEventStatus(eventId , BooleanUtils.toBooleanObject(action));
    }
}
