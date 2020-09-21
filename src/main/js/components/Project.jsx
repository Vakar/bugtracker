import React, { useContext, useEffect, useState } from "react";
import BugForm from "./BugForm";
import BugList from "./BugList";

import { ROUTE_HOME } from "../reactRouts";
import { projectsDelete, projectsFind } from "../restClient";

import { AppContext } from "../context/AppContext";
import { setContextProjects } from "../context/actions";
import DeleteButton from "./elements/DeleteButton";

export default function Project(props) {
  const { context, dispatch } = useContext(AppContext);

  const [project, setProject] = useState(null);
  const [bugs, setBugs] = useState([]);
  const { handle } = props.match.params;

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
    <section about="project data" />
  ) : (
    <section about="project data">
      <DeleteButton deleteFn={deleteProject} />
      <h1 className="card-title mt-4">{project.title}</h1>
      <p>{project.description}</p>
      {/*  todo: DRY same properties*/}
      <BugForm
        bugs={[bugs, setBugs]}
        userId={context.user.id}
        projectId={handle}
      />
      <BugList
        bugs={[bugs, setBugs]}
        userId={context.user.id}
        projectId={handle}
      />
    </section>
  );
}
