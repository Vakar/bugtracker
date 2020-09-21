import React, { useContext, useEffect, useState } from "react";
import User from "./User";
import Navigation from "./Navigation";
import { AppContext, DEFAULT_USER } from "../context/AppContext";
import { setContextUser } from "../context/actions";
import { authorizedUser } from "../restClient";

export default function Header() {
  const { context, dispatch } = useContext(AppContext);
  const [user, setUser] = useState(DEFAULT_USER);

  useEffect(() => authorizedUser(userGetCallback), []);
  const userGetCallback = (appUser) => dispatch(setContextUser(appUser));
  useEffect(() => setUser(context.user), [context.user]);

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark static-top">
      <div className="container">
        <User user={user} />
        <Navigation user={user} />
      </div>
    </nav>
  );
}
