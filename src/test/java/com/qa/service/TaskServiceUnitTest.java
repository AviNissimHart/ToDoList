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

import com.qa.dto.TaskDTO;
import com.qa.persistence.domain.Task;
import com.qa.persistence.repository.TaskRepository;

@SpringBootTest
public class TaskServiceUnitTest {

	// autowiring -> is using something you've already got lying around
    @Autowired
    private TaskService service;

    // mockbean -> get mockito to simulate responses from whatever class
    @MockBean
    private TaskRepository repo;

    // we've mocked the modelMapper here, which means we need to set its values
    // for every test we use it with using the when().theReturn() method-chain
    @MockBean
    private ModelMapper modelMapper;

    // we don't need a mapToDTO() function if we're spinning up a mock modelMapper
    // because mockito inverts the control of that object - as in, manages it
    // for us instead :) less code woo

    // items we're spinning up to <expect> from our unit tests
    private List<Task> taskList;
    private Task testTask;
    private Task testTaskWithId;
    private TaskDTO taskDTO;

    // final values to assign to those <expected> objects
    final String taskName = "Eggs";
    final String type = "Important";
    final Long id = 1L;

    @BeforeEach
    void init() {
        this.taskList = new ArrayList<>();
        this.testTask = new Task(taskName, type);
        this.taskList.add(testTask);
        this.testTaskWithId = new Task(testTask.getTaskName(),
                testTask.getType());
        this.testTaskWithId.setId(id);
        this.taskDTO = modelMapper.map(testTaskWithId, TaskDTO.class);
    }

    @Test
    void createTest() {
        // a when() to set up our mocked repo
        when(this.repo.save(this.testTask)).thenReturn(this.testTaskWithId);

        // and a when() to set up our mocked modelMapper
        when(this.modelMapper.map(this.testTaskWithId, TaskDTO.class)).thenReturn(this.taskDTO);

        // check that the taskDTO we set up as our <expected> value
        // is the same as the <actual> result we get when running the service.create()
        // method

        TaskDTO expected = this.taskDTO;
        TaskDTO actual = this.service.create(this.testTask);
        assertThat(expected).isEqualTo(actual);

        // we check that our mocked repository was hit - if it was, that means our
        // service works
        verify(this.repo, times(1)).save(this.testTask);
    }

    @Test
    void readTest() {
        // the repo we spin up extends a Spring type of repo that uses Optionals
        // thus, when we run a method in the repo (e.g. findById() ) we have to
        // return the object we want as an Optional (using Optional.of(our object) )
        // (just think of an Optional as a wrapper-class for any object)
        when(this.repo.findById(this.id)).thenReturn(Optional.of(this.testTaskWithId));

        when(this.modelMapper.map(testTaskWithId, TaskDTO.class)).thenReturn(taskDTO);

        assertThat(this.taskDTO).isEqualTo(this.service.read(this.id));

        verify(this.repo, times(1)).findById(this.id);
    }

    @Test
    void readAllTest() {
        // findAll() returns a list, which is handy, since we've got one spun up :D
        when(this.repo.findAll()).thenReturn(this.taskList);

        when(this.modelMapper.map(this.testTaskWithId, TaskDTO.class)).thenReturn(taskDTO);

        assertThat(this.service.read().isEmpty()).isFalse();

        verify(this.repo, times(1)).findAll();
    }

    @Test
    void updateTest() {
        Task task = new Task("Medium Eggs", "Can wait");
        task.setId(this.id);

        TaskDTO taskDTO = new TaskDTO(null, "Medium Eggs", "Can wait");

        Task updatedTask = new Task(taskDTO.getTaskName(),
                taskDTO.getType());
        updatedTask.setId(this.id);

        TaskDTO updatedTaskDTO = new TaskDTO(this.id, updatedTask.getTaskName(),
                 updatedTask.getType());

        // finById() grabs a specific task out of the repo
        when(this.repo.findById(this.id)).thenReturn(Optional.of(task));

        // we then save() an updated task back to the repo
        // we'd normally save() it once we've done stuff to it, but instead we're just
        // feeding in an <expected>
        // that we set up at the top of this method ^
        when(this.repo.save(task)).thenReturn(updatedTask);

        when(this.modelMapper.map(updatedTask, TaskDTO.class)).thenReturn(updatedTaskDTO);

        assertThat(updatedTaskDTO).isEqualTo(this.service.update(taskDTO, this.id));

        // since we've ran two when().thenReturn() methods, we need to run a verify() on
        // each:
        verify(this.repo, times(1)).findById(1L);
        verify(this.repo, times(1)).save(updatedTask);
    }

    @Test
    void deleteTest() {
        // we're running this.repo.existsById(id) twice, hence two returns (true &
        // false)
        // the <true> and <false> get plugged in once each to our two verify() methods
        when(this.repo.existsById(id)).thenReturn(true, false);

        assertThat(this.service.delete(id)).isTrue();

        // this plugs in the <true> from our when().thenReturn()
        verify(this.repo, times(1)).deleteById(id);

        // this plugs in the <false> from our when().thenReturn()
        verify(this.repo, times(2)).existsById(id);
    }
}
