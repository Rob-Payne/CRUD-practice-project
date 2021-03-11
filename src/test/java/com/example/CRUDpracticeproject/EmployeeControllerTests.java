package com.example.CRUDpracticeproject;

import com.example.CRUDpracticeproject.dao.EmployeeRepository;
import com.example.CRUDpracticeproject.model.Employee;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTests {

    @Autowired
    MockMvc mvc;

    @Autowired
    EmployeeRepository employeeRepository;

    //LIST
    @Test
    @Transactional
    @Rollback
    public void getEmployeesListTest() throws Exception{
        //Insert new Employee into database
        Employee newEmployee = new Employee();
        newEmployee.setStartDate("2003-04-24");
        newEmployee.setName("Vanilla Ice");
        employeeRepository.save(newEmployee);

        //Create GET request
        MockHttpServletRequestBuilder getListOfEmployees = get("/employees")
                .contentType(MediaType.APPLICATION_JSON);

        //Send GET request
        //Check for 200 and that the returned value is acceptable
        this.mvc.perform(getListOfEmployees)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Vanilla Ice")));
    }

    //READ
    @Test
    @Transactional
    @Rollback
    public void getEmployeeByIdTest() throws Exception{
        Employee newEmployee = new Employee();
        newEmployee.setStartDate("2003-04-24");
        newEmployee.setName("Vanilla Ice");
        Long newEmployeeId = employeeRepository.save(newEmployee).getId();
        System.out.println(newEmployeeId);

        MockHttpServletRequestBuilder getEmployeeById = get("/employees/" + newEmployeeId)
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(getEmployeeById)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Vanilla Ice")));

    }

    @Test
    @Transactional
    @Rollback
    public void createNewEmployee() throws Exception{
        MockHttpServletRequestBuilder createEmployee = post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Jay\", \"startDate\":\"2012-01-23\"}");

        this.mvc.perform(createEmployee)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Jay")));
    }

    @Test
    @Transactional
    @Rollback
    public void deleteEmployee() throws Exception{
        Employee newEmployee = new Employee();
        newEmployee.setStartDate("2003-04-24");
        newEmployee.setName("Vanilla Ice");
        Long newEmployeeId = employeeRepository.save(newEmployee).getId();

        MockHttpServletRequestBuilder createWasSuccessful = get("/employees/"+newEmployeeId)
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(createWasSuccessful)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        MockHttpServletRequestBuilder deleteEmployee = delete("/employees/"+newEmployeeId)
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(deleteEmployee)
                .andExpect(status().isOk());

        MockHttpServletRequestBuilder checkDeleteWasSuccessful = get("/employees/"+newEmployeeId)
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(checkDeleteWasSuccessful)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @Transactional
    @Rollback
    public void patchEmployeeTest() throws Exception{
        //Create employee1
        Employee employee1 = new Employee();
        employee1.setName("John");
        employee1.setStartDate("2017-02-14");
        //Capture employee1's id while saving to the database
        Long employee1Id = employeeRepository.save(employee1).getId();

        //Create Update Request
        MockHttpServletRequestBuilder patchUser = patch("/employees/"+employee1Id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Not John\"}");

        //Perform Update Request and check the result has been updated
        this.mvc.perform(patchUser)
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is("Not John")));
    }

}
