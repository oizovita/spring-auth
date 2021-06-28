package com.example.demo.repositories;

import com.example.demo.models.Todo;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TodoRepository extends CrudRepository<Todo, UUID> {
}
