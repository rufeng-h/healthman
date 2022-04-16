"""
 author rufeng
 date 2022/03/31/16:11
 description 
"""
import json
from pathlib import Path

import pandas as pd

if __name__ == '__main__':
    df = pd.read_excel('./data/class.xlsx', 'Sheet1')
    mappings = {}
    for idx, row in df.iterrows():
        clg_code = str(row['college_id']).zfill(2)
        majors = mappings.get(clg_code)
        if majors is None:
            majors = set()
            mappings[clg_code] = majors
        majors.add(row['major_name'])
    mmap = {}
    for k, v in mappings.items():
        for i, m in enumerate(v):
            mmap[m] = k + str(i + 1).zfill(2)
    res = {}
    for _, row in df.iterrows():
        res[row['code']] = mmap[row['major_name']]
    Path('./stu_prefix.json').write_text(json.dumps(res, ensure_ascii=False, indent=4), encoding='utf-8')
