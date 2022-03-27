package com.rufeng.healthman.mapper;

import com.rufeng.healthman.pojo.DO.PtScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author rufeng
 * @description 针对表【pt_score(成绩表)】的数据库操作Mapper
 */
@Mapper
public interface PtScoreMapper {

    Integer insertBatch(@Param("items") List<PtScore> dataList);
}




