package uci.cs297p.model;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MovieMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table movies
     *
     * @mbggenerated Thu Oct 11 00:01:53 PDT 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table movies
     *
     * @mbggenerated Thu Oct 11 00:01:53 PDT 2018
     */
    int insert(Movie record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table movies
     *
     * @mbggenerated Thu Oct 11 00:01:53 PDT 2018
     */
    int insertSelective(Movie record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table movies
     *
     * @mbggenerated Thu Oct 11 00:01:53 PDT 2018
     */
    Movie selectByPrimaryKey(Integer id);

    List<Movie> selectBySearch(@Param("keyWords") String keyWords);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table movies
     *
     * @mbggenerated Thu Oct 11 00:01:53 PDT 2018
     */
    int updateByPrimaryKeySelective(Movie record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table movies
     *
     * @mbggenerated Thu Oct 11 00:01:53 PDT 2018
     */
    int updateByPrimaryKeyWithBLOBs(Movie record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table movies
     *
     * @mbggenerated Thu Oct 11 00:01:53 PDT 2018
     */
    int updateByPrimaryKey(Movie record);

}