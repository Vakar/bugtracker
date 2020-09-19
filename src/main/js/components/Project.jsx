import React, { useContext, useEffect, useState } from "react";
import axios from "axios";
import BugForm from "./BugForm";
import BugList from "./BugList";

import { AppContext } from "../context/AppContext";
import { setContextProjects } from "../context/actions";
import DeleteButton from "./elements/DeleteButton";

export default function Project(props) {
  const { context, dispatch } = useContext(AppContext);

  const [project, setProject] = useState(null);
  const [bugs, setBugs] = useState([]);
  const { handle } = props.match.params;

  useEffect(() => {
    axios
      .get(`../users/1/projects/${handle}`)
      .then((res) => {
        setProject(res.data);
        setBugs(res.data.bugs);
      })
      .catch(() =>
        console.error(`Can't get information about project with id ${handle}`)
      );
  }, [props.match.params.handle]);

  const deleteProject = () => {
    axios.delete(`../users/1/projects/${handle}`).then(() => {
      const filtered = context.projects.filter((p) => p.id !== Number(handle));
      dispatch(setContextProjects([...filtered]));
      props.history.push("/");
    });
  };

  return !project ? (
    <section about="project data" />
  ) : (
    <section about="project data">
      <DeleteButton deleteFn={deleteProject} />
      <h1 className="card-title mt-4">{project.title}</h1>
      <p>{project.description}</p>
      <BugForm project={project} bugs={[bugs, setBugs]} />
      <BugList bugs={[bugs, setBugs]} />
    </section>
  );
}
