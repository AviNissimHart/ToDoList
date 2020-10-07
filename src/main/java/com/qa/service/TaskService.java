package com.qa.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.dto.TaskDTO;
import com.qa.exception.TaskNotFoundException;
import com.qa.persistence.domain.Task;
import com.qa.persistence.repository.TaskRepository;
import com.qa.utils.ToDoListBeanUtils;


@Service
public class TaskService {

	private TaskRepository repo;
	
	private ModelMapper mapper;
	
	@Autowired
	public TaskService(TaskRepository repo, ModelMapper mapper) {
		this.repo = repo;
		this.mapper = mapper;
	}
	
	private TaskDTO mapToDTO(Task task) {
        return this.mapper.map(task, TaskDTO.class);
    }

    private Task mapFromDTO(TaskDTO taskDTO) {
        return this.mapper.map(taskDTO, Task.class);
    }
    
 // create
//  public TaskDTO create(TaskDTO taskDTO) {
//      Task toSave = this.mapFromDTO(taskDTO);
//      Task saved = this.repo.save(toSave);
//      return this.mapToDTO(saved);
//  }

  public TaskDTO create(Task task) {
      Task created = this.repo.save(task);
      TaskDTO mapped = this.mapToDTO(created);
      return mapped;
  }

  // readAll
  public List<TaskDTO> read() {
      List<Task> found = this.repo.findAll();
      List<TaskDTO> streamed = found.stream().map(this::mapToDTO).collect(Collectors.toList());
      return streamed;
  }

  // readById
  public TaskDTO read(Long id) {
      Task found = this.repo.findById(id).orElseThrow(TaskNotFoundException::new);
      return this.mapToDTO(found);
  }

  // update
  public TaskDTO update(TaskDTO taskDTO, Long id) {
      Task toUpdate = this.repo.findById(id).orElseThrow(TaskNotFoundException::new);
      ToDoListBeanUtils.mergeNotNull(taskDTO, toUpdate);
      return this.mapToDTO(this.repo.save(toUpdate));
  }

  // delete
  public boolean delete(Long id) {
      if (!this.repo.existsById(id)) {
          throw new TaskNotFoundException();
      }
      this.repo.deleteById(id);
      return !this.repo.existsById(id);
  }
}
