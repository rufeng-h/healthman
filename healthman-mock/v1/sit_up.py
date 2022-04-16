"""
 author rufeng
 date 2022/03/09/11:31
 description 
"""

import copy
import json
import random
from pathlib import Path

from sql import SQL


class ShortRunning:
    def __init__(self):
        """

        """
        self.sql = SQL()
        self.result = []
        self.subject_id = 7

    def to_json(self, gender):
        ls = self._data(gender)
        self.result.extend(ls)
        self._gen_next_year(gender, ls)
        self.sql.close()
        return self.result

    def _gen_next_year(self, gender, ls):
        print("生成下一年")
        cs = []
        chgs = [i for i in range(0, 5)]
        chgs.extend(i for i in range(-5, 0))
        for cur in ls:
            s = copy.copy(cur)
            year = cur['year'] + 1
            s['data'] = cur['data'] + random.choice(chgs)
            s['year'] = year
            grade = year - s['stu_no'] // 1000000 + 13
            s['score'] = self.sql.query_score(s['subject_id'], gender, grade, s['data'])
            cs.append(s)

        self.result.extend(cs)
        if len(self.result) // len(ls) == 4:
            return
        self._gen_next_year(gender, cs)

    def _data(self, gender):
        arg1 = (15, 57)
        arg2 = (27, 45)
        ms = [i for i in range(*arg1)]
        ms.extend([i for i in range(*arg2)])
        stus = self.sql.get_stus(gender)
        ls = []
        for i, k in enumerate(stus.keys()):
            # if i == 10:
            #     break
            stu = {"stu_no": k}
            stu['subject_id'] = self.subject_id
            stu['year'] = int(str(k)[:4])
            stu['data'] = random.choice(ms)
            stu['score'] = self.sql.query_score(self.subject_id, gender, 13, stu['data'])
            ls.append(stu)
        return ls


s = ShortRunning()
gender = 'F'
data = s.to_json(gender)
Path(f'./data/sit_up{gender}.json').write_text(json.dumps(data, ensure_ascii=False, indent=4), encoding='utf-8')
