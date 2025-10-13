package com.example.demo.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoForm {
    @NotBlank(message = "タイトルは必須項目です")
    private String title;
    private String description;
    private Boolean status;
}
