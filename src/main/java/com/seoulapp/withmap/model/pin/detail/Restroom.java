package com.seoulapp.withmap.model.pin.detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Restroom{

	private int id;
	
	private String useableTime;
	
	private String departmentNumber;
}
