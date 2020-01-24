package com.kirin.demo;

import java.io.Serializable;

public class Tile implements Serializable {

	private String position;
	private int start_column;
	private int end_column;
	private int start_row;
	private int end_row;
	private String data;

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getStart_column() {
		return start_column;
	}

	public void setStart_column(int start_column) {
		this.start_column = start_column;
	}

	public int getEnd_column() {
		return end_column;
	}

	public void setEnd_column(int end_column) {
		this.end_column = end_column;
	}

	public int getStart_row() {
		return start_row;
	}

	public void setStart_row(int start_row) {
		this.start_row = start_row;
	}

	public int getEnd_row() {
		return end_row;
	}

	public void setEnd_row(int end_row) {
		this.end_row = end_row;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Tile [position=" + position + ", start_column=" + start_column + ", end_column=" + end_column
				+ ", start_row=" + start_row + ", end_row=" + end_row + ", data=" + data + "]";
	}
	
	

}
