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
		startRows.add(this.stream().map(t -> t.getStart_row()).max((a, b) -> a.compareTo(b)).get());
		System.out.println("startRows:" + startRows);
	}

	public BlockMap get1Block() {
		int statIdx = startRows.get(0), endIdx = startRows.get(1);
		return getBlock(t -> {
			return t.getStart_row() > statIdx && t.getStart_row() < endIdx;
		});
	}

	public BlockMap get2Block() {
		int statIdx = startRows.get(1), endIdx = startRows.get(2);
		return getBlock(t -> {
			return t.getStart_row() > statIdx && t.getStart_row() <= endIdx;
		});
	}

	private BlockMap getBlock(Predicate<Tile> p) {
		BlockMap ans = new BlockMap();
		List<Tile> fts = this.stream().filter(p).collect(Collectors.toList());
		for (Tile t : fts) {
			int col = (t.getStart_column() );
			int row = t.getStart_row();
			
			RC rc = new RC(row, col);
			ans.put(rc, t.getData());
		}
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
