import React from "react";

export default function Login(props) {
    return props.user.id > 0 ? null : (
        <a className="btn btn-primary m-3" href="/oauth2/authorization/facebook">
            Sign in with Facebook
        </a>
    );
}
