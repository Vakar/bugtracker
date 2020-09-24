import React, {useContext, useEffect, useState} from "react";
import {Button} from "reactstrap";

import Bugs from "./elements/Bugs";
import {ROUTE_HOME} from "../reactRouts";
import {projectsDelete, projectsFind} from "../restClient";
import {AppContext} from "../context/AppContext";
import {setContextProjects} from "../context/actions";

export default function Project(props) {
    const {context, dispatch} = useContext(AppContext);
    const [project, setProject] = useState(null);
    const [bugs, setBugs] = useState([]);
    const {handle} = props.match.params;

    useEffect(() => projectsFind(context.user.id, handle, projectGetCallback), [
        props.match.params.handle,
    ]);

    function projectGetCallback(proj) {
        setProject(proj);
        setBugs(proj.bugs);
    }

    const deleteProject = () =>
        projectsDelete(context.user.id, handle, projectDeleteCallback);

    function projectDeleteCallback() {
        const filtered = context.projects.filter((p) => p.id !== Number(handle));
        dispatch(setContextProjects([...filtered]));
        props.history.push(ROUTE_HOME);
    }

    return !project ? (
        <section about="project data"/>
    ) : (
        <section about="project data">
            <Button
                color="secondary"
                size="sm"
                className="float-right"
                onClick={deleteProject}
            >
                <i className="fa fa-trash" aria-hidden="true"></i>
            </Button>
            <h1 className="card-title mt-4">{project.title}</h1>
            <p>{project.description}</p>
            <Bugs
                bugs={[bugs, setBugs]}
                userId={context.user.id}
                projectId={handle}
            />
        </section>
    );
}
