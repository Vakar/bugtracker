import React from "react";

export default function Container(props) {
  return (
    <main className="container flex-fill mt-5" role="main">
      {props.children}
    </main>
  );
}
