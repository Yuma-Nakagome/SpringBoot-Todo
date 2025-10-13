package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.example.demo.entity.Todo;
import com.example.demo.form.TodoForm;
import com.example.demo.service.TodoService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/")
    public String getAllTodos(Model model) {

        List<Todo> todos = todoService.findAllTodos();

        model.addAttribute("todos", todos);

        return "todos";
    }

    @GetMapping("create")
    public String createTodo(Model model) {
        TodoForm todoForm = new TodoForm();
        todoForm.setStatus(false);
        model.addAttribute("todoForm", todoForm);
        return "create-todo";
    }

    @PostMapping("addTodo")
    public String submitForm(@Valid TodoForm todoForm, BindingResult result) {
        // --- バリデーションとエラーハンドリング ---
        // @Valid: todoFormの内容がTodoFormクラスのバリデーションルールに従っているか自動チェック
        // BindingResult: バリデーションエラーがあれば、その詳細情報を保持するオブジェクトs
        if (result.hasErrors()) {
            return "create-todo"; // エラーがあったら、新規登録のフォームに戻る
        }
        // フォームの入力された値を引数にしてコントローラーのメソッドを呼び出す。
        todoService.addTodo(todoForm.getTitle(), todoForm.getDescription(), todoForm.getStatus());
        return "redirect:/";
    }

    @GetMapping("/todo/edit/{id}")
    public String showEditTodo(@PathVariable Long id, Model model) {
        Todo todoEdit = todoService.getTodoItem(id);
        model.addAttribute("todoForm", todoEdit);
        return "edit-todo";
    }

    @PostMapping("/todo/edit/{id}")
    public String editTodo(@PathVariable Long id, @Valid TodoForm todoForm, BindingResult result) {
        // @PathVariable は、URLパスの一部をメソッドの引数として取得するために使用されます。
        if (result.hasErrors()) {
            return "edit-todo"; // エラーがあったら、更新のフォームに戻る
        }
        // フォームの入力された値を引数にしてコントローラーのメソッドを呼び出す。
        todoService.editTodo(id, todoForm.getTitle(), todoForm.getDescription(), todoForm.getStatus());
        return "redirect:/";
    }

    @PostMapping("/todo/delete/{id}")
    public String deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return "redirect:/";
    }

}
