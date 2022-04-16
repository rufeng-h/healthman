"""
 author rufeng
 date 2022/03/08/17:54
 description
 生成成绩数据
"""
import copy
import json
import random
from pathlib import Path
from typing import *

import numpy as np
from sql import SQL


class Bmi:
    def __init__(self):
        """
        """
        self.sql = SQL()
        self.hs = []
        self.vs = []
        self.bs = []

    def to_json(self, gender: str):
        print("正在生成第一年数据...")
        stus = self.sql.get_stus(gender)
        data_m = self.data_male(len(stus)) if gender == 'M' else self.data_female(len(stus))
        hs, vs, bs = [], [], []
        for i, k in enumerate(stus.keys()):
            # if i == 100:
            #     break

            # 身高
            stu = {"stu_no": k}
            stu['subject_id'] = 1
            stu['year'] = int(str(k)[:4])
            stu['data'] = data_m[0, i]
            stu['score'] = None
            hs.append(stu)

            # 体重
            stu = {"stu_no": k}
            stu['subject_id'] = 2
            stu['year'] = int(str(k)[:4])
            stu['data'] = data_m[1, i]
            stu['score'] = None
            vs.append(stu)

            # bmi
            stu = {"stu_no": k}
            stu['subject_id'] = 3
            stu['year'] = int(str(k)[:4])
            stu['data'] = data_m[2, i]
            stu['score'] = self.sql.query_score(3, gender, 13, stu['data'])
            bs.append(stu)

        print("第一年生成完毕...")
        self.hs.extend(hs)
        self.vs.extend(vs)
        self.bs.extend(bs)

        self._gen_next_three_year(gender, hs, vs, bs)
        self.sql.close()
        return self.hs, self.vs, self.bs

    def _gen_next_three_year(self, gender, hs: List[Dict], vs: List[Dict], bs: List[Dict]) -> None:
        ha = [i / 10 for i in range(6)]
        va = [i / 10 for i in range(-60, 60)]
        hhs, vvs, bbs = [], [], []
        print(f"下一年...")
        for i in range(len(hs)):
            cur_h = hs[i]
            year = cur_h['year'] + 1
            h = copy.copy(cur_h)
            h['year'] = year
            h['data'] = cur_h['data'] + random.choice(ha)

            cur_v = vs[i]
            v = copy.copy(cur_v)
            v['year'] = year
            v['data'] = cur_v['data'] + random.choice(va)

            cur_b = bs[i]
            b = copy.copy(cur_b)
            b['year'] = year
            b['data'] = np.round(v['data'] ** 2 / h['data'], 1) * 10

            grade = year - v['stu_no'] // 1000000 + 13
            b['score'] = self.sql.query_score(b['subject_id'], gender, grade, b['data'])

            assert cur_h['year'] == cur_v['year'] == cur_b['year']

            hhs.append(h)
            bbs.append(b)
            vvs.append(v)

        self.hs.extend(hhs)
        self.vs.extend(vvs)
        self.bs.extend(bbs)

        if len(self.hs) // len(hs) == 4:
            return
        self._gen_next_three_year(gender, hhs, vvs, bbs)

    def data_male(self, CNT):
        bmi = (self._bmi_male(CNT=CNT) * 10).astype(np.int32)
        height = self._heights_male(CNT=CNT)
        heavy = np.round(np.sqrt(height * bmi/ 10), 2)
        data = np.array([height, heavy, bmi])
        return data

    def data_female(self, CNT):
        bmi = (self._bmi_female(CNT=CNT) * 10).astype(np.int32)
        height = self._heights_female(CNT=CNT)
        heavy = np.round(np.sqrt(height * bmi / 10), 2)
        data = np.array([height, heavy, bmi])
        return data

    def _bmi_male(self, M=22, D=1.5, MIN=15.8, MAX=30, CNT=24748):
        bmi = np.round(self._normal(M, D, MIN, MAX, CNT), 1)
        return bmi

    def _heights_male(self, CNT=24748, D=5.5, M=175, MAX=205, MIN=155):
        ret = np.round(self._normal(M, D, MIN, MAX, CNT), 2)
        return ret

    def _normal(self, M, D, MIN, MAX, CNT):
        ret = np.random.normal(M, D, CNT)
        while ret.min() < MIN or ret.max() > MAX:
            print(ret.min(), ret.max())
            ret = np.random.normal(M, D, CNT).round(2)
        print(ret.min(), ret.max())
        return ret

    def _heights_female(self, CNT=25253, D=4.2, M=165, MAX=190, MIN=150):
        ret = np.round(self._normal(M, D, MIN, MAX, CNT), 2)
        return ret

    def _bmi_female(self, M=22, D=1.5, MIN=15.5, MAX=30, CNT=25253):
        return np.round(self._normal(M, D, MIN, MAX, CNT), 1)


# stu_list = fetch_sql_data()
# print(len(stu_list))
# b = Bmi()
# b.data_female()
# b.data_male()
# b.to_sql()


gender = 'F'
b = Bmi()
# males = b.to_json('M')
# females = b.to_json('F')
# males.extend(females)
# hs, vs, bs = b.to_json(gender)
# result = []
# result.extend(hs)
# result.extend(vs)
# result.extend(bs)
# Path(f'./bmi{gender}.json').write_text(json.dumps(result, ensure_ascii=False, indent=4), encoding='utf-8')