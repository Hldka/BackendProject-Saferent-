package com.saferent.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ImageSavedResponse extends SfResponse{

    private String imageId;

    public ImageSavedResponse(String imageId,String message,boolean success){
        super(message,success);
        this.imageId=imageId;
    }
}
