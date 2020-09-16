import React, { useContext, useEffect, useState } from "react";
import axios from "axios";
import BugForm from "./BugForm";
import BugList from "./BugList";

import { AppContext } from "../context/AppContext";
import { setProjects } from "../context/actions";
import DeleteButton from "./bootstrap/DeleteButton";

function Project(props) {
  const { state, dispatch } = useContext(AppContext);

  const [project, setProject] = useState(null);
  const [bugs, setBugs] = useState([]);
  const { handle } = props.match.params;

  useEffect(() => {
    axios.get(`../users/1/projects/${handle}`).then((res) => {
      setProject(res.data);
      setBugs(res.data.bugs);
    });
  }, [props.match.params.handle]);

  const deleteProject = () => {
    axios.delete(`../users/1/projects/${handle}`).then(() => {
      const filtered = state.projects.filter((p) => p.id !== handle);
      dispatch(setProjects(filtered));
      props.history.push("/");
    });
  };

  if (project) {
    return (
      <section about="project data">
        <DeleteButton deleteFn={deleteProject} />
        <h1 className="card-title mt-4">{project.title}</h1>
        <p>{project.description}</p>
        <BugForm project={project} bugs={[bugs, setBugs]} />
        <BugList bugs={[bugs, setBugs]} />
      </section>
    );
  }

  return <section about="project data" />;
}

export default Project;
