package com.kirin.demo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class BlockMap extends HashMap<RC, String> {

	private int startRow, endRow, startCol, endCol;

	public BlockMap initRow(int s, int e) {
		startRow = s;
		endRow = e;
		return this;
	}

	public BlockMap initCol(int s, int e) {
		startCol = s;
		endCol = e;
		return this;
	}

	private void clacBoundle() {
		int sc = Integer.MAX_VALUE, ec = -1;
		int sr = Integer.MAX_VALUE, er = -1;
		for (RC rc : this.keySet()) {
			int col = rc.getCol();
			int row = rc.getRow();

			if (sc > col) {
				sc = col;
			} 
			if(ec < col) {
				ec = col;
			}
			if (sr > row) {
				sr = row;
			}
			if (er < row) {
				er = row;
			}
		}
		initRow(sr, er);
		initCol(sc, ec);
		System.out.println("Bound:"+this);
	}

	public void saveCSV() throws IOException {
		clacBoundle();
		File f = new File("output.csv");
		System.out.println("path:" + f.getAbsolutePath());
//		f.deleteOnExit();
//		f.createNewFile();

		StringBuilder sb = new StringBuilder();
		for (int r = startRow; r <= endRow; r++) {
			for (int c = startCol; c <= endCol; c++) {
				RC rc = new RC(r, c);
				String d = this.get(rc);
				if (d == null)
					continue;
				d = d.replace(",", "@");
//				sb.append(d + "[" + r + "|" + c + "]").append(",");
				sb.append(d ).append(",");
			}
			sb.append("\t\n");
		}

		System.out.println(this + " " + sb.toString());

		FileUtils.writeStringToFile(f, sb.toString(), StandardCharsets.UTF_8);

	}
	
	public RC getKeyByValue(String val) {
		return this.keySet().stream().filter(rc->{
			String v = get(rc).trim();
			return StringUtils.equals(v, val);
		}).findAny().get();
	}
	
	public List<RC> getFullCols(RC _rc){
		int c = _rc.getCol();
		return this.keySet().stream().filter(rc->{
			return rc.getCol() == c;
		}).sorted((a,b)->{
			return Integer.compare(a.getRow(), b.getRow()) ;
		}).collect(Collectors.toList());
	}
	
	public List<String> getFullColValues(RC _rc){
		return getFullCols(_rc).stream().map(k-> get(k) ).collect(Collectors.toList());
	}
	
	public void appendCol(List<String> l) {
		clacBoundle();
		int newCol = endCol+1;
		int i=0;
		for(int r=startRow;r<=endRow;r++) {
			RC rc = new RC(r,newCol);
			this.put(rc, l.get(i++));
		}
	}

	@Override
	public String toString() {
		return "BlockMap [startRow=" + startRow + ", endRow=" + endRow + ", startCol=" + startCol + ", endCol=" + endCol
				+ "]";
	}

}
