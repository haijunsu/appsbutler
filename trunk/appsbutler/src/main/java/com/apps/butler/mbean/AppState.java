package com.apps.butler.mbean;

import com.apps.butler.hibernate.SqlTypeEnum;

public enum AppState implements SqlTypeEnum<AppState> {

	UNKNOW(-1) {
		public String toString() {
			return "UNKNOW";
		}
	},

	Starting(1) {
		public String toString() {
			return "Starting";
		}
	},

	Running(2) {
		public String toString() {
			return "Running";
		}
	},

	Stopped(3) {
		public String toString() {
			return "Stopped";
		}
	};

	private int value;

	private AppState(int value) {
		this.value = value;
	}

	public Enum<AppState> fromValue(int value) {
		switch (value) {
		case 1:
			return Starting;
		case 2:
			return Running;
		case 3:
			return Stopped;
		default:
			return UNKNOW;
		}
	}

	public int value() {
		return value;
	}

}
