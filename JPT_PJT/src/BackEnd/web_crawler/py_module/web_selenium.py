from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException, WebDriverException
from bs4 import BeautifulSoup
from bs4.element import Tag, NavigableString
import time
import requests
import json


from driver_update import chromedriver_update
chromedriver_update()

target_url = 'https://www.catch.co.kr'
            

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
                print("=============================")
                v_seting = []
                for v in value:
                    v_seting = find_string(v, v_seting)
                print(v_seting)
                print("=============================")
                table_dict[cnt][idx] = v_seting
                if '마감' in value.get_text(strip=True): flag = True
            cnt += 1
        if flag : return table_dict
    return table_dict

def find_string(v_String,lst):
    if isinstance(v_String, NavigableString):
        if not v_String == ' ':
            lst += [v_String]
    else:
        for v in v_String:
            find_string(v, lst)
    return lst

def scrape_catch(driver, url, idx):

    # 1. 해당 URL에 접속
    try:
        driver.get(url)
    except WebDriverException:
        return print("Failed to connect")
    
    # 2. 1초 대기 후
    if idx == 0: # idx == 0 -> 채용공고 일 경우 
        element = WebDriverWait(driver, 1)
        
        # try:
        #     # 모달 창의 닫기 버튼을 찾아서 클릭
        #     close_buttons = driver.find_element(By.CSS_SELECTOR, '.modal_wrap .close')
        #     print("야호!",len(close_buttons))
        # except Exception as e:
        #     print('!!!! no MODAL !!!!!!!')
        close_buttons = driver.find_elements(By.CSS_SELECTOR, '.modal_wrap .close')
        for button in close_buttons:
            try: button.click()
            except : pass
        element = driver.find_element(By.CSS_SELECTOR, "label[for='ckSortA1']")
        element.click()
    elif idx == 1: # idx == 0 -> 기업정보 일 경우 
        element = WebDriverWait(driver, 0)
        
    # 4. 1초간 대기
    # time.sleep(0)
    soup = BeautifulSoup(driver.page_source, 'html.parser')

        # 4.1 모달 있는지 확인 후 있으면 닫기
    
    # 5. 해당 table을 찾음
    
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
    site_link = get_employment_url(soup) # 채용 공고 링크
    table_items = scrape_common(soup) # 채용 공고 및 정보
    
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


def scrape_company_info(soup):
    # 기업정보에 필요한 추가 작업
    
    # # Search for the pattern
    # script = soup.find('script', text=re.compile(r'window.__NUXT__'))
    # pattern = r'benefitList":"([^"]*)"'
    # print("어엉",script)
    # match = re.search(pattern, str(script))
    # benefit_list_json = dict()
    # if match:
    #     # Extract the matched text and convert it to valid JSON
    #     benefit_list_str = match.group(1).replace("'", '"')
    #     benefit_list_json = json.loads(benefit_list_str)
    #     print(benefit_list_json)
    
    script = soup.find('script', {'type':"application/ld+json"})
    
    script_info = json.loads(script.string)
    recom_reason_div = soup.find('div', {'class': 'recom_reason'})
    text_list = dict()
    if recom_reason_div:
        text_list = {idx : a_tag.text for idx, a_tag in enumerate(recom_reason_div.find_all('a'))}
        
    return [scrape_common(soup), script_info, text_list, *extract_list(soup)]