package com.nltechtrainings.question_service.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class QuestionResponseDto {
    private Integer id;
    private String response;
}
