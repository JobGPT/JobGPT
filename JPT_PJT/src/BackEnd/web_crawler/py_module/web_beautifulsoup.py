from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException, WebDriverException
# from driver_update import chromedriver_update
# chromedriver_update()
## 나중에 Model 을활용해 해결 예정
options = None
driver = None

if not options:
    options = webdriver.ChromeOptions()
    options.add_experimental_option("excludeSwitches", ["enable-logging"])

def set_driver():
    global driver
    if driver: return
    driver = webdriver.Chrome(
                executable_path='chromedriver', 
                options=options
                )
## driver 를 켜두기

import requests
import json
from urllib.parse import urlparse
from bs4 import BeautifulSoup
from web_selenium import *

def search_at_CATCH(URL : str):
    domain = find_domain(URL)
    response = requests.get(URL)
    
    soup = BeautifulSoup(response.content, 'html.parser') if response.status_code == 200 \
        else print(f"Error: received status code {response.status_code}")
    

    btns = soup.find('ul', class_="menu")
    menu = find_menu(btns)
    
    set_driver()
    rlt = []
    for idx, key in enumerate(menu):
        path = menu[key]
        url = domain+path
        x = search_info(driver, url, idx)
        
        rlt.append(x)
        if key == "기업개요":
            break # 23.05.15 - 추후 논의 후 추가 개발 예정
    driver.quit()
    
    return rlt


def search_info(driver, url : str, idx):
    
    obj = dict()
    
    scraped_data = scrape_catch(driver, url, idx)
    
    for idx, data in enumerate(scraped_data):
        obj[f'table-{idx}'] = data

    return obj

def find_domain(url : str) -> str:
    parsed_url = urlparse(url)
    domain = f"{parsed_url.scheme}://{parsed_url.netloc}"
    return domain

def find_menu(html_: str) -> dict:
    menu_items = html_.find_all('li')

    menu_dict = {}

    for item in menu_items:
        link = item.find('a')['href']
        name = item.find('img')['alt']
        menu_dict[name] = link
    return menu_dict



# url='https://www.catch.co.kr/Comp/CompSummary/C80086'
# url='https://www.catch.co.kr/Comp/CompSummary/112745'
# search_at_CATCH(url)


def main(target):
    results = search_at_CATCH(target)
    for result in results:
        print(json.dumps(result)) 


# url='https://www.catch.co.kr/Comp/CompSummary/380954'
# print(search_at_CATCH(url))
if __name__ == "__main__":
    import sys
    import io

    sys.stdout = io.TextIOWrapper(sys.stdout.detach(), encoding = 'utf-8')
    sys.stderr = io.TextIOWrapper(sys.stderr.detach(), encoding = 'utf-8')
    target = sys.argv[1] 
    main(target)