import React, { useContext, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { AppContext } from "../context/AppContext";
import axios from "axios";
import { setContextProjects} from "../context/actions";

export default function Navigation(props) {
  const { context, dispatch } = useContext(AppContext);
  const [projects, setProjects] = useState([]);

  // When context user changes, gets user projects and save them to context.
  useEffect(() => {
    if (context.user.id !== 0) {
      axios
        .get(`/users/${context.user.id}/projects`)
        .then((res) => dispatch(setContextProjects(res.data))).catch(() => console.error("Can't get list of projects!"));
    }
  }, [context.user]);

  useEffect(() => {
    setProjects(context.projects);
  }, [context.projects]);

  if (props.user.id === 0) {
    return null;
  } else {
    return (
      <React.Fragment>
        <button
          className="navbar-toggler"
          type="button"
          data-toggle="collapse"
          data-target="#navbarResponsive"
          aria-controls="navbarResponsive"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon" />
        </button>
        <div className="collapse navbar-collapse" id="navbarResponsive">
          <ul className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link className="nav-link" to="/">
                Home
              </Link>
            </li>
            <li className="nav-item dropdown">
              <a
                className="nav-link dropdown-toggle"
                href="#"
                id="navbarDropdown"
                role="button"
                data-toggle="dropdown"
                aria-haspopup="true"
                aria-expanded="false"
              >
                Projects
                <span className="badge badge-light ml-1">
                  {projects.length}
                </span>
              </a>
              <div className="dropdown-menu" aria-labelledby="navbarDropdown">
                {projects.map((p, index) => {
                  return (
                    <Link
                      key={index}
                      className="dropdown-item"
                      to={`/project/${p.id}`}
                    >
                      {p.title}
                    </Link>
                  );
                })}
              </div>
            </li>
          </ul>
        </div>
      </React.Fragment>
    );
  }
}
