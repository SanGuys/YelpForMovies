package uci.cs297p.proj.model;

import uci.cs297p.proj.model.Movies;

public interface MoviesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table movies
     *
     * @mbggenerated Sat Oct 06 19:02:35 PDT 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table movies
     *
     * @mbggenerated Sat Oct 06 19:02:35 PDT 2018
     */
    int insert(Movies record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table movies
     *
     * @mbggenerated Sat Oct 06 19:02:35 PDT 2018
     */
    int insertSelective(Movies record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table movies
     *
     * @mbggenerated Sat Oct 06 19:02:35 PDT 2018
     */
    Movies selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table movies
     *
     * @mbggenerated Sat Oct 06 19:02:35 PDT 2018
     */
    int updateByPrimaryKeySelective(Movies record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table movies
     *
     * @mbggenerated Sat Oct 06 19:02:35 PDT 2018
     */
    int updateByPrimaryKeyWithBLOBs(Movies record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table movies
     *
     * @mbggenerated Sat Oct 06 19:02:35 PDT 2018
     */
    int updateByPrimaryKey(Movies record);
}