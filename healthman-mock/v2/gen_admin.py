"""
 author rufeng
 date 2022/03/28/9:02
 description 
"""
import copy

import pandas as pd
from faker import Faker

from v2.sql import get_conn

faker = Faker('zh-CN')


def gen_avatar():
    return faker.image_url()


class AdminGenerator(object):
    def __init__(self):
        self.conn = get_conn()
        self.cursor = self.conn.cursor()
        self.classes = self._get_classes()
        self.colleges = self._get_colleges()
        self.class_college_map = self._get_cls_name_clg_name()

    def run(self):
        template = {'姓名': None, '邮箱': None, '备注': None, '手机': None, '工号': None, '班级权限': None, '学院权限': None}
        res = []

        cnt = 1
        for clg in self.colleges:
            item = copy.copy(template)
            item['工号'] = str(cnt).zfill(6)
            item['姓名'] = faker.name()
            item['邮箱'] = faker.email()
            item['手机'] = faker.phone_number()
            item['学院权限'] = clg
            item['学院'] = clg
            res.append(item)
            cnt += 1

        for cls in self.classes:
            item = copy.copy(template)
            item['工号'] = str(cnt).zfill(6)
            item['姓名'] = faker.name()
            item['邮箱'] = faker.email()
            item['手机'] = faker.phone_number()
            item['班级权限'] = cls
            item['学院'] = self.class_college_map.get(cls)
            res.append(item)
            cnt += 1

        self.cursor.close()
        self.conn.cursor()

        pd.DataFrame(res).to_excel(f'./data/admin.xlsx', index=False)

        return res

    def _get_classes(self):
        self.cursor.execute("SELECT cls_name FROM pt_class")
        return [t[0] for t in self.cursor.fetchall()]

    def _get_colleges(self):
        self.cursor.execute("SELECT clg_name FROM pt_college")
        return [t[0] for t in self.cursor.fetchall()]

    def _get_cls_name_clg_name(self):
        self.cursor.execute('''SELECT cls_name, clg_name
FROM pt_class
         INNER JOIN pt_college on pt_class.clg_code = pt_college.clg_code''')
        return {k: v for k, v in self.cursor.fetchall()}


if __name__ == '__main__':
    s = AdminGenerator()
    s.run()
