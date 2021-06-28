package com.example.demo.controllers;

import com.example.demo.models.Todo;
import com.example.demo.repositories.TodoRepository;
import com.example.demo.payload.request.TodoCreateRequest;
import com.example.demo.payload.request.TodoUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@Validated
@RestController
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping()
    public Iterable<Todo> index() {
        return todoRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Todo>
    show(@PathVariable UUID id) {
        Todo todo;
        todo = todoRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok().body(todo);
    }

    @PostMapping
    public @ResponseBody
    Todo create(@Valid @RequestBody TodoCreateRequest todoCreateDTO) {
        return todoRepository.save(new Todo());
    }

    @PutMapping(path = "/{id}")
    public @ResponseBody
    ResponseEntity<Todo> update(@PathVariable UUID id, @RequestBody TodoUpdateRequest todoUpdateDTO) {
        Todo todo = todoRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        todo.setDescription(todoUpdateDTO.getDescription());
        todo.setDeadline(todoUpdateDTO.getDeadline());
        todo.setDetails(todoUpdateDTO.getDetails());
        todo.setStatus(todoUpdateDTO.getStatus());
        todoRepository.save(todo);

        return ResponseEntity.ok().body(todo);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity.HeadersBuilder<?>
    delete(@PathVariable UUID id) {

        if (!todoRepository.existsById(id)) {
            return ResponseEntity.notFound();
        }

        todoRepository.deleteById(id);

        return ResponseEntity.noContent();
    }
}
