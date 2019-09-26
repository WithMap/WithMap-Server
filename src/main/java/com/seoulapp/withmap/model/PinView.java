package com.seoulapp.withmap.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PinView {
		private Pin pin;
		private Object DetailContents;
		private List<PinImage> pinImages;
		private boolean isMine;

		//TODO : DetailContents 담기
		public PinView(final Pin pin, final List<PinImage> pinImages, boolean isMine) {
			super();
			this.pin = pin;
			this.pinImages = pinImages;
			this.isMine = isMine;
		}

}
