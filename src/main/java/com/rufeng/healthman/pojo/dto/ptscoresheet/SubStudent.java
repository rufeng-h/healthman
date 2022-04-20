package com.rufeng.healthman.pojo.dto.ptscoresheet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rufeng.healthman.enums.GenderEnum;
import com.rufeng.healthman.pojo.dto.ptstu.PtStudentBaseInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Objects;


/**
 * @author rufeng
 * id不包含在hashcode中
 */
@Data
@NoArgsConstructor
public class SubStudent {
    /**
     * update时使用
     */
    @Null
    @JsonIgnore
    private Long id;
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

    public SubStudent(PtStudentBaseInfo info, Long subId) {
        this.gender = info.getStuGender();
        this.grade = info.getGrade();
        this.subId = subId;
    }

    public SubStudent(Integer grade, GenderEnum gender, Long subId) {
        this.grade = grade;
        this.gender = gender;
        this.subId = subId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubStudent)) {
            return false;
        }
        SubStudent that = (SubStudent) o;
        return Objects.equals(grade, that.grade) && gender == that.gender && Objects.equals(subId, that.subId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grade, gender, subId);
    }
}