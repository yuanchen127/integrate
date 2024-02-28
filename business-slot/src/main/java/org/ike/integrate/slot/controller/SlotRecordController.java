package org.ike.integrate.slot.controller;

import org.ike.integrate.slot.repository.SlotRecordRepo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping("slot/record")
public class SlotRecordController<T> {

    @Resource
    private SlotRecordRepo<T> slotRecordRepo;

    @GetMapping("/list")
    public List<T> listRecord() {
        return slotRecordRepo.listRecord();
    }

    @PostMapping("/{id}")
    public boolean rePushParam(@PathVariable("id")Serializable id) {
        T record = slotRecordRepo.getRecord(id);
        return false;
    }
}
