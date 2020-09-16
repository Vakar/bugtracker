import "bootstrap/dist/css/bootstrap.css";
import "bootstrap/dist/js/bootstrap.js";

import React from "react";
import ReactDOM from "react-dom";
import { BrowserRouter, Route, Switch } from "react-router-dom";

import Project from "./components/Project";
import Container from "./components/Container";
import Navigation from "./components/Navigation";
import Home from "./components/Home";
import ProjectForm from "./components/ProjectForm";
import Footer from "./components/Footer";

import { AppContextProvider } from "./context/AppContext";

const routing = (
  <AppContextProvider>
    <BrowserRouter>
      <Navigation />
      <Container>
        <Switch>
          <Route exact path="/" component={Home} />
          <Route exact path="/project/:handle" component={Project} />
          <Route exact path="/addProject" component={ProjectForm} />
        </Switch>
      </Container>
      <Footer />
    </BrowserRouter>
  </AppContextProvider>
);

ReactDOM.render(routing, document.getElementById("react"));
