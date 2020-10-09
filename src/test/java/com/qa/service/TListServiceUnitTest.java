package com.qa.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.dto.TListDTO;
import com.qa.persistence.domain.TList;
import com.qa.persistence.repository.TListRepository;


@SpringBootTest
public class TListServiceUnitTest {

	@Autowired
	private TListService service;
	
	@MockBean
	private TListRepository repo;
	
	@MockBean
	private ModelMapper modelMapper;
	
	private List<TList> tlistList;
	private TList testTList;
	private TList testTListWithId;
	private TListDTO tlistDTO;
	
	final Long id = 1L;
	final String testName = "Shopping";
	
	
	@BeforeEach
	void init() {
		this.tlistList = new ArrayList<>();
		this.testTList = new TList(testName);
		this.tlistList.add(testTList);
		this.testTListWithId = new TList(testTList.getCategory());
		this.testTListWithId.setId(id);
		this.tlistDTO = modelMapper.map(testTListWithId, TListDTO.class);
	}
	
	@Test
	void createTest() {
		when(this.repo.save(this.testTList)).thenReturn(this.testTListWithId);
		
		when(this.modelMapper.map(this.testTListWithId, TListDTO.class))
			.thenReturn(this.tlistDTO);
		
		TListDTO expected = this.tlistDTO;
		TListDTO actual = this.service.create(this.testTList);
		assertThat(expected).isEqualTo(actual);
		
		verify(this.repo, times(1)).save(this.testTList);
		
	}
	
	@Test
	void readTest() {
		when(this.repo.findById(this.id))
			.thenReturn(Optional.of(this.testTListWithId));
		when(this.modelMapper.map(this.testTListWithId, TListDTO.class))
			.thenReturn(tlistDTO);
		assertThat(this.tlistDTO).isEqualTo(this.service.read(this.id));
		verify(this.repo, times(1)).findById(this.id);
	}
	
	@Test
	void readAllTest() {
		when(this.repo.findAll()).thenReturn(this.tlistList);
		when(this.modelMapper.map(this.testTListWithId, TListDTO.class))
			.thenReturn(tlistDTO);
		assertThat(this.service.read().isEmpty()).isFalse();
		verify(this.repo, times(1)).findAll();
	}
	
	@Test
	void updateTest() {
		TList tlist = new TList("Shopping");
		tlist.setId(this.id);
		TListDTO tlistDTO = new TListDTO(null, "Food Shopping", null);
		TList updatedTList = new TList(tlistDTO.getCategory());
		updatedTList.setId(this.id);
		
		TListDTO updatedTListDTO = new TListDTO(this.id, updatedTList.getCategory(), null);
		
		when(this.repo.findById(this.id))
			.thenReturn(Optional.of(tlist));
		
		when(this.repo.save(tlist)).thenReturn(updatedTList);
		when(this.modelMapper.map(updatedTList, TListDTO.class))
			.thenReturn(updatedTListDTO);
		
		assertThat(updatedTListDTO)
			.isEqualTo(this.service.update(tlistDTO, this.id));

		verify(this.repo, times(1)).findById(1L);
		verify(this.repo, times(1)).save(updatedTList);
		
	}
	
	@Test
	void testDelete() {
		when(this.repo.existsById(id)).thenReturn(true, false);
		assertThat(this.service.delete(id)).isTrue();
		verify(this.repo, times(1)).deleteById(id);
		verify(this.repo, times(2)).existsById(id);
	}
}
