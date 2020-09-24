import React, {useContext, useEffect, useState} from "react";
import {AppContext, DEFAULT_USER} from "../../context/AppContext";
import {setContextUser} from "../../context/actions";
import {authorizedUser} from "../../restClient";
import {Collapse, Navbar, NavbarToggler} from "reactstrap";
import User from "./User";
import Navigation from "./Navigation";
import Login from "./Login";

export default function Header() {
    const {context, dispatch} = useContext(AppContext);
    const [user, setUser] = useState(DEFAULT_USER);

    useEffect(() => authorizedUser(userGetCallback), []);
    const userGetCallback = (appUser) => dispatch(setContextUser(appUser));
    useEffect(() => setUser(context.user), [context.user]);

    const [isOpen, setIsOpen] = useState(false);
    const toggle = () => setIsOpen(!isOpen);

    if (user.id > 0) {
        return (
            <Navbar color="dark" dark expand="lg">
                <div className="container">
                    <User user={user}/>
                    <NavbarToggler onClick={toggle}/>
                    <Collapse isOpen={isOpen} navbar>
                        <Navigation user={user}/>
                    </Collapse>
                </div>
            </Navbar>
        );
    } else {
        return (
            <Navbar color="dark" dark expand="lg">
                <div className="container">
                    <span className="navbar-brand ml-2">Bugtracker</span>
                    <span className="ml-auto">
            <Login user={user}/>
          </span>
                </div>
            </Navbar>
        );
    }
}
