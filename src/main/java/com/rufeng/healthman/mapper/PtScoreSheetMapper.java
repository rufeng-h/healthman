package com.rufeng.healthman.mapper;

import com.rufeng.healthman.pojo.DO.PtScoreSheet;
import com.rufeng.healthman.pojo.Query.PtScoreSheetQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author rufeng
 * @description 针对表【pt_score_sheet(单项评分标准（国网）)】的数据库操作Mapper
 */
@Mapper
public interface PtScoreSheetMapper {
    /**
     * 查询量表
     *
     * @param query query
     * @return list
     */
    List<PtScoreSheet> listScoreSheet(@NonNull @Param("query") PtScoreSheetQuery query);

    Integer insertBatch(@Param("items") List<PtScoreSheet> sheets);
}




