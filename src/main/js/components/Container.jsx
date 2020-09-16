import React from "react";

export default function Container(props) {
  return (
    <main className="container flex-fill" role="main">
      {props.children}
    </main>
  );
}
