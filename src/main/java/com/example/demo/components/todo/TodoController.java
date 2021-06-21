package com.example.demo.components.todo;

import com.example.demo.components.todo.dto.TodoCreateDTO;
import com.example.demo.components.todo.dto.TodoUpdateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Min;

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
    show(@PathVariable @Min(1) int id) {
        Todo todo;
        todo = todoRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok().body(todo);
    }

    @PostMapping
    public @ResponseBody
    Todo create(@Valid @RequestBody TodoCreateDTO todoCreateDTO) {
        Todo todo = TodoMapper.INSTANCE.dtoCreateToEntity(todoCreateDTO);
        return todoRepository.save(todo);
    }

    @PutMapping(path = "/{id}")
    public @ResponseBody
    ResponseEntity<Todo> update(@PathVariable @Min(1) int id, @RequestBody TodoUpdateDTO todoUpdateDTO) {
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
    delete(@PathVariable @Min(1) int id) {

        if (!todoRepository.existsById(id)) {
            return ResponseEntity.notFound();
        }

        todoRepository.deleteById(id);

        return ResponseEntity.noContent();
    }
}
