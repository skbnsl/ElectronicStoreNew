package com.lcwd.electronic.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CategoryDto {
    private String categoryId;

    @NotBlank
    private String title;

    @NotBlank(message = "Decription Required")
    private String description;

    //@NotBlank(message = "")
    private String coverImage;
}
