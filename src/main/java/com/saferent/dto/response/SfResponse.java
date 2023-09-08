package com.saferent.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SfResponse {

    private String message;

    private boolean success;
}
