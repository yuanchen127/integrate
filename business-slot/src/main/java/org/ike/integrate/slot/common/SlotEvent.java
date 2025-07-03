package org.ike.integrate.slot.common;

import lombok.Getter;
import lombok.Setter;

/**
 * 埋点业务
 */
@Getter
@Setter
public class SlotEvent {

	private int id;

	private String name;

	private String description;

	public SlotEvent(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public SlotEvent(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public boolean equals(SlotEvent event) {
		if (null == event) {
			return false;
		}
		return id == event.getId();
	}
}
