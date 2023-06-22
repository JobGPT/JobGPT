from bs4 import BeautifulSoup

import requests
from io import BytesIO
import zipfile
import xmltodict
crtfc_key = '6ff44cfbc9ab0514222ca2d18dc68814282e81c9'

## 회사의 고유번호 따오기
api = 'https://opendart.fss.or.kr/api/corpCode.xml'
res = requests.get(api, params={'crtfc_key': crtfc_key})
z = zipfile.ZipFile(BytesIO(res.content))

print(z.namelist())
import json

target = "삼성전자"

data_xml = z.read('CORPCODE.xml').decode('utf-8')
data_odict = xmltodict.parse(data_xml)
data_dict = json.loads(json.dumps(data_odict))
data = data_dict.get('result', {}).get('list')

target_inform_dart = None
for item in data:
    if item['corp_name'] in [target]:
        target_inform_dart = item



## 회사의 공시정보 따오기
api = 'https://opendart.fss.or.kr/api/list.json'

from datetime import datetime, timedelta
now = datetime.now()
end_date = now.strftime("%Y%m%d")
start_date = now - timedelta(days=365)
start_date = start_date.strftime("%Y%m%d")

params = {
    'crtfc_key': crtfc_key,
    'corp_code' : '00126380', # 삼성전자 임
    'bgn_de' : start_date,
    'end_de' : end_date,
    'last_reprt_at': 'Y',
    'pblntf_ty': 'A',
    'corp_cls' : 'Y'
}

res = requests.get(api, params=params)
data = json.loads(res.content)

## 사업의 내용 챙겨오기 -> 공시서류 원본파일
'''
{'corp_code': '00126380', 'corp_name': '삼
성전자', 'stock_code': '005930', 'corp_cls': 'Y', 'report_nm': '분기보고서 (2023.03)', 'rcept_no': '20230515002335', 'flr_nm': '삼성전자', 'rcept_dt': '20230515', 'rm': ''}

{'corp_code': '00126380', 'corp_name': '삼성전자', 'stock_code': '005930', 'corp_cls': 'Y', 'report_nm': '사업보고서 (2022.12)', 'rcept_no': '20230307000542', 'flr_nm': '삼성전자', 'rcept_dt': '20230307', 'rm': '연'}, 
'''
api = 'https://opendart.fss.or.kr/api/document.xml'
params = {
    'crtfc_key': crtfc_key,
    'rcept_no' : '20230515002403', # 삼성전자 사업보고서
}

res = requests.get(api, params=params)
z = zipfile.ZipFile(BytesIO(res.content))

print(z.namelist())
import json

data_xml = z.read('20230515002403.xml').decode('utf-8')
with open('output.xml', 'w', encoding='utf-8') as f:
    f.write(data_xml)
data_xml = BeautifulSoup(data_xml, 'xml') 
str_xml = str(data_xml)
data_odict = xmltodict.parse(str_xml)
data_dict = json.loads(json.dumps(data_odict))


document = data_dict.get('DOCUMENT')
body = document.get('BODY')
sections = body.get('SECTION-1')

about_comps = sections[1].get('SECTION-2')
contents_of_business = sections[2].get('LIBRARY').get('SECTION-2')
# # print(len(lib))
about_comp = about_comps[0]
business_outline = contents_of_business[0]
main_services = contents_of_business[1]

print(about_comp)
print()
print(business_outline)
print()
print(main_services)

