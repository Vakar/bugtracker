import React, { useContext } from "react";
import { useHistory } from "react-router-dom";
import { AppContext, DEFAULT_USER } from "../context/AppContext";
import axios from "axios";
import { setContextUser } from "../context/actions";

export default function User(props) {
  const history = useHistory();
  const { dispatch } = useContext(AppContext);

  function handleLogout() {
    axios.post("http://localhost:8080/logout").then(() => {
      dispatch(setContextUser(DEFAULT_USER));
      history.push("/");
    }).catch(() => console.error("Can't logout properly!"));
  }

  return props.user.id === 0 ? (
    <span className="navbar-brand ml-2">Bugtracker</span>
  ) : (
    <React.Fragment>
      <button type="button" className="btn btn-dark" onClick={handleLogout}>
        <i className="fa fa-power-off" aria-hidden="true" />
      </button>
      <span className="navbar-brand ml-2">{props.user.name}</span>
    </React.Fragment>
  );
}
