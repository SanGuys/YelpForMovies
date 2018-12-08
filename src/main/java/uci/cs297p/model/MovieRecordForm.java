package uci.cs297p.model;

import lombok.Data;

@Data
public class MovieRecordForm {
    Integer id;
    String name;
    Integer year;
    String introduction;
    String picturePath;
    Float rating;
    Integer ratingNumber;

    @Override
    public String toString() {
        return "MovieRecordForm{" +
                "id=" + id  +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", introduction='" + introduction + '\'' +
                ", picture_path='" + picturePath + '\'' +
                ", rating='" + rating + '\'' +
                ", ratingNumber='" + ratingNumber + '\'' +
                '}';
    }
}
