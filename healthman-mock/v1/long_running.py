"""
 author rufeng
 date 2022/03/08/23:51
 description 
"""
import copy
import json
import random
from pathlib import Path

from sql import SQL


class LongRunning:
    def __init__(self):
        """

        """
        self.sql = SQL()
        self.result = []

    def to_json(self, gender):
        ls = self._data(gender)
        self.result.extend(ls)
        self._gen_next_year(gender, ls)
        self.sql.close()
        return self.result

    def _gen_next_year(self, gender, ls):
        print("生成下一年")
        cs = []
        chgs = [i for i in range(0, 30)]
        chgs.extend(i for i in range(-10, 0))
        for cur in ls:
            s = copy.copy(cur)
            year = cur['year'] + 1
            v = cur['data'] - cur['data'] // 100 * 100
            r = random.choice(chgs)

            if v + r < 0:
                r -= 40

            elif v + r >= 60:
                r += 40

            s['data'] = cur['data'] + r

            s['year'] = year
            grade = year - s['stu_no'] // 1000000 + 13
            s['score'] = self.sql.query_score(s['subject_id'], gender, grade, s['data'])
            cs.append(s)

        self.result.extend(cs)
        if len(self.result) // len(ls) == 4:
            return
        self._gen_next_year(gender, cs)

    def _data(self, gender):
        arg1 = (315, 611) if gender == 'M' else (315, 523)
        arg2 = (345, 430) if gender == 'M' else (347, 432)
        ms = [i for i in range(*arg1) if i - i // 100 * 100 < 60]
        ms.extend([i for i in range(*arg2) if i - i // 100 * 100 < 60])

        stus = self.sql.get_stus(gender)
        ls = []
        for i, k in enumerate(stus.keys()):
            # if i == 100:
            #     break
            stu = {"stu_no": k}
            stu['subject_id'] = 8
            stu['year'] = int(str(k)[:4])
            stu['data'] = random.choice(ms)
            stu['score'] = self.sql.query_score(8, gender, 13, stu['data'])
            ls.append(stu)
        return ls


gender = 'F'
l = LongRunning()
result = l.to_json(gender)
for d in result:
    data = d['data']
    v =  data - data // 100 * 100
    if v >= 60:
        print(v)
        raise ValueError

Path(f'long_running{gender}.json').write_text(json.dumps(result, ensure_ascii=False, indent=4),
                                              encoding='utf-8')
