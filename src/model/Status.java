package model;

import java.util.HashMap;
import java.util.Map;

public class Status {
	private String currentCellStatus = "";
	private String northCellStatus = "";
	private String eastCellStatus = "";
	private String southCellStatus = "";
	private String westCellStatus = "";
	private String[] value = new String[4];
	private Map<String[], String[]> statusUndWeg = new HashMap<>();
	private String[] key = { "go north", "go east", "go south", "go west" };

//	public Status() {
//
//		statusUndWeg.put("go north", northCellStatus);
//		statusUndWeg.put("go east", eastCellStatus);
//		statusUndWeg.put("go south", southCellStatus);
//		statusUndWeg.put("go west", westCellStatus);
//	}

	public Status() {
		value[0] = northCellStatus;
		value[1] = eastCellStatus;
		value[2] = southCellStatus;
		value[3] = westCellStatus;
		statusUndWeg.put(key, value);

	}

	public void aktualisiereStatus(String northCellStatus, String eastCellStatus, String southCellStatus,
			String westCellStatus) {
		statusUndWeg.clear();
		value[0] = northCellStatus;
		setNorthCellStatus(northCellStatus);
		value[1] = eastCellStatus;
		setEastCellStatus(eastCellStatus);
		value[2] = southCellStatus;
		setSouthCellStatus(southCellStatus);
		value[3] = westCellStatus;
		setWestCellStatus(westCellStatus);

		statusUndWeg.put(key, value);
	}

	public String welcherStatus(String status) {
		int floorCounter = 0;
		String ausgabe = "";
		switch (status) {
		case "FINISH":
			ausgabe = "finish";
			break;
		case "FLOOR":

			break;
		case "FORM":

			break;

		default:
			ausgabe = "";
		}
		return ausgabe;
	}

	public String getCurrentCellStatus() {
		return currentCellStatus;
	}

	public void setCurrentCellStatus(String currentCellStatus) {
		this.currentCellStatus = currentCellStatus;
	}

	public String getNorthCellStatus() {
		return northCellStatus;
	}

	public void setNorthCellStatus(String northCellStatus) {
		this.northCellStatus = northCellStatus;
	}

	public String getEastCellStatus() {
		return eastCellStatus;
	}

	public void setEastCellStatus(String eastCellStatus) {
		this.eastCellStatus = eastCellStatus;
	}

	public String getSouthCellStatus() {
		return southCellStatus;
	}

	public void setSouthCellStatus(String southCellStatus) {
		this.southCellStatus = southCellStatus;
	}

	public String getWestCellStatus() {
		return westCellStatus;
	}

	public void setWestCellStatus(String westCellStatus) {
		this.westCellStatus = westCellStatus;
	}

	public String[] getValue() {
		return value;
	}

	public void setValue(String[] status) {
		this.value = status;
	}

	public Map<String[], String[]> getStatusUndWeg() {
		return statusUndWeg;
	}

	public void setStatusUndWeg(Map<String[], String[]> statusUndWeg) {
		this.statusUndWeg = statusUndWeg;
	}

	public String[] getKey() {
		return key;
	}

	public void setKey(String[] key) {
		this.key = key;
	}

}
