package com.kirin.demo;

import java.io.File;
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

		File file = new File("C:\\Users\\DDT\\Desktop\\JayOCR\\data\\1.json");

		try {
			Map car = obm.readValue(file, Map.class);
			procces(car);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void procces(Map m) throws JsonProcessingException {

		Map cm = (Map) m.get("data");
		String checkJson = obm.writeValueAsString(cm.get("block_array"));
		System.out.println(checkJson);
		List<Object> tls = obm.readValue(checkJson, List.class);
		List<TileList> tileList = tls.stream()
				.map(conv).filter(l -> l.size() > 0)
				.filter(l -> l.isGrided())
				.collect(Collectors.toList());
		System.out.println(tileList.size()+":"+tileList);
		if(tileList.size()>1) throw new RuntimeException("Size > 1");
		TileList tl = tileList.get(0);
		

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