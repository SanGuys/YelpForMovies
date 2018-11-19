package uci.cs297p.model;

import lombok.Data;

@Data
public class MovieRecordForm {
    int id;
    String name;
    int year;
    String introduction;
    String picturePath;
    float rating;
    int ratingNumber;

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
