"""
 author rufeng
 date 2022/04/14/0:19
 description 
"""
import argparse
import math

import numpy as np
import pandas as pd

from sql import get_conn

gradeMappings = {
    1: '一年级',
    2: '二年级',
    3: '三年级',
    4: '四年级',
    5: '五年级',
    6: '六年级',
    7: '初一',
    8: '初二',
    9: '初三',
    10: '高一',
    11: '高二',
    12: '高三',
    13: '大一',
    14: '大二',
    15: '大三',
    16: '大四',
}


class ScoreSheetGenerator(object):
    def __init__(self, sub_id, start, end, step, levels):
        self.sub_id = sub_id
        self.start = start
        self.end = end
        self.step = step
        self.levels = levels
        self.conn = get_conn()
        self.cursor = self.conn.cursor()

    def run(self):
        self.cursor.execute(f"SELECT sub_name FROM pt_subject WHERE sub_id = {self.sub_id}")
        sub_name = self.cursor.fetchone()[0]
        print(sub_name)
        self.cursor.execute(f"SELECT gender, grade FROM pt_sub_student WHERE sub_id = {self.sub_id}")
        res = []
        for i, (gender, grade) in enumerate(self.cursor.fetchall()):
            res.extend(self._gen(self.start, self.end, self.step * (1 + i * 0.05), gender, grade))
        pd.DataFrame(res).to_excel(f'./data/score_sheet/{sub_name}.xlsx', index=False)

    def _gen(self, start, end, step, gender, grade):
        data = [None, *np.arange(start, end, step).round(2).tolist(), None]
        print(gradeMappings[grade], gender, data)
        sp = math.ceil((len(data) - 1) / len(self.levels))
        s = 100 / (len(data) - 1)
        res = []
        for i in range(0, len(data) - 1):
            item = {}
            item['等级'] = self.levels[i // sp]
            item['下限'] = data[i]
            item['上限'] = data[i + 1]
            item['成绩'] = int(i * s)
            item['性别'] = '男' if gender == 'M' else '女'
            item['年级'] = gradeMappings[grade]
            res.append(item)
        return res

    def close(self):
        self.cursor.close()
        self.conn.close()


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('subId', type=int)
    parser.add_argument('start', type=float)
    parser.add_argument('end', type=float)
    parser.add_argument('step', type=float)
    parser.add_argument('-l', action='extend', default=['优', '良', '中', '差'], dest='levels')
    args = parser.parse_args()
    print(args)
    ScoreSheetGenerator(args.subId, args.start, args.end, args.step, args.levels).run()
