package org.ike.integrate.slot.controller;


import org.ike.integrate.slot.common.SlotContext;
import org.ike.integrate.slot.common.SlotEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/slot/event")
public class EventController {

	@Resource
	private SlotContext slotContext;

	@GetMapping("list")
	public List<SlotEvent> list() {
		return slotContext.listSlotEvent().stream().collect(Collectors.toList());
	}
}
