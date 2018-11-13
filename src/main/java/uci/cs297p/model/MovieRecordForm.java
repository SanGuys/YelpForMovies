package uci.cs297p.model;

import lombok.Data;

@Data
public class MovieRecordForm {
    int id;
    String name;
    int year;
    String introduction;
    String picturePath;
    String pictureContent;

    @Override
    public String toString() {
        return "MovieRecordForm{" +
                "id=" + id  +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", introduction='" + introduction + '\'' +
                ", picture_path='" + picturePath + '\'' +
                ", picture_content='" + pictureContent + '\'' +
                '}';
    }
}
