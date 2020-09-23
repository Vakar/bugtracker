import React, { useRef, useState } from "react";
import { bugsSave } from "../restClient";

export default function BugForm(props) {
  const describeNewBugButton = useRef(null);
  const [title, setTitle] = useState("");
  const [expectedResults, setExpectedResults] = useState("");
  const [actualResults, setActualResults] = useState("");
  const [stepsToReproduce, setStepsToReproduce] = useState("");

  const { projectId, userId } = props;
  const [bugs, setBugs] = props.bugs;

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
    clickDescribeNewBugButton();
    cleanForm();
  }

  function clickDescribeNewBugButton() {
    describeNewBugButton.current.click();
  }

  function cleanForm() {
    setTitle("");
    setExpectedResults("");
    setActualResults("");
    setStepsToReproduce("");
  }

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
              ref={describeNewBugButton}
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
