package com.project.backend.dao;

import com.project.backend.entity.Form;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormDao extends CrudRepository<Form, Integer> {
}
