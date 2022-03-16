package com.rufeng.healthman.service;

import com.rufeng.healthman.pojo.DO.PtMajor;
import com.rufeng.healthman.pojo.Query.PtMajorQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-13 9:28
 * @package com.rufeng.healthman.service
 * @description TODO
 */
public interface PtMajorService {
    /**
     * page
     *
     * @param query 查询条件
     * @return page
     */
    List<PtMajor> listMajor(@Param("query") @NonNull PtMajorQuery query);
}
