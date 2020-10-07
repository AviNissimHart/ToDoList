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

import com.qa.dto.TaskDTO;
import com.qa.persistence.domain.Task;
import com.qa.service.TaskService;


@SpringBootTest
public class TaskControllerUnitTest {
	
	@Autowired
	private TaskController controller;
	
	@MockBean
	private TaskService service;
	
	@Autowired
	private ModelMapper modelMapper;

	private TaskDTO mapToDTO(Task task) {
		return this.modelMapper.map(task, TaskDTO.class);
	}
	
	private List<Task> taskList;
    private Task testTask;
    private Task testTaskWithId;
    private TaskDTO taskDTO;

    private final String taskName = "Eggs";
    private final String type = "Important";
    private final Long id = 1L;


    @BeforeEach
    void init() {
    	this.taskList = new ArrayList<>();
    	this.testTask = new Task(taskName, type);
    	this.testTaskWithId = new Task(testTask.getTaskName(), testTask.getType());
    	this.testTaskWithId.setId(id);
    	this.taskList.add(testTaskWithId);
    	this.taskDTO = this.mapToDTO(testTaskWithId);
    }
    
    @Test
    void createTest() {
    	when(this.service.create(testTask)).thenReturn(this.taskDTO);
    	
    	TaskDTO testCreated = this.taskDTO;
    	assertThat(new ResponseEntity<TaskDTO>(testCreated, HttpStatus.CREATED))
    	.isEqualTo(this.controller.create(testTask));
    	
    	verify(this.service, times(1)).create(this.testTask);
    }
    
    @Test
    void readTest() {
    	when(this.service.read(this.id)).thenReturn(this.taskDTO);
    	TaskDTO testReadOne = this.taskDTO;
    	assertThat(new ResponseEntity<TaskDTO>(testReadOne, HttpStatus.OK))
    	.isEqualTo(this.controller.read(this.id));
    	
    	verify(this.service, times(1)).read(this.id);
    }
    
    @Test
    void readAllTest() {
        when(this.service.read()).thenReturn(this.taskList.stream()
        		.map(this::mapToDTO).collect(Collectors.toList()));

        assertThat(this.controller.read().getBody().isEmpty()).isFalse();

        verify(this.service, times(1)).read();
    }
    
    @Test
    void updateTest() {
       
        TaskDTO newTask = new TaskDTO(this.id, "Large Eggs", "Can wait");
        TaskDTO newTaskWithId = new TaskDTO(this.id, newTask.getTaskName(),
                newTask.getType());

        when(this.service.update(newTask, this.id)).thenReturn(newTaskWithId);

        assertThat(new ResponseEntity<TaskDTO>(newTaskWithId, HttpStatus.ACCEPTED))
                .isEqualTo(this.controller.update(this.id, newTask));

        verify(this.service, times(1)).update(newTask, this.id);
    }

    @Test
    void deleteTest() {
        this.controller.delete(id); // this will ping the service, which is mocked!

        verify(this.service, times(1)).delete(id);
    }

}
