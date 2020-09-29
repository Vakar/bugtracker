import { Alert, Button } from "reactstrap";
import React, { useEffect, useState } from "react";

import Cookies from "universal-cookie";
import { Link } from "react-router-dom";
import { ROUTE_PRIVACY } from "../../reactRouts";

function AcceptCookies() {
  const cookies = new Cookies();
  const [visible, setVisible] = useState(false);
  const COOKIE_NAME = "cookiesAccepted";

  useEffect(() => {
    const value = cookies.get(COOKIE_NAME);
    const isCookieExists = value === "true";
    setVisible(!isCookieExists);
  }, []);

  const COOKIE_AGE_30_DAYS = 30 * 24 * 60 * 60;

  function acceptCookies() {
    cookies.set(COOKIE_NAME, "true", {
      path: "/",
      secure: true,
      sameSite: "strict",
      maxAge: COOKIE_AGE_30_DAYS,
    });
    setVisible(false);
  }

  return (
    <Alert className="m-0" color="info" isOpen={visible}>
      <span>
        Cookies are essential so that you can't use this website without them.
        More information you can find out on our{" "}
        <Link className="alert-link" to={ROUTE_PRIVACY}>
          privacy policy page
        </Link>
        .
      </span>{" "}
      <div className="text-center">
        <Button color="success" size="sm" onClick={acceptCookies}>
          I agree
        </Button>
      </div>
    </Alert>
  );
}

export default AcceptCookies;
