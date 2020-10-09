package com.qa.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qa.dto.TaskDTO;
import com.qa.persistence.domain.Task;
import com.qa.persistence.repository.TaskRepository;

@SpringBootTest
public class TaskServiceIntegrationTest {

	// because we're testing the service layer, we can't use a MockMvc
    // because MockMvc only models a controller (in mockito format)

    @Autowired
    private TaskService service;

    @Autowired
    private TaskRepository repo;

    @Autowired
    private ModelMapper modelMapper;

    private TaskDTO mapToDTO(Task task) {
        return this.modelMapper.map(task, TaskDTO.class);
    }

    // there's no objectMapper this time
    // because we don't need to convert any returned objects to JSON
    // that's a controller job, and we're not testing the controller! :D

    private Task testTask;
    private Task testTaskWithId;
    private TaskDTO testTaskDTO;

    private final String taskName = "Eggs";
    private final String type = "Important";
    private Long id = 1L;

    @BeforeEach
    void init() {
        this.repo.deleteAll();
        this.testTask = new Task(taskName, type);
        this.testTaskWithId = this.repo.save(this.testTask);
        this.testTaskDTO = this.mapToDTO(testTaskWithId);
        this.id = this.testTaskWithId.getId();
    }

    @Test
    void testCreate() {
        assertThat(this.testTaskDTO)
            .isEqualTo(this.service.create(testTask));
    }

    @Test
    void testRead() {
        assertThat(this.testTaskDTO)
                .isEqualTo(this.service.read(this.id));
    }

    @Test
    void testReadAll() {
        // check this one out with a breakpoint and running it in debug mode
        // so you can see the stream happening
        assertThat(this.service.read())
                .isEqualTo(Stream.of(this.testTaskDTO)
                        .collect(Collectors.toList()));
    }

    @Test
    void testUpdate() {
        TaskDTO newTask = new TaskDTO(null, "Medium Eggs", "It can wait");
        TaskDTO updatedTask =
                new TaskDTO(this.id, newTask.getTaskName(), newTask.getType());

        assertThat(updatedTask)
            .isEqualTo(this.service.update(newTask, this.id));
    }

    @Test
    void testDelete() {
        assertThat(this.service.delete(this.id)).isTrue();
    }
}
