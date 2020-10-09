package com.qa.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.dto.TListDTO;
import com.qa.persistence.domain.TList;
import com.qa.persistence.repository.TListRepository;


@SpringBootTest
@AutoConfigureMockMvc
public class TListControllerIntegrationTest {
	@Autowired
	private MockMvc mock;
	
	@Autowired
	private TListRepository repo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Long id;
	private TList testTList;
	private TList testTListWithId;
	
	private TListDTO mapToDTO(TList tlist) {
		return this.modelMapper.map(tlist, TListDTO.class);
	}
	
	 @BeforeEach
	    void init() {
	        this.repo.deleteAll();
	        this.testTList = new TList("Shopping");
	        this.testTListWithId = this.repo.save(this.testTList);
	        this.id = this.testTListWithId.getId();
	    }
	 
	 @Test
	    void testCreate() throws Exception {
	        this.mock
	            .perform(request(HttpMethod.POST, "/tlist/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(this.objectMapper.writeValueAsString(testTList))
	                .accept(MediaType.APPLICATION_JSON))
	            .andExpect(status().isCreated())
	            .andExpect(content().json(this.objectMapper.writeValueAsString(testTListWithId)));
	    }
	 
	 @Test
	    void testRead() throws Exception {
	        this.mock
	            .perform(request(HttpMethod.GET, "/tlist/read/" + this.id)
	                .accept(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(content().json(this.objectMapper.writeValueAsString(this.testTList)));
	    }
	 
	 @Test
	    void testReadAll() throws Exception {
	        List<TList> tlistList = new ArrayList<>();
	        tlistList.add(this.testTListWithId);

	        String content = this.mock
	            .perform(request(HttpMethod.GET, "/tlist/read")
	                .accept(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andReturn().getResponse().getContentAsString();
	        
	        assertEquals(this.objectMapper.writeValueAsString(tlistList), content);
	    }
	 
	 @Test
	    void testUpdate() throws Exception {
	        TList newTList = new TList("Food Shopping");
	        TList updatedTList = new TList(newTList.getCategory());
	        updatedTList.setId(this.id);

	        String result = this.mock
	            .perform(request(HttpMethod.PUT, "/tlist/update/" + this.id)
	                .accept(MediaType.APPLICATION_JSON)
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(this.objectMapper.writeValueAsString(newTList)))
	            .andExpect(status().isAccepted())
	            .andReturn().getResponse().getContentAsString();
	        
	        assertEquals(this.objectMapper.writeValueAsString(this.mapToDTO(updatedTList)), result);
	    }

	 @Test
	    void testDelete() throws Exception {
	        this.mock
	            .perform(request(HttpMethod.DELETE, "/tlist/delete/" + this.id))
	            .andExpect(status().isNoContent());
	    }

}
