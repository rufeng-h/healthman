"""
 author rufeng
 date 2022/03/22/12:29
 description 
"""
import pandas as pd

from sql import MySQL


class College(MySQL):
    def __init__(self):
        super().__init__()

    def to_excel(self):
        self.cursor.execute(
            "SELECT clg_code, clg_name, clg_principal, clg_office, clg_tel, clg_home FROM pt_college")
        columns = ('学院代码', '学院名称', '负责人', '办公室', '电话', '主页')
        pd.DataFrame(self.cursor.fetchall(), columns=columns).to_excel('./data/college.xlsx', index=False)

        self.cursor.close()
        self.conn.close()


if __name__ == '__main__':
    College().to_excel()
