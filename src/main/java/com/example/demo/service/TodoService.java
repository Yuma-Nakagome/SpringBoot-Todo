package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Todo;
import com.example.demo.repository.TodoRepository;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> findAllTodos() {
        List<Map<String, Object>> todos = todoRepository.findAll();

        if (todos == null) {
            // null の場合は、空のリストを返す
            return new ArrayList<>();
        }

        List<Todo> todoList = new ArrayList<>();

        for (Map<String, Object> mapTodo : todos) {
            Long id = ((Number) mapTodo.get("id")).longValue();
            String title = (String) mapTodo.get("title");
            String description = (String) mapTodo.get("description");
            Boolean status = (Boolean) mapTodo.get("status");

            Todo todo = new Todo();
            todo.setId(id);
            todo.setTitle(title);
            todo.setDescription(description);
            todo.setStatus(status);

            todoList.add(todo);
        }

        return todoList;
    }

    public void addTodo(String title, String description, boolean status) {
        todoRepository.addTodo(title, description, status);
    }

    public Todo getTodoItem(Long id) {
        Map<String, Object> mapTodo = todoRepository.getTodoItemById(id);

        Long mapTodo_id = ((Number) mapTodo.get("id")).longValue();
        String title = ((String) mapTodo.get("title"));
        String description = ((String) mapTodo.get("description"));
        Boolean status = ((Boolean) mapTodo.get("status"));

        Todo todo = new Todo();
        todo.setId(mapTodo_id);
        todo.setTitle(title);
        todo.setDescription(description);
        todo.setStatus(status);

        return todo;

    }

    public void editTodo(Long id, String title, String description, Boolean status) {
        todoRepository.editTodo(id, title, description, status);
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteTodo(id);
    }

}
