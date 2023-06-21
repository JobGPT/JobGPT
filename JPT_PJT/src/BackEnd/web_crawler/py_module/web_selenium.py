from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException, WebDriverException
from bs4 import BeautifulSoup
import time
import requests
import json


from driver_update import chromedriver_update
chromedriver_update()

target_url = 'https://www.catch.co.kr'

# options = None
# driver = None


# if not options:
#     options = webdriver.ChromeOptions()
#     options.add_experimental_option("excludeSwitches", ["enable-logging"])

# driver = webdriver.Chrome(
#             executable_path='chromedriver', 
#             options=options
#             )

# def search_by_selenium(driver, query : str|list[str], num_results : int) -> str | list[str]:
#     print('Crawling start with selenium')
    
#     if isinstance(query, str):       # 구글 검색 실패로 우회해서 들어올 경우 타겟 문구인 query가 들어옴
#         '''
#         우회하여 검색하는 코드는 아직 완성되지 않았습니다.
#         현재 작업 진행 중.
#         '''
#         raise f'Issue : have to make a code to bypass and access'
#     else : #isinstance(query, list)  # 구글 검색 성공시, list 안에 url이 담겨서 query가 들어옴
#         for url in query:
#             driver.get(url=url)
#             WebDriverWait(driver, 10).until(
#                 EC.presence_of_element_located((By.TAG_NAME, "body"))
#             )
#             html_source = driver.execute_script("return document.body.outerHTML;")
            

from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from bs4 import BeautifulSoup
import time
import json
import re

def find_table(html_: str) -> dict:
    table_items = html_.find('table')
    if not table_items: return dict()
    rows = table_items.find_all('tr')
    table_dict = {}
    
    cnt = 0; flag = False
    for row in rows:
        values = row.find_all('td')
        cols = row.find_all('th')
        if cols:
            for col, value in zip(cols, values):
                key = col.get_text(strip=True)
                table_dict[key] = value.get_text(strip=True)
                if '마감' in value.get_text(strip=True): flag = True
                    
        else:
            table_dict[cnt] = dict()
            for idx, value in enumerate(values):
                table_dict[cnt][idx] = value.get_text(strip=True)
                if '마감' in value.get_text(strip=True): flag = True
            cnt += 1
        if flag : return table_dict
    return table_dict

def scrape_catch(driver, url, idx):

    # 1. 해당 URL에 접속
    try:
        driver.get(url)
    except WebDriverException:
        return print("Failed to connect")
    
    # 2. 1초 대기 후
    if idx == 0: # idx == 0 -> 채용공고 일 경우 
        element = WebDriverWait(driver, 1).until(
            EC.presence_of_element_located((By.CSS_SELECTOR, "label[for='ckSortA1']"))
        )
        element.click()
    elif idx == 1: # idx == 0 -> 기업정보 일 경우 
        element = WebDriverWait(driver, 0)
        
    # 4. 1초간 대기
    # time.sleep(0)

    # 5. 해당 table을 찾음
    soup = BeautifulSoup(driver.page_source, 'html.parser')
    
    if idx == 0:
        return scrape_recruit(soup)
    elif idx == 1:
        return scrape_company_info(soup)
    


def scrape_common(soup):
    # 공통으로 수행되는 작업
    corp_detail_boxes = soup.find('div', class_='corp_detail_box')
    table = find_table(corp_detail_boxes)
    return table

def get_employment_url(soup):
    corp_detail_boxes = soup.find('div', class_='corp_detail_box')
    table = corp_detail_boxes.find('table')
    tr_elements = table.find_all('tr')
    
    site_link = []
    
    for tr_element in tr_elements:
        a_element = tr_element.find('td', class_='al1').find('a')
        href = a_element['href']
        detail_url = target_url + href
        response = requests.get(detail_url)
        if response.status_code == 200:
            html = response.text
            detail_soup = BeautifulSoup(html, 'html.parser')
            link_holder = detail_soup.find('li', class_="howto", id="view2")

            a_element = link_holder.find('a')
            href = a_element['href']
            employ_id = href.split('_')[1]
            _href = f'/controls/recruitLink/{employ_id}?gubun=1'
            site_link.append(target_url+_href)
            
    return site_link

def scrape_recruit(soup):
    global target_url
    # 채용공고에 필요한 추가 작업
    
    # 채용 사이트 받아오기

    site_link = get_employment_url(soup)
    table_items = scrape_common(soup)
    
    for idx, item in enumerate(table_items):
        
        table_items[item]['link'] = site_link[idx]
    
    return [table_items]


def extract_list(soup):

    # JavaScript 스크립트 찾기
    script = soup.find('script', text=re.compile(r'window.__NUXT__'))

    if not script:
        print("Script not found.")
        return None

    script_content = script.string

    # 'competitor' 부분 찾기
    competitor_pattern = r'competitor:"([^"]*)"'
    competitor_match = re.search(competitor_pattern, script_content)
    competitor = {idx : item for idx, item in enumerate(competitor_match.group(1).split(', '))} if competitor_match else None

    # 'brandList' 부분 찾기
    brandlist_pattern = r'brandList:(\[\{[^]]*\}\])'
    brandlist_match = re.search(brandlist_pattern, script_content)
    _brandlist = brandlist_match.group(1) if brandlist_match else None
    
    # 'BrandName'과 'BrandDesc' 추출하기
    brand_names = re.findall(r'BrandName:"([^"]*)"', _brandlist)
    brand_descs = re.findall(r'BrandDesc:"([^"]*)"', _brandlist)

    brandlist = dict()
    for name, desc in zip(brand_names, brand_descs):
        brandlist[name] = desc
        
    return [competitor, brandlist]

    return # competitor, brandlist

def scrape_company_info(soup):
    # 기업정보에 필요한 추가 작업
    script = soup.find('script', {'type':"application/ld+json"})

    script_info = json.loads(script.string)
    recom_reason_div = soup.find('div', {'class': 'recom_reason'})
    text_list = dict()
    if recom_reason_div:
        text_list = {idx : a_tag.text for idx, a_tag in enumerate(recom_reason_div.find_all('a'))}
        
    return [scrape_common(soup), script_info, text_list, *extract_list(soup)]