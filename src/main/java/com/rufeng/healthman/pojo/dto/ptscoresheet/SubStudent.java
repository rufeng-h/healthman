package com.rufeng.healthman.pojo.dto.ptscoresheet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rufeng.healthman.enums.GenderEnum;
import com.rufeng.healthman.pojo.dto.ptstu.StudentBaseInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;


/**
 * @author rufeng
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
public class SubStudent {
    @NotNull
    @Min(1)
    @Max(16)
    private Integer grade;
    @NotNull
    private GenderEnum gender;
    /**
     * 必须为null
     */
    @JsonIgnore
    @Null
    private Long subId;

    public SubStudent(StudentBaseInfo info, Long subId) {
        this.gender = info.getStuGender();
        this.grade = info.getGrade();
        this.subId = subId;
    }
}