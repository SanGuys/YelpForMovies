package uci.cs297p.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uci.cs297p.model.Movie;
import uci.cs297p.model.MovieMapper;
import uci.cs297p.model.MovieRecordForm;

@Service
public class EditMovieRecordService {
    @Autowired
    private MovieMapper movieMapper;

    public void addMovie(MovieRecordForm movieRecordForm) {
        // public Movie(Integer id, String name, Integer year, String introduction, String picturePath, Date createTime, Date updateTime, byte[] pictureContent) {
        Movie movie = new Movie(null, movieRecordForm.getName(), movieRecordForm.getYear(), movieRecordForm.getIntroduction(),
                movieRecordForm.getPicturePath(), null, null, movieRecordForm.getPictureContent().getBytes());
        movieMapper.insertSelective(movie);
    }
}
