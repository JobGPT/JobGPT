from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.wait import WebDriverWait


def search_by_selenium(driver, query : str, num_results : int) -> str | list[str]:
    print('Crawling start with selenium')
    
    from .driver_update import chromedriver_update
    chromedriver_update()
    
    url = "https://edu.ssafy.com/"
    
    
    driver.get(url=url)
    WebDriverWait(driver, 10).until(
        EC.presence_of_element_located((By.TAG_NAME, "body"))
    )
    
    # 검색 결과를 추출하여 리스트 형태로 반환합니다.
    search_results = [result.get_attribute("href") for result in driver.find_elements_by_xpath("//a[@href]")][:num_results]
    
    # 검색 결과가 하나도 없는 경우 에러 메시지를 반환합니다.
    if not search_results:
        return "Error: No search results were found."
    
    # 검색 결과가 하나인 경우 해당 결과를 반환합니다.
    elif len(search_results) == 1:
        return search_results[0]
    
    # 검색 결과가 여러 개인 경우 검색 결과 리스트를 반환합니다.
    else:
        return search_results
    