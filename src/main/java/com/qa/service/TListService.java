package com.qa.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.dto.TListDTO;
import com.qa.dto.TaskDTO;
import com.qa.exception.TListNotFoundException;
import com.qa.persistence.domain.TList;
import com.qa.persistence.domain.Task;
import com.qa.persistence.repository.TListRepository;
import com.qa.persistence.repository.TaskRepository;
import com.qa.utils.ToDoListBeanUtils;


@Service
public class TListService {
	
private TListRepository repo;
	
	private ModelMapper mapper;

	@Autowired
	public TListService(TListRepository repo, ModelMapper mapper) {
		this.repo = repo;
		this.mapper = mapper;
	}
	
	private TListDTO mapToDTO(TList tlist) {
        return this.mapper.map(tlist, TListDTO.class);
    }

    private TList mapFromDTO(TListDTO tlistDTO) {
        return this.mapper.map(tlistDTO, TList.class);
    }
    
    public TListDTO create(TList tlist) {
        TList created = this.repo.save(tlist);
        TListDTO mapped = this.mapToDTO(created);
        return mapped;
    }

    public List<TListDTO> read() {
        List<TList> found = this.repo.findAll();
        List<TListDTO> streamed = found.stream().map(this::mapToDTO).collect(Collectors.toList());
        return streamed;
    }

    public TListDTO read(Long id) {
        TList found = this.repo.findById(id).orElseThrow(TListNotFoundException::new);
        return this.mapToDTO(found);
    }

    public TListDTO update(TListDTO bandDTO, Long id) {
        TList toUpdate = this.repo.findById(id).orElseThrow(TListNotFoundException::new);
        ToDoListBeanUtils.mergeNotNull(bandDTO, toUpdate);
        return this.mapToDTO(this.repo.save(toUpdate));
    }

    public boolean delete(Long id) {
        if (!this.repo.existsById(id)) {
            throw new TListNotFoundException();
        }
        this.repo.deleteById(id);
        return !this.repo.existsById(id);
    }
}
