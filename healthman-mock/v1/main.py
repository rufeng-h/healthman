import datetime
import random
import re
from pprint import pprint

import pymysql
from faker import Faker

from sql import get_conn

faker = Faker('zh-CN')
start_year, end_year = 2018, 2022
target_colleges = {'计算机与人工智能学院', '材料科学与工程学院', '机械工程学院', '数学学院', '外国语学院', '交通运输与物流学院',
                   '人文学院', '信息科学与技术学院', '公共管理学院', '力学与航空航天学院', '土木工程学院', '地球科学与环境工程学院', '物理科学与技术学院', '生命科学与工程学院'}


def fetch_sql_data():
    conn = pymysql.connect(user='root', password='123456', database='healthman')
    cursor = conn.cursor()
    try:
        # 当前最大学号
        cursor.execute("SELECT MAX(number) FROM pt_student")
        max_number = cursor.fetchone()[0]
        max_number = 0 if max_number is None else int(str(max_number)[-6:])
        # 获取班级
        cursor.execute("SELECT name, code, major_code, major_name, college_name, college_id, grade FROM pt_class")
        cls_maps = {str(y): [] for y in range(start_year, end_year)}
        for name, code, major_code, major_name, college_name, college_id, grade in cursor.fetchall():
            year = re.search(r'(\d{4})-', name).group(1)
            if college_name in target_colleges and start_year <= int(year) < end_year:
                cls_maps[year].append({
                    'name': name,
                    'code': code,
                    'major_code': major_code,
                    'major_name': major_name,
                    'college_name': college_name,
                    'college_id': college_id,
                })
    finally:
        cursor.close()
        conn.close()
    return max_number, cls_maps


def gen_date(number):
    fmt = '%Y-%m-%d'
    year = int(str(number)[:4]) - 18
    start = datetime.datetime.strptime(f'{year}-01-01', fmt)
    end = datetime.datetime.strptime(f'{year}-12-31', fmt)
    return faker.date_between_dates(start, end).strftime(fmt)


def gen_avatar():
    return faker.image_url()


def gen_student(total):
    cnt, [cur_number, cls_maps] = 0, fetch_sql_data()
    years = list(range(start_year, end_year))
    genders = ['M', 'F']
    while cnt <= total:
        stu = {}
        year = random.choice(years)
        stu['number'] = int(f'{year}{str(cur_number + 1).zfill(6)}')
        stu['birthday'] = gen_date(stu['number'])
        stu['avatar'] = gen_avatar()
        stu['gender'] = random.choice(genders)
        stu['name'] = faker.name_male() if stu['gender'] == 'M' else faker.name_female()
        cls = random.choice(cls_maps[str(year)])
        stu['class_code'] = cls['code']
        stu['college_id'] = cls['college_id']
        stu['major_code'] = cls['major_code']
        yield stu
        cnt, cur_number = cnt + 1, cur_number + 1


class StuGenerator(object):
    def __init__(self, cls_code, gen_cnt):
        self.conn = get_conn()
        self.cursor = self.conn.cursor()
        self.cls_code = cls_code
        self.gen_cnt = gen_cnt
        self._prepare()

    def run(self):
        genders = ['M', 'F']
        items = []
        cnt = 1
        while cnt <= self.gen_cnt:
            stu = {}
            stu['stu_id'] = f'{self.id_prefix}{str(cnt).zfill(2)}'
            stu['stu_birth'] = gen_date(stu['stu_id'])
            stu['avatar'] = gen_avatar()
            stu['stu_gender'] = random.choice(genders)
            stu['stu_name'] = faker.name_male() if stu['stu_gender'] == 'M' else faker.name_female()
            stu['cls_code'] = self.cls_code
            stu['clg_code'] = self.clg_code
            items.append(stu)
            cnt += 1

        return items

    def to_excel(self):
        pass

    def _prepare(self):
        self.cursor.execute(f"SELECT COUNT(0) FROM pt_student WHERE cls_code = '{self.cls_code}'")
        if self.cursor.fetchall()[0][0] != 0:
            raise ValueError
        self.cursor.execute(f"SELECT clg_code, cls_name FROM pt_class WHERE cls_code = '{self.cls_code}'")
        self.clg_code, cls_name = self.cursor.fetchall()[0]
        clg_code = self.clg_code.zfill(2)
        pat = re.search(r'(\d{4})-(\d{2})', cls_name)
        year, cls_no = pat.group(1), pat.group(2).zfill(2)
        self.id_prefix = f"{year}{clg_code}{cls_no}"
        self.cursor.close()
        self.conn.close()


if __name__ == '__main__':
    # data = list(gen_student(20000))
    # Path("data/suts.json").write_text(json.dumps(data), encoding="utf-8")
    g = StuGenerator('0101201702', 40)
    pprint(g.run())
