package com.kirin.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TileList extends ArrayList<Tile> {

	private List<Integer> startRows = new ArrayList<>();

	public void init() {
		startRows = this.stream().filter(t -> StringUtils.equals(t.getData(), "fabric")).map(t -> t.getStart_row())
				.collect(Collectors.toList());
		startRows.add(this.stream().map(t -> t.getStart_row()).max( (a,b)->  a.compareTo(b) ).get());
		System.out.println("startRows:" + startRows);
	}

	public BlockMap get1Block() {
		int statIdx = startRows.get(0) , endIdx = startRows.get(1);
		return getBlock(t -> {
			return t.getStart_row() > statIdx && t.getStart_row() < endIdx;
		});
	}
	
	public BlockMap get2Block() {
		int statIdx = startRows.get(1) , endIdx = startRows.get(2);
		return getBlock(t -> {
			return t.getStart_row() > statIdx && t.getStart_row() < endIdx;
		});
	}

	private BlockMap getBlock(Predicate<Tile> p) {
		BlockMap ans = new BlockMap();
		List<Tile> fts = this.stream().filter(p).collect(Collectors.toList());
		int sc = Integer.MAX_VALUE , ec = -1;
		int sr = Integer.MAX_VALUE , er = -1;
		for (Tile t : fts) {
			int col = (t.getEnd_column() / 2);
			int row = t.getStart_row();
			RC rc = new RC(row,col );
			
			if(sc > col) {
				sc = col;
			}
			if(ec < col) {
				ec = col;
			}
			if(sr > row) {
				sr = row;
			}
			if(er < row) {
				er = row;
			}
			
			ans.put(rc, t.getData());
		}
		ans.initRow(sr, er).initCol(sc, ec);
		return ans;
	}

	public boolean isGrided() {
		return this.stream().anyMatch(t -> {
			if (StringUtils.equals(t.getData(), "BOM Comments"))
				return true;
			if (StringUtils.equals(t.getData(), "fabric"))
				return true;
			return false;
		});

	}

}
