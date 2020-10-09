package com.qa.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.qa.dto.TListDTO;
import com.qa.persistence.domain.TList;
import com.qa.service.TListService;


@SpringBootTest
public class TListControllerUnitTest {

	@Autowired
	private TListController controller;
	
	@MockBean
	private TListService service;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private TListDTO mapToDTO(TList tlist) {
		return this.modelMapper.map(tlist, TListDTO.class);
	}
	
	private List<TList> tlistList;
	private TList testTList;
	private TList testTListWithId;
	private TListDTO tlistDTO;
	
	private final String name = "Shopping";
	private final Long id = 1L;
	
	@BeforeEach
	void init() {
		this.tlistList = new ArrayList<>();
		this.testTList = new TList(name);
		this.testTListWithId = new TList(testTList.getCategory());
		this.testTListWithId.setId(id);
		this.tlistList.add(testTListWithId);
		this.tlistDTO = this.mapToDTO(testTListWithId);
		
	}
	
	@Test
	void createTest() {
		when(this.service.create(testTList)).thenReturn(this.tlistDTO);
		
		TListDTO testCreated = this.tlistDTO;
		assertThat(new ResponseEntity<TListDTO>(testCreated, HttpStatus.CREATED))
			.isEqualTo(this.controller.create(testTList));
		
		verify(this.service, times(1)).create(this.testTList);
	}
	
	@Test
	void readTest() {
		when(this.service.read(this.id)).thenReturn(this.tlistDTO);
		TListDTO testReadOne = this.tlistDTO;
		assertThat(new ResponseEntity<TListDTO>(testReadOne, HttpStatus.OK))
			.isEqualTo(this.controller.read(this.id));
		
		verify(this.service, times(1)).read(this.id);
	}
	
	@Test
	void readAllTest() {
		when(this.service.read()).thenReturn(this.tlistList.stream()
				.map(this::mapToDTO).collect(Collectors.toList()));
		
		assertThat(this.controller.read().getBody().isEmpty()).isFalse();
		
		verify(this.service, times(1)).read();
	}
	
	@Test
	void updateTest() {
		TListDTO newTList = new TListDTO(null, "Food Shopping", null);
		TListDTO newTListWithId = new TListDTO(this.id, newTList.getCategory(), null);
		
		when(this.service.update(newTList, this.id)).thenReturn(newTListWithId);
		
		assertThat(new ResponseEntity<TListDTO>(newTListWithId, HttpStatus.ACCEPTED))
			.isEqualTo(this.controller.update(this.id, newTList));
		
		verify(this.service, times(1)).update(newTList, this.id);
		
	}
	
	@Test
	void deleteTest() {
		this.controller.delete(id);
		
		verify(this.service, times(1)).delete(id);
	}
}
