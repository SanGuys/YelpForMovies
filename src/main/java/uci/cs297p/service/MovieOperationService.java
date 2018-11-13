package uci.cs297p.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uci.cs297p.model.Movie;
import uci.cs297p.model.MovieMapper;
import uci.cs297p.model.MovieRecordForm;

import java.util.List;

@Service
public class MovieOperationService {
    @Autowired
    private MovieMapper movieMapper;

    public List<Movie> searchMovie(String keyWords){
        List<Movie> movieList = movieMapper.selectBySearch(keyWords);
        return movieList;
    }

    public void addMovie(MovieRecordForm movieRecordForm) {
        // public Movie(Integer id, String name, Integer year, String introduction, String picturePath, Date createTime, Date updateTime, byte[] pictureContent) {
        Movie movie = new Movie(null, movieRecordForm.getName(), movieRecordForm.getYear(), movieRecordForm.getIntroduction(),
                movieRecordForm.getPicturePath(), null, null, movieRecordForm.getPictureContent().getBytes());
        movieMapper.insertSelective(movie);
    }

    public void editMovie(MovieRecordForm movieRecordForm) {
        // public Movie(Integer id, String name, Integer year, String introduction, String picturePath, Date createTime, Date updateTime, byte[] pictureContent) {
        Movie movie = new Movie(movieRecordForm.getId(), movieRecordForm.getName(), movieRecordForm.getYear(), movieRecordForm.getIntroduction(),
                movieRecordForm.getPicturePath(), null, null, movieRecordForm.getPictureContent().getBytes());
        movieMapper.updateByPrimaryKeySelective(movie);
    }

    public void deleteMovie(Integer ID){
        movieMapper.deleteByPrimaryKey(ID);
    }

    public Movie getMovie(Integer ID) {
        Movie movie = movieMapper.selectByPrimaryKey(ID);
        return movie;
    }
}
