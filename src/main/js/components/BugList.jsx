import React, { useEffect, useState } from "react";
import Bug from "./Bug";

export default function BugList(props) {
  const [bugs, ] = props.bugs;
  const [bugList, setBugList] = useState([]);

  useEffect(() => {
    setBugList(bugs);
  }, [bugs]);

  return (
    <ul className="list-group list-group-flush mt-3">
      {bugList.map((bug, index) => {
        return <Bug key={index} bug={bug} bugs={props.bugs} />;
      })}
    </ul>
  );
}