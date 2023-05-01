from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.wait import WebDriverWait
from .web_selenium import *

import json
# 해당 객체를 불러올 때 수행해함

class WebCrawler:
    
    options = None
    
    def __init__(self, target : str) -> None:
        
        if not WebCrawler.options:
            WebCrawler.options = webdriver.ChromeOptions()
            WebCrawler.options.add_experimental_option("excludeSwitches", ["enable-logging"])
        
        self.driver = webdriver.Chrome(
            executable_path='chromedriver', 
            options=WebCrawler.options
            )
        
        self.browser = None
        self.target = target
        
        
    def google_official_search(self, num_results: int = 8) -> str | list[str]:
        """Return the results of a Google search using the official Google API
        Args:
            query (str): The search query.
            num_results (int): The number of results to return.
        Returns:
            str: The results of the search.
        """
        from googleapiclient.discovery import build
        from googleapiclient.errors import HttpError
        query = self.target
        try:
            # 구글 search api 키와 Customsearch 엔진 id를 받아온다.
            api_key = 'AIzaSyBlNSqg-9WQraWKrdqLdMqh-L0umq_73eA'  # api key 는 나중에 config로 변경해줘야함
            custom_search_engine_id = '801d58a0c2a1a418b'        # custom_search_engine_id 역시 config에서 불러와야함
            
            # Initialize the Custom Search API service
            service = build("customsearch", "v1", developerKey=api_key)
            
            # Send the search query and retrieve the results
            result = (
                service.cse()
                .list(q=query, cx=custom_search_engine_id, num=num_results)
                .execute()
            )
            
            # 검색 결과
            search_results = result.get("items", [])
            
            # 검색 결과에서 링크만 추출
            search_results_links = [item["link"] for item in search_results]
            
        except HttpError as e:
            # API 호출에 에러가 뜰 경우, selenium으로 우회하며 검색
            error_details = json.loads(e.content.decode())
            
            # Check if the error is related to an invalid or missing API key
            if error_details.get("error", {}).get(
                "code"
            ) == 403 and "invalid API key" in error_details.get("error", {}).get(
                "message", ""
            ):
                print("Error: The provided Google API key is invalid or missing.")
            else:
                print(f"Error: {e}")
                
            return search_by_selenium(self.driver, query, num_results)
            
        # google_result can be a list or a string depending on the search results
        # Return the list of search result URLs
        return self.safe_google_results(search_results_links)

    def safe_google_results(self, results: str | list) -> str:
        """
            Return the results of a google search in a safe format.
        Args:
            results (str | list): The search results.
        Returns:
            str: The results of the search.
        """
        if isinstance(results, list):
            
            # results 리스트 내의 바이트 타입을 문자열 타입으로 변환합니다.
            encoded_results = [result if isinstance(result, str) else str(result) for result in results]
            
            # 인코딩된 문자열들을 Json 형태로 직렬화합니다.
            safe_message = json.dumps(
                encoded_results,
                ensure_ascii=False,
            )
            return safe_message
        else:  
            # "이거 무슨형태로 나오는지 봐야함"
            safe_message = results.encode("utf-8", "ignore").decode("utf-8")
            return safe_message
        
    def close_browser(self) -> None:
        self.driver.quit()

crawl = WebCrawler(target='안녕')
get_data = crawl.google_official_search()
print(get_data)