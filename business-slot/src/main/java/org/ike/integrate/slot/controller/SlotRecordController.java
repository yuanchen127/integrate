package org.ike.integrate.slot.controller;

import lombok.extern.slf4j.Slf4j;
import org.ike.integrate.slot.common.Page;
import org.ike.integrate.slot.common.SlotRecordPoint;
import org.ike.integrate.slot.repository.SlotRecordRepo;
import org.ike.integrate.slot.req.PageSearchReq;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/slot/record")
public class SlotRecordController<T> {

    @Resource
    private SlotRecordRepo slotRecordRepo;

    @GetMapping("/list")
    public List<SlotRecordPoint> listRecord() {
        return slotRecordRepo.listRecord();
    }

    /**
     * 分页查询接口
     */
    @PostMapping("page")
    public Page<SlotRecordPoint> page(@RequestBody PageSearchReq req) {
        req.checkParam();
        return slotRecordRepo.page(req);
    }

    /**
     * 查询class列表
     * @return
     */
    @GetMapping("class/list")
    public Set<String> listClass() {
        return slotRecordRepo.listClass();
    }

    @PostMapping("/{id}")
    public boolean retry(@PathVariable("id")Serializable id) {
        return slotRecordRepo.retry(id);
    }
}
