"""
 author rufeng
 date 2022/03/08/23:52
 description 
"""
import pymysql


class SQL:
    def __init__(self):
        self.conn = pymysql.connect(user='root', password='123456', database='healthman')
        self.cursor = self.conn.cursor()

    def get_stus(self, gender):
        self.cursor.execute(f"SELECT stu_id, stu_gender FROM pt_student WHERE stu_gender = '{gender}'")
        return {number: g for number, g in self.cursor.fetchall()}

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

    def close(self):
        self.cursor.close()
        self.conn.close()


def get_conn():
    return pymysql.connect(user='root', password='123456', database='healthman')
