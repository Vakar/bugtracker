import React from "react";
import { Link } from "react-router-dom";

export default function WellCome() {
  return (
    <div className="container">
      <div className="row">
        <div className="col-lg-12 text-center">
          <h1 className="mt-5">Welcome to Bugtracker</h1>
          <p className="lead">Please select the project or create new one!</p>
          <Link role="button" className="btn btn-success" to="/addProject">
            Create new project
          </Link>
        </div>
      </div>
    </div>
  );
}
