import "bootstrap/dist/css/bootstrap.css";
import "bootstrap/dist/js/bootstrap.js";

import React from "react";
import ReactDOM from "react-dom";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import {ROUTE_HOME,ROUTE_PROJECT, ROUTE_CREATE_PROJECT_FORM} from "./reactRouts";

import Project from "./components/Project";
import Container from "./components/Container";
import Header from "./components/Header";
import ProjectForm from "./components/ProjectForm";
import Footer from "./components/Footer";

import { AppContextProvider } from "./context/AppContext";
import Home from "./components/Home";

const routing = (
  <AppContextProvider>
    <BrowserRouter>
      <Header />
      <Container>
        <Switch>
          <Route exact path={ROUTE_HOME} component={Home} />
          <Route exact path={`${ROUTE_PROJECT}:handle`} component={Project} />
          <Route exact path={ROUTE_CREATE_PROJECT_FORM} component={ProjectForm} />
        </Switch>
      </Container>
      <Footer />
    </BrowserRouter>
  </AppContextProvider>
);

ReactDOM.render(routing, document.getElementById("react"));
