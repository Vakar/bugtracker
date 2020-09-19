import React from "react";

export default function Login() {
  return (
    <form className="form-signin">
      <img
        className="mb-4 mt-5"
        src="/img/icon.png"
        alt="bugtracker-icon"
        height="72"
      />
      <h1 className="h3 mb-3 font-weight-normal">Bugtracker</h1>
      <a className="btn btn-primary m-3" href="/oauth2/authorization/facebook">
        Sign in with Facebook
      </a>
    </form>
  );
}
