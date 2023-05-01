from selenium import webdriver
from selenium.webdriver.common.by import By
import json
import os
import chromedriver_autoinstaller as AutoChrome
import shutil
import time 

def chromedriver_update():
    chrome_ver = AutoChrome.get_chrome_version().split('.')[0]

    current_list = os.listdir(os.getcwd()) 			        # 현재 경로의 모든 객체들
    chrome_list = []
    for i in current_list:
        path = os.path.join(os.getcwd(), i) 			    # 현재 경로의 모든객체의 전체경로
        if os.path.isdir(path): 				            # 그 경로가 폴더인지 확인
            if 'chromedriver.exe' in os.listdir(path):      # 폴더면 안에 chromedriver.exe가 있는지 확인
                chrome_list.append(i) 				        # 있는경우 chrome_list에 추가

    old_version = list(set(chrome_list)-set([chrome_ver])) 	# 그중에 최신버전은 제외

    for i in old_version:
        path = os.path.join(os.getcwd(),i) 			        # 구버전이 있는 폴더의 경로 
        shutil.rmtree(path) 					            # 그 경로 삭제

    if not chrome_ver in current_list: 				        # 최신버전 폴더가 현재 경로에 없으면
        AutoChrome.install(True) 				            # 크롬드라이버 설치
    else : pass 						                    # 아니면 무시

def open_browser():
    global browser
    url = "https://edu.ssafy.com/"

    options = webdriver.ChromeOptions()
    options.add_experimental_option("excludeSwitches", ["enable-logging"])

    driver = webdriver.Chrome(executable_path='chromedriver', options=options)
    driver.get(url=url)
    browser = driver
    # time.sleep(10)


browser = None
chromedriver_update()
open_browser()