package uci.cs297p.proj.model;

import lombok.Data;

@Data
public class MovieRecordForm {
    String name;
    int year;
    String introduction;
    String picturePath;
    String pictureContent;

    @Override
    public String toString() {
        return "MovieRecordForm{" +
                "name='" + name + '\'' +
                ", year=" + year +
                ", introduction='" + introduction + '\'' +
                ", picture_path='" + picturePath + '\'' +
                ", picture_content='" + pictureContent + '\'' +
                '}';
    }
}
