import "bootstrap/dist/css/bootstrap.css";
import "bootstrap/dist/js/bootstrap.js";

import React from "react";
import ReactDOM from "react-dom";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import {
  ROUTE_CREATE_PROJECT_FORM,
  ROUTE_HOME,
  ROUTE_PROJECT,
} from "./reactRouts";

import Project from "./components/Project";
import Container from "./components/commons/Container";
import Header from "./components/commons/Header";
import ProjectForm from "./components/ProjectForm";
import Footer from "./components/commons/Footer";

import { AppContextProvider } from "./context/AppContext";
import Home from "./components/Home";
import PageNotFound from "./components/PageNotFound";

const routing = (
  <AppContextProvider>
    <BrowserRouter>
      <Header />
      <Container>
        <Switch>
          <Route exact path={ROUTE_HOME} component={Home} />
          <Route exact path={`${ROUTE_PROJECT}:handle`} component={Project} />
          <Route
            exact
            path={ROUTE_CREATE_PROJECT_FORM}
            component={ProjectForm}
          />
          <Route component={PageNotFound} />
        </Switch>
      </Container>
      <Footer />
    </BrowserRouter>
  </AppContextProvider>
);

ReactDOM.render(routing, document.getElementById("react"));
