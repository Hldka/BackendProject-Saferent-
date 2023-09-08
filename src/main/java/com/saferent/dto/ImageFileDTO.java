package com.saferent.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageFileDTO {

    private String name ;
    private String url;

    private String type;

    private long size ;


}
