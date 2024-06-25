package com.example.todo.todo;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {
    private static final List<Todo> todos = new ArrayList<>();
    private static int idCount = 0;
    static
    {
        todos.add(new Todo(idCount++,"admin","aws", LocalDate.now(), false));
        todos.add(new Todo(idCount++,"admin","web", LocalDate.now().plusYears(1), false));
        todos.add(new Todo(idCount++,"admin","spring", LocalDate.now().plusYears(2), false));
    }

    public List<Todo> findByUsername(String username) {
        return todos.stream().filter(todo -> todo.getUsername().equals(username)).toList();
    }

    public void addTodo(String username, String description, LocalDate deadline, boolean isDone) {
        todos.add(new Todo(idCount++, username, description,deadline, isDone));
    }

    public void deleteTodo(int id) {
        todos.removeIf(todo -> todo.getId() == id);
    }

    public Todo findById(int id) {
        return todos.stream().filter(todo -> todo.getId()==id).findAny().get();
    }

    public void updateTodo(Todo todo) {
        deleteTodo(todo.getId());
        todos.add(todo);
    }
}
