package com.saferent.domain;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="t_imagefile")
public class ImageFile {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2")
    private String id;
    private String name;
    private String type;
    private long length;
    @OneToOne(cascade = CascadeType.ALL)
    private ImageData imageData;

    public ImageFile(String name,String type,ImageData imageData){
        this.name=name;
        this.type=type;
        this.imageData=imageData;
        this.length=imageData.getData().length;
    }
}
