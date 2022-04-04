package com.rufeng.healthman.pojo.DTO.ptscoresheet;

import com.rufeng.healthman.enums.GenderEnum;
import com.rufeng.healthman.pojo.DTO.ptstu.StudentBaseInfo;
import lombok.Data;

import java.util.Objects;

/**
 * @author rufeng
 */
@Data
public class ScoreSheetKey {
    private Integer grade;
    private GenderEnum stuGender;
    private Long subId;

    public ScoreSheetKey(StudentBaseInfo info, Long subId) {
        this.stuGender = info.getStuGender();
        this.grade = info.getGrade();
        this.subId = subId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScoreSheetKey)) {
            return false;
        }
        ScoreSheetKey that = (ScoreSheetKey) o;
        return grade.equals(that.grade) && stuGender == that.stuGender && subId.equals(that.subId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grade, stuGender, subId);
    }
}