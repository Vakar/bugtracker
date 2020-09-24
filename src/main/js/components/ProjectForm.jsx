import React, {useContext, useState} from "react";
import {useHistory} from "react-router-dom";
import {Button, Form, FormGroup, Input, Label} from "reactstrap";

import {AppContext} from "../context/AppContext";
import {projectsSave} from "../restClient";
import {routeProjectWithId} from "../reactRouts";
import {setContextProjects} from "../context/actions";

export default function ProjectForm() {
  const history = useHistory();
  const {context, dispatch} = useContext(AppContext);
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");

  const onSubmit = (e) => {
    const project = {
      title: title,
      description: description,
    };
    projectsSave(context.user.id, project, saveProjectCallback);
    e.preventDefault();
  };

  function saveProjectCallback(project) {
    dispatch(setContextProjects([...context.projects, project]));
    history.push(routeProjectWithId(project.id));
  }

  return (
      <React.Fragment>
        <h1 className="mt-4">Create new project form</h1>
        <Form onSubmit={onSubmit}>
          <FormGroup>
            <Label for="title">Project title</Label>
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
            <Label for="description">Project description</Label>
            <Input
                type="textarea"
                name="description"
                id="description"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                maxLength="1023"
                required
            />
          </FormGroup>
          <Button type="submit">Submit</Button>
        </Form>
      </React.Fragment>
  );
}
