package it.carmelolagamba.homelo.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckInfo {

	private String home;
	private List<CheckDetails> info;

	public CheckInfo() {
		info = new ArrayList<CheckDetails>();
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public List<CheckDetails> getInfo() {
		return info;
	}

	public void setInfo(List<CheckDetails> info) {
		this.info = info;
	}

	public void addInfo(CheckDetails detail) {
		info.add(detail);
	}

	public void addInfo(CheckDetails... details) {
		info.addAll(Arrays.asList(details));
	}

}
