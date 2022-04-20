"""
 author rufeng
 date 2022/03/22/12:57
 description 
"""
import random

import pandas as pd

from sql import MySQL


class PtClass(MySQL):
    def __init__(self):
        super().__init__()
        self._prepare()
        self.class_df = pd.read_excel("./data/class.xlsx", "Sheet1")
        cond = self.class_df['grade'].apply(lambda x: x >= 2019)
        self.class_df = self.class_df[cond]

    def _prepare(self):
        self.cursor.execute("SELECT clg_code, clg_name FROM pt_college")

        self.clg_mappings = {code: name for code, name in self.cursor.fetchall()}
        self.cursor.execute("SELECT tea_id FROM pt_teacher")
        self.tea_ids = [x[0] for x in self.cursor.fetchall()]
        self.cursor.close()
        self.conn.close()

    def to_excel(self):
        self.class_df['college'] = self.class_df['college_id'].apply(lambda x: self.clg_mappings.get(str(x)))
        print(self.class_df['college'].value_counts())
        self.class_df.dropna(axis=0, inplace=True)
        # self.class_df['录入年份'] = self.class_df.apply(lambda row: row.grade, axis=1)
        self.class_df['年级'] = '大一'

        self.class_df = self.class_df[['code', 'name', 'college_name', '年级']].copy()
        self.class_df.rename(columns={'code': '班级代码', 'name': '班级名称', 'college_name': '学院'}, inplace=True)
        self.class_df['教师工号'] = self.class_df.apply(lambda x: random.choice(self.tea_ids), axis=1)
        for name in self.class_df['学院'].value_counts().keys():
            self.class_df[self.class_df['学院'] == name].to_excel(f'./data/classes/{name}.xlsx',
                                                                index=False)


if __name__ == '__main__':
    PtClass().to_excel()
