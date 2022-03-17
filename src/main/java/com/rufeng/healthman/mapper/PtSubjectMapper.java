package com.rufeng.healthman.mapper;

import com.rufeng.healthman.pojo.DO.PtSubject;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author rufeng
 * @description 针对表【pt_subject(科目)】的数据库操作Mapper
 */
@Mapper
public interface PtSubjectMapper {

    /**
     * 查询所有
     *
     * @return list
     */
    List<PtSubject> listSubject();
}




