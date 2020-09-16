import React, { useContext, useEffect } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

import { AppContext } from "../context/AppContext";
import { setProjects } from "../context/actions";

export default function Navigation() {
  const { state, dispatch } = useContext(AppContext);

  useEffect(() => {
    axios
      .get(`/users/${state.user.id}/projects`)
      .then((res) => dispatch(setProjects(res.data)));
  }, [state.user.id, state.projects]);

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark static-top">
      <div className="container">
        <img
          src="https://avatars0.githubusercontent.com/u/12372533?v=4"
          alt="avatar"
          style={{ height: "36px" }}
          className="rounded-circle"
        />
        <span className="navbar-brand ml-2">{state.user.name}</span>
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
                Projects{" "}
                <span className="badge badge-light">
                  {state.projects.length}
                </span>
              </a>
              <div className="dropdown-menu" aria-labelledby="navbarDropdown">
                {state.projects.map((p, index) => {
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
      </div>
    </nav>
  );
}
