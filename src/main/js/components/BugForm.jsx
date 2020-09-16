import React, { useRef, useState } from "react";
import axios from "axios";

export default function BugForm(props) {
  const describeNewBug = useRef(null);

  const [bugs, setBugs] = props.bugs;
  const project = props.project;

  const [title, setTitle] = useState("");
  const [expectedResults, setExpectedResults] = useState("");
  const [actualResults, setActualResults] = useState("");
  const [stepsToReproduce, setStepsToReproduce] = useState("");

  const onSubmit = (event) => {
    axios
      .post(
        `http://localhost:8080/users/${project.owner.id}/projects/${project.id}/bugs`,
        {
          title: title,
          expectedResults: expectedResults,
          actualResults: actualResults,
          stepsToReproduce: stepsToReproduce,
          fixStatus: "NEW",
        }
      )
      .then((res) => {
        setBugs([...bugs, res.data]);
        describeNewBug.current.click();
        setTitle("");
        setExpectedResults("");
        setActualResults("");
        setStepsToReproduce("");
      });
    event.preventDefault();
  };

  return (
    <React.Fragment>
      <ul className="list-group">
        <li className="list-group-item list-group-item-dark d-flex justify-content-between align-items-center bg-light">
          <span className="h4">
            Bugs{" "}
            <span className="badge badge-secondary badge-pill">
              {bugs.length}
            </span>
          </span>
          <div
            className="btn-group"
            role="group"
            aria-label="bug control panel"
          >
            <button
              type="button"
              className="btn btn-success"
              ref={describeNewBug}
              data-toggle="collapse"
              data-target="#describeNewBugFrom"
              aria-expanded="true"
              aria-controls="describeNewBugFrom"
            >
              Describe new bug
            </button>
          </div>
        </li>
      </ul>
      <div className="collapse" id="describeNewBugFrom">
        <div className="card-body">
          <form onSubmit={onSubmit}>
            <div className="form-group">
              <label htmlFor="title">Bug title</label>
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
              <label htmlFor="expectedResults">Expected results</label>
              <textarea
                className="form-control"
                id="expectedResults"
                rows="3"
                value={expectedResults}
                onChange={(e) => setExpectedResults(e.target.value)}
                maxLength="1023"
                required
              />
            </div>
            <div className="form-group">
              <label htmlFor="actualResults">Actual results</label>
              <textarea
                className="form-control"
                id="actualResults"
                rows="3"
                value={actualResults}
                onChange={(e) => setActualResults(e.target.value)}
                maxLength="1023"
                required
              />
            </div>
            <div className="form-group">
              <label htmlFor="stepsToReproduce">Steps to reproduce</label>
              <textarea
                className="form-control"
                id="stepsToReproduce"
                rows="3"
                value={stepsToReproduce}
                onChange={(event) => setStepsToReproduce(event.target.value)}
                maxLength="1023"
                required
              />
            </div>
            <button type="submit" className="btn btn-info">
              Submit
            </button>
          </form>
        </div>
      </div>
    </React.Fragment>
  );
}
