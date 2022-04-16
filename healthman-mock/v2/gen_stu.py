import argparse
import datetime
import json
import random
import re
from pathlib import Path

import pandas as pd
from faker import Faker

from sql import get_conn

faker = Faker('zh-CN')


def gen_date(number):
    fmt = '%Y-%m-%d'
    year = int(str(number)[:4]) - 18
    start = datetime.datetime.strptime(f'{year}-01-01', fmt)
    end = datetime.datetime.strptime(f'{year}-12-31', fmt)
    return faker.date_between_dates(start, end).strftime(fmt)


def gen_avatar():
    return faker.image_url()


class StuGenerator(object):
    def __init__(self, cnt=None):
        self.conn = get_conn()
        self.cnt = cnt
        self.cursor = self.conn.cursor()
        self.stu_prefix_map = json.loads(Path('./stu_prefix.json').read_text(encoding='utf-8'))

    def _gen_cls(self, cls_code, gen_cnt):
        genders, items, cnt = ['男', '女'], [], 1
        sql = f"SELECT COUNT(0) FROM pt_student WHERE cls_code = '{cls_code}'"
        self.cursor.execute(sql)
        if self.cursor.fetchall()[0][0] != 0:
            return []
        self.cursor.execute(f"SELECT cls_name FROM pt_class WHERE cls_code = '{cls_code}'")
        cls_name = self.cursor.fetchall()[0][0]
        pat = re.search(r'(\d{4})-(\d{2})', cls_name)
        year, cls_no = pat.group(1), pat.group(2).zfill(2)
        id_prefix = f"{year}{self.stu_prefix_map[cls_code]}{cls_no}"
        while cnt <= gen_cnt:
            stu = {'stu_id': f'{id_prefix}{str(cnt).zfill(2)}'}
            stu['stu_birth'] = gen_date(stu['stu_id'])
            stu['stu_gender'] = random.choice(genders)
            stu['stu_name'] = faker.name_male() if stu['stu_gender'] == 'M' else faker.name_female()
            stu['cls_name'] = cls_name
            items.append(stu)
            cnt += 1
        return items

    def run(self, clg_code=None, cls_code=None):
        if clg_code is not None:
            sql = f"SELECT cls_code FROM pt_class WHERE clg_code = '{clg_code}'"
            self.cursor.execute(sql)
            cls_codes = [code[0] for code in self.cursor.fetchall()]
            self.cursor.execute(f"SELECT clg_name FROM pt_college WHERE clg_code = '{clg_code}'")
            filename = self.cursor.fetchall()[0][0]
        else:
            sql = f"SELECT cls_name FROM pt_class WHERE cls_code = '{cls_code}'"
            self.cursor.execute(sql)
            filename = self.cursor.fetchall()[0]
            cls_codes = [cls_code]
        print(filename)
        data = []
        for code in cls_codes:
            data.extend(self._gen_cls(code, self.cnt if isinstance(self.cnt, int) else random.randint(25, 35)))
        self.to_excel(filename, data)

    def to_excel(self, name, items):
        print(f'共{len(items)}条数据')
        frame = pd.DataFrame(items)
        frame.rename(columns={'stu_id': '学号', 'stu_name': '姓名',
                              'stu_birth': '出生日期', 'cls_name': '班级',
                              'stu_gender': '性别'}, inplace=True)
        frame.to_excel(f'./data/stus/{name}.xlsx', index=False)

    def close(self):
        self.cursor.close()
        self.conn.close()


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('-cnt')
    parser.add_argument('-clg')
    parser.add_argument('-cls')
    args = parser.parse_args()
    if not args.clg and not args.cls:
        raise ValueError('无参数')
    if args.clg and args.cls:
        raise ValueError('学院或班级')
    g = StuGenerator(args.cnt)
    g.run(cls_code=args.cls, clg_code=args.clg)
