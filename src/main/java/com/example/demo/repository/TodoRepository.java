package com.example.demo.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TodoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // データベースから全てのタスクを取得してくる
    public List<Map<String, Object>> findAll() {
        List<Map<String, Object>> todos;
        String query = "SELECT * FROM todos";
        try {
            todos = jdbcTemplate.queryForList(query);
        } catch (Exception e) {
            return null;
        }
        return todos;
    }

    public void addTodo(String title, String description, boolean status) {
        String query = "INSERT INTO todos (title, description, status) VALUES(?, ?, ?)";
        jdbcTemplate.update(query, title, description, status);
    }

    public Map<String, Object> getTodoItemById(Long id) {
        // キー (String): データベースの列名です。（例: "id", "title", "status"）
        // 値 (Object): その列に対応するデータの中身です。（例: 1 (Integer), "牛乳を買う" (String), false
        // (Boolean)）
        String query = "SELECT  *  FROM todos WHERE id =  ?";
        try {
            return jdbcTemplate.queryForMap(query, id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    public void editTodo(Long id, String title, String description, Boolean status) {
        String query = "UPDATE todos SET title = ?, description = ?, status = ? WHERE id = ?";
        jdbcTemplate.update(query, title, description, status, id);
    }

    public void deleteTodo(Long id) {
        String query = "DELETE FROM todos WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

}
