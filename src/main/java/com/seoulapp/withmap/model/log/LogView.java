package com.seoulapp.withmap.model.log;

import java.util.List;

import com.seoulapp.withmap.model.Pin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LogView {
	private List<LikeLog> likeLogs;
	private List<ReportLog> reportLogs;
	private List<Pin> pins;
}
