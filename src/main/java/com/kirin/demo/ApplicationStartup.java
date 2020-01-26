package com.kirin.demo;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

	@Inject
	private ObjectMapper obm;

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {

		File file = new File("input.json");

		System.out.println("Read File:"+file.getAbsolutePath());
		
		try {
			Map car = obm.readValue(file, Map.class);
			procces(car);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void procces(Map m) throws IOException {

		Map cm = (Map) m.get("data");
		String checkJson = obm.writeValueAsString(cm.get("block_array"));
		System.out.println(checkJson);
		List<Object> tls = obm.readValue(checkJson, List.class);
		List<TileList> tileList = tls.stream().map(conv).filter(l -> l.size() > 0).filter(l -> l.isGrided())
				.collect(Collectors.toList());
		System.out.println(tileList.size() + ":" + tileList);
		if (tileList.size() > 1)
			throw new RuntimeException("Size > 1");
		TileList tl = tileList.get(0);
		tl.init();
		BlockMap bm1 = tl.get1Block();
		BlockMap bm2 = tl.get2Block();
		RC apRc = bm2.getKeyByValue("030925-DKLV");
		List<String> apList = bm2.getFullColValues(apRc);
		bm1.appendCol(apList);

		apRc = bm2.getKeyByValue("0560-EMRL");
		apList = bm2.getFullColValues(apRc);
		bm1.appendCol(apList);

//		System.out.println("bm2:"+obm.writeValueAsString(bm2)); 

		bm1.saveCSV();

	}

	private Function<Object, TileList> conv = o -> {
		Map m = (Map) o;
		try {
			String nj = obm.writeValueAsString(m.get("form_blocks"));
			return obm.readValue(nj, TileList.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

	};

}