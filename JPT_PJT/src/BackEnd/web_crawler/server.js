import express from 'express';
import bodyParser from 'body-parser';
import path from 'path';
import { spawn } from 'child_process';
import { fileURLToPath } from 'url';

import  {
  fetchCorpCode,
  fetchDisclosInform,
  fetchDocumentData
} from './api/Dart/Dart/index.js'

import  {
  get_summery,
} from './api/Catch/Catch/index.js'

import { mysql_db, dbQuery } from './src/confing/database.js'

class Server{
  constructor(){
    this.app = express();
    this.configureMiddleware();
    this.setupRoutes();
  }

  configureMiddleware(){
    this.app.use(bodyParser.json());
  }

  setupRoutes(){
    this.app.get('/', (req, res) => this.handleRoot(req, res));
    this.app.post('/search', (req, res) => this.handleTarget(req, res));
    this.app.post('/catch', (req, res) => this.handleCompanyName(req, res)); 
    this.app.get('/catch/:comp', (req,res) => this.get_companyInfo(req, res));
  }

  handleCompanyName(req, res){
    const companyName = req.body.comp; // POST 요청의 본문에서 회사 이름을 가져옵니다.
    if (!companyName) {
      res.status(400).send('Missing company name in request body.');
      return;
    }
    res.redirect(`/catch/${companyName}`); // /catch/:comp 경로로 리다이렉트합니다.
  }

  async get_companyInfo(req, res){
    const name = req.params.comp;
    let data_form = {
      'summery' : "입력한 회사의 정보가 없습니다.",
      'detail' : "입력한 회사의 정보가 없습니다."
    }
    if (!name) {
      res.status(400).send('Missing company name.');
      return;
    }

    try {
      // Catch 채용정보 분석
      const {CompName, catch_inform} = await this.CatchData(name)
      data_form.summery = catch_inform
      
      // DB에 정보 저장.
      dbQuery("INSERT INTO companyInfo (companyName, compInfo) VALUES (?, ?)", 
      [CompName._text, JSON.stringify(catch_inform.company_overview)])
      .then(result => {
          console.log("Data inserted successfully");
      })
      .catch(error => {
          console.error("Error inserting data: ", error);
      });

      // DART 회사정보 분석
      const detail = await this.DartData(CompName._text)
      data_form.detail = detail
      
      res.json(data_form);

    } catch (error) {
      console.error(error);
      res.status(500).send(error.message);
    }
  }

  async CatchData(name){
    const {Data} = await this.getCompanyFromCatch(name);
    const {CompName, SummaryURL} = Data.Companys.Company[0];
    const data = await this.executePythonScript('web_beautifulsoup.py', SummaryURL._text);
    // 0. 채용공고, 1. 기업개요, 2. 재무평가, 3. 현직자리뷰, 4. 면접후기
    const catch_inform = {
      'job_Posting' : data[0],
      'company_overview' : data[1]
    }
    return { CompName, catch_inform }
  }

  async DartData(name){
    let detail = "Dart에 회사 정보가 없습니다."
    const corp_referance = await fetchCorpCode({target : name})

    if (corp_referance){
      const {corp_code} = corp_referance
      const corp_data = await fetchDisclosInform({corp_code : corp_code._text})
      let businessReports = corp_data.filter(item => item.report_nm.includes("분기보고서"))[0];

      if (businessReports){
        console.log(`${businessReports.corp_name}의 ${businessReports.report_nm}을 탐색합니다`)
      }

      const dart_inform = await fetchDocumentData({rcept_no : businessReports.rcept_no})
      
      detail = dart_inform ? dart_inform : "찾은 정보가 없습니다."
    }

    return detail
  }

  async getCompanyFromCatch(name) {
    return await get_summery(name);
  }

  async executePythonScript(scriptName, target) {
    try {
      return await openPython(scriptName, target);
    } catch (error) {
      console.error(error);
      throw new Error('Error occurred while executing Python script');
    }
  }

  handleRoot(req, res) {
    console.log('hello_world');
    res.send('Hello_world');
  }

  handleTarget(req, res) {
    const { target, service_code } = req.body;
    // 절대경로로 module을 찾음
    const currentModulePath = fileURLToPath(import.meta.url);
    const currentModuleDir = path.dirname(currentModulePath);
    
    const addScriptPath = path.join(currentModuleDir, 'py_module', 'module.py');
    const pythonProcess = spawn('python', [addScriptPath, target], { encoding : 'utf8' });

    pythonProcess.stdout.on('data', (data) => {
      const URLs = data.toString();
      res.json({ URLs });
    });

    pythonProcess.stderr.on('data', (data) => {
      console.error(`stderr: ${data}`);
      res.status(500).send('Error occurred while executing Python script');
    });

    pythonProcess.on('close', (code) => {
      console.log(`Python process exited with code ${code}`);
    });
  }

  start(port = 3000) {
    const PORT = process.env.PORT || port;
    this.app.listen(PORT, () => {
      console.log(`Server started on port ${PORT}`);
    });
  }
}

// 서버 인스턴스를 생성하고 시작합니다.
const server = new Server();
server.start();


// 아래는 Server를 구동하기위한 Promise 함수들 입니다.


async function openPython(module_name, target){
  
  return new Promise((resolve, reject) => {

    const currentModulePath = fileURLToPath(import.meta.url);
    const currentModuleDir = path.dirname(currentModulePath);
    const addScriptPath = path.join(currentModuleDir, 'py_module', module_name);

    // const pythonInterpreterPath = path.join(currentModuleDir, '..', '..', '..', '..', 'venv', 'Scripts', 'python');
    const pythonProcess = spawn('python', [addScriptPath, target], { encoding : 'utf8' });

    let output = '';

    pythonProcess.stdout.on('data', (data) => {
      output += data.toString();
    });

    pythonProcess.stderr.on('data', (data) => {
      console.error(`stderr: ${data}`);
      reject('Error occurred while executing Python script');
    });

    pythonProcess.on('close', (code) => {
      console.log(`Python process exited with code ${code}`);

      const results = output.split('\n')  // 출력을 줄 단위로 분할합니다.
        .filter(line => line)  // 빈 줄을 제거합니다.
        .map(line => {
          try {
            return JSON.parse(line);  // 각 줄을 JSON으로 변환합니다.
          } catch (error) {
            console.error(`Failed to parse line as JSON: ${line}`);
            return null;
          }
        })
        .filter(obj => obj);  // JSON 변환에 실패한 줄을 제거합니다.

      resolve(results);
    });
  });
}