"""
 author rufeng
 date 2022/03/31/10:54
 description 
"""
import random

import pandas as pd
import sys

from sql import get_conn


class ScoreGennerator:
    def __init__(self, ms_id):
        self.ms_id = ms_id
        self.conn = get_conn()
        self.cursor = self.conn.cursor()
        self.ms_name = self.get_ms_name()
        self.sub_mapping = self._subs()
        self.func_mappings = {
            1: gen_height,
            2: gen_heavy,
            3: gen_bmi,
            4: gen_short_running,
            5: gen_vital,
            6: gen_pull_up,
            7: gen_sit_up,
            8: gen_long_running,
            9: gen_sitting,
            10: gen_jump,
        }

    def get_ms_name(self):
        self.cursor.execute(f"SELECT ms_name FROM pt_measurement WHERE ms_id = {self.ms_id}")
        return self.cursor.fetchall()[0][0]

    def _stu_subs(self):
        sql = f"""WITH stus AS (SELECT
                     stu_id,
                     stu_gender,
                     YEAR(cms_created) - pc.cls_entry_year + pc.cls_entry_grade AS grade
              FROM pt_class_measurement
                       LEFT JOIN pt_class pc
                                 on pt_class_measurement.cls_code = pc.cls_code
                       LEFT JOIN pt_student ps on pc.cls_code = ps.cls_code
              WHERE ms_id = {self.ms_id}),
     subs AS (SELECT sub_id
              from pt_subject_subgroup
                       left join pt_measurement on pt_measurement.grp_id = pt_subject_subgroup.grp_id
                       LEFT JOIN pt_subgroup ps on pt_subject_subgroup.grp_id = ps.grp_id
              WHERE pt_measurement.ms_id = {self.ms_id})
SELECT stu_id, pss.sub_id, stus.grade,stu_gender
FROM subs JOIN stus
         INNER JOIN pt_sub_student pss
                    ON pss.grade = stus.grade AND
                       stu_gender = pss.gender AND
                       subs.sub_id = pss.sub_id
"""
        self.cursor.execute(sql)
        return self.cursor.fetchall()

    def _subs(self):
        self.cursor.execute("SELECT sub_id, sub_name FROM pt_subject")
        return {k: v for k, v in self.cursor.fetchall()}

    def run(self):
        records = self._stu_subs()
        res = {}
        for record in records:
            stu = res.get(record[0])
            if stu is None:
                stu = res[record[0]] = {}
            stu[self.sub_mapping[record[1]]] = round(self.func_mappings.get(record[1])(record[2]), 2)
        frame = pd.DataFrame(res).T
        # frame['测试编号'] = self.ms_id
        frame.reset_index(inplace=True)
        frame.rename(columns={'index': '学号'}, inplace=True)
        # print(frame.pivot_table(index=['ms_id', 'stu_id', 'sub_name']))
        # print(frame)
        # frame.rename({'ms_id': '测试编号', 'stu_id': '学号', 'sub_name': '科目'}, inplace=True)
        frame.to_excel(f'./data/score/{self.ms_name}.xlsx', index=False)


def gen_heavy(gender):
    return random.randint(45, 65) if gender == 'F' else random.randint(55, 100)


def gen_vital(g):
    arg1 = (2300, 5200) if g == 'M' else (1800, 3500)
    return random.randint(*arg1)


def gen_bmi(_):
    return random.uniform(15.5, 30)


def gen_long_running(g):
    arg1 = (315, 611) if g == 'M' else (315, 523)
    return random.choice([i for i in range(*arg1) if i - i // 100 * 100 < 60])


def gen_short_running(g):
    return random.randint(64, 101) if g == 'M' else random.randint(74, 113)


def gen_pull_up(_):
    return random.randint(0, 23)


def gen_sitting(g):
    arg1 = (-20, 255) if g == 'M' else (20, 265)
    return random.randint(*arg1)


def gen_height(gender):
    return random.randint(150, 175) if gender == 'F' else random.randint(160, 190)


def gen_sit_up(_):
    return random.randint(15, 57)


def gen_jump(g):
    arg1 = (180, 280) if g == 'M' else (120, 210)
    return random.randint(*arg1)


if __name__ == '__main__':
    s = ScoreGennerator(int(sys.argv[1]))
    # s = ScoreGennerator(13)
    s.run()
