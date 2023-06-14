import Button from 'react-bootstrap/Button';

import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';

import plus from "../assets/plus.svg"

function NewChatBtn() {
  return <div className="NewChatBtn w-100 me-4">
    <Container>
      <Row>
        <Button variant="outline-dark">
          <div className="d-flex p-1">
            <img src={plus} alt="새채팅생성버튼" />
            <div className="mx-2">New chat</div>
          </div>
        </Button>
      </Row>
    </Container>
  </div>;
}

export default NewChatBtn;
