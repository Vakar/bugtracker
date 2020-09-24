import React, {useContext} from "react";
import {AppContext} from "../context/AppContext";
import {Link} from "react-router-dom";

export default function Home() {
    const {context} = useContext(AppContext);
    return context.user.id === 0 ? (
        <React.Fragment>
            <img
                className="mb-4 mt-5"
                src="/img/icon.png"
                alt="bugtracker-icon"
                height="72"
            />
            <h1 className="h3 mb-3 font-weight-normal">Bugtracker</h1>
        </React.Fragment>
    ) : (
        <div className="col-lg-12 text-center">
            <h1 className="mt-5">Welcome to Bugtracker</h1>
            <p className="lead">Please select the project or create new one!</p>
            <Link role="button" className="btn btn-success" to="/addProject">
                Create new project
            </Link>
        </div>
    );
}
