package com.jrsportsgame.jrbm.mapper;

import com.jrsportsgame.jrbm.model.Userplayers;
import com.jrsportsgame.jrbm.model.UserplayersExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserplayersMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userplayers
     *
     * @mbg.generated Mon Jun 24 13:08:53 GMT+08:00 2019
     */
    long countByExample(UserplayersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userplayers
     *
     * @mbg.generated Mon Jun 24 13:08:53 GMT+08:00 2019
     */
    int deleteByExample(UserplayersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userplayers
     *
     * @mbg.generated Mon Jun 24 13:08:53 GMT+08:00 2019
     */
    int deleteByPrimaryKey(Integer upid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userplayers
     *
     * @mbg.generated Mon Jun 24 13:08:53 GMT+08:00 2019
     */
    int insert(Userplayers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userplayers
     *
     * @mbg.generated Mon Jun 24 13:08:53 GMT+08:00 2019
     */
    int insertSelective(Userplayers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userplayers
     *
     * @mbg.generated Mon Jun 24 13:08:53 GMT+08:00 2019
     */
    List<Userplayers> selectByExample(UserplayersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userplayers
     *
     * @mbg.generated Mon Jun 24 13:08:53 GMT+08:00 2019
     */
    Userplayers selectByPrimaryKey(Integer upid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userplayers
     *
     * @mbg.generated Mon Jun 24 13:08:53 GMT+08:00 2019
     */
    int updateByExampleSelective(@Param("record") Userplayers record, @Param("example") UserplayersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userplayers
     *
     * @mbg.generated Mon Jun 24 13:08:53 GMT+08:00 2019
     */
    int updateByExample(@Param("record") Userplayers record, @Param("example") UserplayersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userplayers
     *
     * @mbg.generated Mon Jun 24 13:08:53 GMT+08:00 2019
     */
    int updateByPrimaryKeySelective(Userplayers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userplayers
     *
     * @mbg.generated Mon Jun 24 13:08:53 GMT+08:00 2019
     */
    int updateByPrimaryKey(Userplayers record);
}