"""
 author rufeng
 date 2022/03/08/23:52
 description 
"""
import pymysql


class SQL:
    def __init__(self):
        self.conn = pymysql.connect(user='root', password='123456', database='healthman_test')
        self.cursor = self.conn.cursor()

    def get_stus(self, gender, cls_code):
        sql = f"SELECT stu_id, stu_gender FROM pt_student WHERE stu_gender = '{gender}' AND cls_code = '{cls_code}'"
        print(sql)
        self.cursor.execute(sql)
        return {int(number): g for number, g in self.cursor.fetchall()}

    def query_score(self, subject_id, gender, grade, data):
        self.cursor.execute(
            f"""SELECT score FROM pt_score_sheet WHERE 
            gender = '{gender}' and 
            subject_id = {subject_id} and
            grade = {grade} and
            {data} >= lower and
            {data} < upper""")
        score = self.cursor.fetchone()[0]
        assert score is not None
        return score

    def query_cls_name(self, cls_code):
        sql = f"SELECT cls_name FROM pt_class WHERE cls_code = '{cls_code}'"
        self.cursor.execute(sql)
        return self.cursor.fetchall()[0][0]

    def close(self):
        self.cursor.close()
        self.conn.close()


class MySQL:
    def __init__(self):
        self.conn = pymysql.connect(user='root', password='123456', database='healthman')
        self.cursor = self.conn.cursor()


def get_conn():
    return pymysql.connect(user='root', password='123456', database='healthman_test')
