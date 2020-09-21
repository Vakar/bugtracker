import React from "react";
import DeleteButton from "./elements/DeleteButton";
import {bugsDelete} from "../restClient";

export default function Bug(props) {
    const [bugs, setBugs] = props.bugs;
    const {bug, userId, projectId} = props;

    const deleteBug = () => bugsDelete(userId, projectId, bug.id, callback);
    const callback = () => setBugs(bugs.filter((b) => b.id !== bug.id));

    return (
        <React.Fragment>
            <a
                href="#"
                className="list-group-item list-group-item-action list-group-item d-flex justify-content-between align-items-center"
                data-toggle="collapse"
                data-target={`#bugDetailsCollapse-${bug.id}`}
                aria-expanded="false"
                aria-controls={`bugDetailsCollapse-${bug.id}`}
            >
                <h5 className="mb-1">{bug.title}</h5>
            </a>
            <li
                className="list-group-item collapse"
                id={`bugDetailsCollapse-${bug.id}`}
            >
                <h5 className="mb-1">Expected results:</h5>
                <p className="pl-4">{bug.expectedResults}</p>
                <h5 className="mb-1">Actual results:</h5>
                <p className="pl-4">{bug.actualResults}</p>
                <h5 className="mb-1">Steps to reproduce:</h5>
                <p className="pl-4">{bug.stepsToReproduce}</p>
                <DeleteButton deleteFn={deleteBug}/>
            </li>
        </React.Fragment>
    );
}
