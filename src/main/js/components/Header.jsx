import React, { useContext, useEffect, useState } from "react";
import User from "./User";
import Navigation from "./Navigation";
import { AppContext, DEFAULT_USER } from "../context/AppContext";
import axios from "axios";
import { setContextUser } from "../context/actions";

export default function Header() {
  const { context, dispatch } = useContext(AppContext);
  const [user, setUser] = useState(DEFAULT_USER);

  // Try to get authorized user data.
  useEffect(() => {
    axios
      .get(`/user`)
      .then((res) => dispatch(setContextUser(res.data)))
      .catch(() => console.warn("You are not authorized!"));
  }, []);

  useEffect(() => {
    setUser(context.user);
  }, [context.user]);

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark static-top">
      <div className="container">
        <User user={user} />
        <Navigation user={user} />
      </div>
    </nav>
  );
}
