import re

import pandas as pd
from docx import Document

document = Document(docx='./test.docx')


def to_dataframe(table):
    columns = [re.sub(r' |\n', '', cell.text) for cell in table.rows[0].cells]
    data = [[re.sub(r' |\n', '', cell.text) for cell in row.cells] for row in table.rows[1:]]
    return pd.DataFrame(data, columns=columns)


for i, table in enumerate(document.tables):
    to_dataframe(table).to_excel(f't{i + 1}.xlsx', index=False)
