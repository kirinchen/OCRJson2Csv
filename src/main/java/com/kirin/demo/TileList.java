package com.kirin.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class TileList extends ArrayList<Tile> {

	
	private List<Integer> startRows = new ArrayList<>();
	
	public void init() {
		startRows = this.stream().filter(t-> StringUtils.equals(t.getData(), "fabric") ).map(t->t.getStart_row()).collect(Collectors.toList());
	}
	
	public boolean isGrided() {
		return this.stream().anyMatch(t->{
			if(StringUtils.equals(t.getData(), "BOM Comments")) return true;
			if(StringUtils.equals(t.getData(), "fabric")) return true;
			return false;
		});
		
	}
	
}
