package com.seoulapp.withmap.model.log;

import org.joda.time.DateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeLog {

	private int pinId;

	private int userId;

	private DateTime crtDate;
}
