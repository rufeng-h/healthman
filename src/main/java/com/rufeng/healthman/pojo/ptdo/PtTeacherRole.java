package com.rufeng.healthman.pojo.ptdo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 教师、角色
 *
 * @author rufeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PtTeacherRole implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String teaId;
    private Long roleId;
    private LocalDateTime created;
    private LocalDateTime modified;
}