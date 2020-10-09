package com.qa.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.dto.TListDTO;
import com.qa.dto.TaskDTO;
import com.qa.persistence.domain.TList;
import com.qa.persistence.domain.Task;
import com.qa.persistence.repository.TListRepository;

@SpringBootTest
public class TListServiceIntegrationTest {

	@Autowired
	private TListService service;
	
	@Autowired
	private TListRepository repo;
	
	@MockBean
	private ModelMapper modelMapper;
	
	private TListDTO mapTListToDTO(TList tlist) {
		return this.modelMapper.map(tlist, TListDTO.class);
	}
	
	private TaskDTO mapTaskToDTO(Task task) {
		return this.modelMapper.map(task, TaskDTO.class);
	}
	
	
	private TList testTList;
	private TList testTListWithId;
	private TListDTO testTListDTO;
	private List<TaskDTO> tasks;
	
	private Long id;
	private final String name = "Shopping";
	
	@BeforeEach
	void init() {
		this.repo.deleteAll();
		this.testTList = new TList(name);
		this.testTListWithId = this.repo.save(this.testTList);
		this.testTListDTO = this.mapTListToDTO(testTListWithId);
		this.id = this.testTListWithId.getId();
		
		
	}
	
	@Test
	void testCreate() {
		assertThat(this.testTListDTO)
			.isEqualTo(this.service.create(testTList));
	}
	
	@Test
	void testRead() {
		assertThat(this.testTListDTO)
			.isEqualTo(this.service.read(this.id));
	}
	
	@Test
	void testReadAll() {
		assertThat(Stream.of(this.testTListDTO)
				.collect(Collectors.toList()))
			.isEqualTo(this.service.read());
	}
	
//	@Test
//	void testUpdate() {
//		TList tlist = new TList("Uplands Road");
//		tlist.setId(this.id);
//		this.tasks = new ArrayList<>();
//		tasks.add(this.mapTaskToDTO(new Task("Eggs", "Important")));
//		TListDTO newTList = new TListDTO(null, "Shopping", tasks);
//		TListDTO updatedTList =
//				new TListDTO(this.id, newTList.getCategory(), null);
//		
//		assertThat(updatedTList)
//			.isEqualTo(this.service.update(newTList, this.id));
//	}
	
	@Test
	void testDelete() {
		assertThat(this.service.delete(this.id)).isTrue();
	}
}
