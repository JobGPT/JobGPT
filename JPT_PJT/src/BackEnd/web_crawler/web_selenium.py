from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.wait import WebDriverWait
from bs4 import BeautifulSoup
import time

def search_by_selenium(driver, query : str|list[str], num_results : int) -> str | list[str]:
    print('Crawling start with selenium')
    
    from .driver_update import chromedriver_update
    chromedriver_update()
    
    if isinstance(query, str):       # 구글 검색 실패로 우회해서 들어올 경우 타겟 문구인 query가 들어옴
        '''
        우회하여 검색하는 코드는 아직 완성되지 않았습니다.
        현재 작업 진행 중.
        '''
        # # 검색 결과를 추출하여 리스트 형태로 반환합니다.
        # search_results = [result.get_attribute("href") for result in driver.find_elements_by_xpath("//a[@href]")][:num_results]
        
        # # 검색 결과가 하나도 없는 경우 에러 메시지를 반환합니다.
        # if not search_results:
        #     return "Error: No search results were found."
        
        # # 검색 결과가 하나인 경우 해당 결과를 반환합니다.
        # elif len(search_results) == 1:
        #     return search_results[0]
        
        # # 검색 결과가 여러 개인 경우 검색 결과 리스트를 반환합니다.
        # else:
        #     return search_results
        
        raise f'Issue : have to make a code to bypass and access'
    else : #isinstance(query, list)  # 구글 검색 성공시, list 안에 url이 담겨서 query가 들어옴
        for url in query:
            driver.get(url=url)
            WebDriverWait(driver, 10).until(
                EC.presence_of_element_located((By.TAG_NAME, "body"))
            )
            html_source = driver.execute_script("return document.body.outerHTML;")