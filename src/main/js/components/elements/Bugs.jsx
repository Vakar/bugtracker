import React, { useState } from "react";
import { bugsSave } from "../../restClient";
import {
  Badge,
  Button,
  Collapse,
  Form,
  FormGroup,
  Input,
  Label,
  ListGroup,
  ListGroupItem,
} from "reactstrap";
import Bug from "./Bug";

export default function Bugs(props) {
  const [title, setTitle] = useState("");
  const [expectedResults, setExpectedResults] = useState("");
  const [actualResults, setActualResults] = useState("");
  const [stepsToReproduce, setStepsToReproduce] = useState("");

  const { projectId, userId } = props;
  const [bugs, setBugs] = props.bugs;

  const [isOpen, setIsOpen] = useState(false);
  const toggle = () => setIsOpen(!isOpen);

  const onSubmit = (event) => {
    const bug = {
      title: title,
      expectedResults: expectedResults,
      actualResults: actualResults,
      stepsToReproduce: stepsToReproduce,
      fixStatus: "NEW",
    };
    bugsSave(userId, projectId, bug, bugSaveCallback);
    event.preventDefault();
  };

  function bugSaveCallback(bug) {
    setBugs([...bugs, bug]);
    toggle();
    cleanForm();
  }

  function cleanForm() {
    setTitle("");
    setExpectedResults("");
    setActualResults("");
    setStepsToReproduce("");
  }

  return (
    <React.Fragment>
      <div about="bug form">
        <ListGroupItem
          color="dark"
          className="justify-content-between d-flex align-items-center"
        >
          <span className="h4">
            Bugs <Badge pill>{bugs.length}</Badge>
          </span>
          <Button color="success" onClick={toggle}>
            Describe new bug
          </Button>
        </ListGroupItem>

        <Collapse isOpen={isOpen}>
          <Form onSubmit={onSubmit} className="m-5">
            <FormGroup>
              <Label for="title">Bug title</Label>
              <Input
                type="text"
                name="title"
                id="title"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                maxLength="255"
                required
              />
            </FormGroup>
            <FormGroup>
              <Label for="expectedResults">Expected results</Label>
              <Input
                type="textarea"
                name="expectedResults"
                id="expectedResults"
                value={expectedResults}
                onChange={(e) => setExpectedResults(e.target.value)}
                maxLength="1023"
                required
              />
            </FormGroup>
            <FormGroup>
              <Label for="actualResults">Actual results</Label>
              <Input
                type="textarea"
                name="actualResults"
                id="actualResults"
                value={actualResults}
                onChange={(e) => setActualResults(e.target.value)}
                maxLength="1023"
                required
              />
            </FormGroup>
            <FormGroup>
              <Label for="stepsToReproduce">Steps to reproduce</Label>
              <Input
                type="textarea"
                name="stepsToReproduce"
                id="stepsToReproduce"
                value={stepsToReproduce}
                onChange={(event) => setStepsToReproduce(event.target.value)}
                maxLength="1023"
                required
              />
            </FormGroup>
            <Button type="submit">Submit</Button>
          </Form>
        </Collapse>
      </div>
      <div about="bug list">
        <ListGroup className="mt-3">
          {bugs.map((bug, index) => {
            return (
              <Bug
                key={index}
                bug={bug}
                bugs={props.bugs}
                userId={userId}
                projectId={projectId}
              />
            );
          })}
        </ListGroup>
      </div>
    </React.Fragment>
  );
}
