import React, { useContext, useState } from "react";

import { AppContext } from "../context/AppContext";
import { useHistory } from "react-router-dom";

import axios from "axios";
import { setContextProjects } from "../context/actions";

export default function ProjectForm() {
  const history = useHistory();
  const { context, dispatch } = useContext(AppContext);
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");

  const onSubmit = (e) => {
    axios
      .post(`http://localhost:8080/users/${context.user.id}/projects`, {
        title: title,
        description: description,
      })
      .then((res) => {
        dispatch(setContextProjects([...context.projects, res.data]));
        history.push(`/project/${res.data.id}`);
      }).catch(() => console.error("Can't save project!"));
    e.preventDefault();
  };

  return (
    <React.Fragment>
      <h1 className="mt-4">Create new project form</h1>
      <form onSubmit={onSubmit}>
        <div className="form-group">
          <label htmlFor="title">Project title</label>
          <input
            type="text"
            className="form-control"
            id="title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            maxLength="255"
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="expectedResults">Project description</label>
          <textarea
            className="form-control"
            id="description"
            rows="3"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            maxLength="1023"
            required
          />
        </div>
        <button type="submit" className="btn btn-secondary">
          Submit
        </button>
      </form>
    </React.Fragment>
  );
}
