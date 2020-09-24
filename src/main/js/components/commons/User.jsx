import React, { useContext } from "react";
import { useHistory } from "react-router-dom";
import { AppContext, DEFAULT_USER } from "../../context/AppContext";
import { setContextUser } from "../../context/actions";
import { ROUTE_HOME } from "../../reactRouts";
import { logout } from "../../restClient";
import { Button } from "reactstrap";

export default function User(props) {
  const history = useHistory();
  const { dispatch } = useContext(AppContext);

  function handleLogout() {
    logout(callback);
  }

  function callback() {
    dispatch(setContextUser(DEFAULT_USER));
    history.push(ROUTE_HOME);
  }

  return (
    <React.Fragment>
      <Button color="dark" onClick={handleLogout}>
        <i className="fa fa-power-off" aria-hidden="true" />
      </Button>
      <span className="navbar-brand ml-2">{props.user.name}</span>
    </React.Fragment>
  );
}
