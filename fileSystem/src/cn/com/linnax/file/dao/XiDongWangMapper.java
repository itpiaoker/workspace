//package cn.com.linnax.file.dao;
//
//import cn.com.linnax.file.model.XiDongWang;
//import cn.com.linnax.file.model.XiDongWangExample;
//import cn.com.linnax.file.model.XiDongWangWithBLOBs;
//import org.apache.ibatis.annotations.Insert;
//import org.apache.ibatis.annotations.Param;
//import org.apache.ibatis.annotations.Select;
//
//import java.util.List;
//
//public interface XiDongWangMapper {
//    /**
//     * This method was generated by MyBatis Generator.
//     * This method corresponds to the database table xi_dong_wang
//     *
//     * @mbggenerated
//     */
//    int countByExample(XiDongWangExample example);
//
//    /**
//     * This method was generated by MyBatis Generator.
//     * This method corresponds to the database table xi_dong_wang
//     *
//     * @mbggenerated
//     */
//    int deleteByExample(XiDongWangExample example);
//
//    /**
//     * This method was generated by MyBatis Generator.
//     * This method corresponds to the database table xi_dong_wang
//     *
//     * @mbggenerated
//     */
//    @Insert({
//        "insert into xi_dong_wang (aid, typeid, ",
//        "redirecturl, templet, ",
//        "userip, download, top_nav, ",
//        "sub_nav, body, ",
//        "attribute, introduction)",
//        "values (#{aid,jdbcType=INTEGER}, #{typeid,jdbcType=SMALLINT}, ",
//        "#{redirecturl,jdbcType=VARCHAR}, #{templet,jdbcType=VARCHAR}, ",
//        "#{userip,jdbcType=CHAR}, #{download,jdbcType=VARCHAR}, #{topNav,jdbcType=VARCHAR}, ",
//        "#{subNav,jdbcType=VARCHAR}, #{body,jdbcType=LONGVARCHAR}, ",
//        "#{attribute,jdbcType=LONGVARCHAR}, #{introduction,jdbcType=LONGVARCHAR})"
//    })
//    int insert(XiDongWangWithBLOBs record);
//
//    /**
//     * This method was generated by MyBatis Generator.
//     * This method corresponds to the database table xi_dong_wang
//     *
//     * @mbggenerated
//     */
//    int insertSelective(XiDongWangWithBLOBs record);
//
//    /**
//     * This method was generated by MyBatis Generator.
//     * This method corresponds to the database table xi_dong_wang
//     *
//     * @mbggenerated
//     */
//    List<XiDongWangWithBLOBs> selectByExampleWithBLOBs(XiDongWangExample example);
//
//    /**
//     * This method was generated by MyBatis Generator.
//     * This method corresponds to the database table xi_dong_wang
//     *
//     * @mbggenerated
//     */
//    List<XiDongWang> selectByExample(XiDongWangExample example);
//
//    /**
//     * This method was generated by MyBatis Generator.
//     * This method corresponds to the database table xi_dong_wang
//     *
//     * @mbggenerated
//     */
//    int updateByExampleSelective(@Param("record") XiDongWangWithBLOBs record, @Param("example") XiDongWangExample example);
//
//    /**
//     * This method was generated by MyBatis Generator.
//     * This method corresponds to the database table xi_dong_wang
//     *
//     * @mbggenerated
//     */
//    int updateByExampleWithBLOBs(@Param("record") XiDongWangWithBLOBs record, @Param("example") XiDongWangExample example);
//
//    /**
//     * This method was generated by MyBatis Generator.
//     * This method corresponds to the database table xi_dong_wang
//     *
//     * @mbggenerated
//     */
//    int updateByExample(@Param("record") XiDongWang record, @Param("example") XiDongWangExample example);
//    
//    @Select({
//        "select count(aid) from xi_dong_wang"
//    })
//    int totalNumber();
//}