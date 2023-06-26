from bs4 import BeautifulSoup
from datetime import datetime, timedelta
import requests
from io import BytesIO
import zipfile
import xmltodict
import json
crtfc_key = '6ff44cfbc9ab0514222ca2d18dc68814282e81c9'

## 회사의 고유번호 따오기
api = 'https://opendart.fss.or.kr/api/corpCode.xml'
res = requests.get(api, params={'crtfc_key': crtfc_key})
z = zipfile.ZipFile(BytesIO(res.content))

print(z.namelist())
import json



data_xml = z.read('CORPCODE.xml').decode('utf-8')
data_odict = xmltodict.parse(data_xml)
data_dict = json.loads(json.dumps(data_odict))
data = data_dict.get('result', {}).get('list')

# target = "삼성전자"
# target_inform_dart = None
# for item in data:
#     if item['corp_name'] in [target]:
#         target_inform_dart = item
#         break

# print(target_inform_dart['corp_code'])


## 회사의 공시정보 따오기


api = 'https://opendart.fss.or.kr/api/list.json'
now = datetime.now()
end_date = now.strftime("%Y%m%d")
start_date = now - timedelta(days=365)
start_date = start_date.strftime("%Y%m%d")

params = {
    'crtfc_key': crtfc_key,
    'corp_code' : target_inform_dart['corp_code'], # 삼성전자 임
    'bgn_de' : start_date,
    'end_de' : end_date,
    'last_reprt_at': 'Y',
    'pblntf_ty': 'A',
    'corp_cls' : 'Y'
}

res = requests.get(api, params=params)
data = json.loads(res.content)
if data.get('list'):
    D = next((item for item in data.get('list') if "분기보고서" in item["report_nm"]), None)
else:
    D = None
## 사업의 내용 챙겨오기 -> 공시서류 원본파일
'''
{'corp_code': '00126380', 'corp_name': '삼
성전자', 'stock_code': '005930', 'corp_cls': 'Y', 'report_nm': '분기보고서 (2023.03)', 'rcept_no': '20230515002335', 'flr_nm': '삼성전자', 'rcept_dt': '20230515', 'rm': ''}

{'corp_code': '00126380', 'corp_name': '삼성전자', 'stock_code': '005930', 'corp_cls': 'Y', 'report_nm': '사업보고서 (2022.12)', 'rcept_no': '20230307000542', 'flr_nm': '삼성전자', 'rcept_dt': '20230307', 'rm': '연'}, 
'''
try:
    if D is not None:
        rcept_no = D['rcept_no']
        api = 'https://opendart.fss.or.kr/api/document.xml'
        params = {
            'crtfc_key': crtfc_key,
            'rcept_no' : rcept_no, # 삼성전자 사업보고서
        }

        res = requests.get(api, params=params)
        z = zipfile.ZipFile(BytesIO(res.content))

        data_xml = z.read(f'{rcept_no}.xml').decode('utf-8')
        data_xml = BeautifulSoup(data_xml, 'xml') 
        str_xml = str(data_xml)
        data_odict = xmltodict.parse(str_xml)
        data_dict = json.loads(json.dumps(data_odict))


        document = data_dict.get('DOCUMENT')
        body = document.get('BODY')
        sections = body.get('SECTION-1')
        
        for section in sections:
            about_comps = section.get('SECTION-2')
            contents_of_business = section.get('LIBRARY')
            
            if about_comps is not None:
                pass
                # print(about_comps)
                # print()
                # for cont in about_comps:
                #     print(cont)
                #     print()
                
            if contents_of_business is not None:

                get_Data = contents_of_business.get('SECTION-2')[0]
                if get_Data.get('P') : 
                    p_tag = get_Data.get('P')
                    print(p_tag)
                # for cont in contents_of_business:
                #     print(cont
                #)
                #     print()
            # print(about_comps)
            # print()
            # print(contents_of_business)
            # print()
    else:
        print("얻은 데이터가 없습니다.")
except Exception as e: 
    print(f'{D["corp_name"]}에서 예외가 발생했습니다.', e)
    
    ## 삼성에서는 되는 코드    
    # about_comps = sections[1].get('SECTION-2')
    # contents_of_business = sections[2].get('LIBRARY').get('SECTION-2')
    # # # print(len(lib))
    # about_comp = about_comps[0]
    # business_outline = contents_of_business[0]
    # main_services = contents_of_business[1]

    # print(about_comp)
    # print()
    # print(business_outline)
    # print()
    # print(main_services)

############################################################
############################################################
def get_target(target):
    data_xml = z.read('CORPCODE.xml').decode('utf-8')
    data_odict = xmltodict.parse(data_xml)
    data_dict = json.loads(json.dumps(data_odict))
    data = data_dict.get('result', {}).get('list')

    target_inform_dart = None
    for item in data:
        if item['corp_name'] in [target]:
            target_inform_dart = item
            break

    try: return target_inform_dart['corp_code']
    except: return None

def get_comp(target_inform_dart):
    api = 'https://opendart.fss.or.kr/api/list.json'
    now = datetime.now()
    end_date = now.strftime("%Y%m%d")
    start_date = now - timedelta(days=365)
    start_date = start_date.strftime("%Y%m%d")

    params = {
        'crtfc_key': crtfc_key,
        'corp_code' : target_inform_dart.corp_code, # 삼성전자 임
        'bgn_de' : start_date,
        'end_de' : end_date,
        'last_reprt_at': 'Y',
        'pblntf_ty': 'A',
        'corp_cls' : 'Y'
    }

    res = requests.get(api, params=params)
    data = json.loads(res.content)

    D = next((item for item in data if "분기보고서" in item["report_nm"]), None)
    return D

def get_mainserv(D):
    try:
        if D is not None:
            rcept_no = D['rcept_no']
            api = 'https://opendart.fss.or.kr/api/document.xml'
            params = {
                'crtfc_key': crtfc_key,
                'rcept_no' : rcept_no, # 삼성전자 사업보고서
            }

            res = requests.get(api, params=params)
            z = zipfile.ZipFile(BytesIO(res.content))

            data_xml = z.read(f'{rcept_no}.xml').decode('utf-8')
            data_xml = BeautifulSoup(data_xml, 'xml') 
            str_xml = str(data_xml)
            data_odict = xmltodict.parse(str_xml)
            data_dict = json.loads(json.dumps(data_odict))


            document = data_dict.get('DOCUMENT')
            body = document.get('BODY')
            sections = body.get('SECTION-1')
            
            for section in sections:
                about_comps = section.get('SECTION-2')
                contents_of_business = section.get('LIBRARY')
                
                if about_comps is not None:
                    pass
                    # print(about_comps)
                    # print()
                    # for cont in about_comps:
                    #     print(cont)
                    #     print()
                    
                if contents_of_business is not None:

                    get_Data = contents_of_business.get('SECTION-2')[0]
                    if get_Data.get('P') : 
                        p_tag = get_Data.get('P')
                        print(p_tag)
                    # for cont in contents_of_business:
                    #     print(cont
                    #)
                    #     print()
                # print(about_comps)
                # print()
                # print(contents_of_business)
                # print()
        else:
            print("얻은 데이터가 없습니다.")
            return None
    except Exception as e: 
        print(f'{D["corp_name"]}에서 예외가 발생했습니다.', e)
        return None

# for idx, item in enumerate(data):
#     d = get_comp(item)
    
#     if idx == 300:
#         break