package com.seoulapp.withmap.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PinView {
		private Pin pin;
		private List<PinImage> pinImages;
		private Object DetailContents;
		private boolean isMine = false;
		private boolean isRecommended = false;

		public PinView(final Pin pin, final List<PinImage> pinImages) {
			super();
			this.pin = pin;
			this.pinImages = pinImages;
		}

}
