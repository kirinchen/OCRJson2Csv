package com.kirin.demo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;

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

	public void saveCSV() throws IOException {
		File f = new File("output.csv");
		System.out.println("path:" + f.getAbsolutePath());
//		f.deleteOnExit();
//		f.createNewFile();

		StringBuilder sb = new StringBuilder();
		for (int r = startRow; r <= endRow; r++) {
			for (int c = startCol; c <= endCol; c++) {
				RC rc = new RC(r, c);
				String d = this.get(rc);
				sb.append(d).append(",");
			}
			sb.append("\t\n");
		}

		System.out.println(this + " " + sb.toString());

		FileUtils.writeStringToFile(f, sb.toString(), StandardCharsets.UTF_8);

	}

	@Override
	public String toString() {
		return "BlockMap [startRow=" + startRow + ", endRow=" + endRow + ", startCol=" + startCol + ", endCol=" + endCol
				+ "]";
	}

}
