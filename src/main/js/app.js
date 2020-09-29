import "bootstrap/dist/css/bootstrap.css";
import "bootstrap/dist/js/bootstrap.js";

import { BrowserRouter, Route, Switch } from "react-router-dom";
import {
  ROUTE_CREATE_PROJECT_FORM,
  ROUTE_HOME,
  ROUTE_PRIVACY,
  ROUTE_PROJECT
} from "./reactRouts";

import { AppContextProvider } from "./context/AppContext";
import Container from "./components/commons/Container";
import Footer from "./components/commons/Footer";
import Header from "./components/commons/Header";
import Home from "./components/Home";
import PageNotFound from "./components/PageNotFound";
import Privacy from "./components/Privacy";
import Project from "./components/Project";
import ProjectForm from "./components/ProjectForm";
import React from "react";
import ReactDOM from "react-dom";

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
          <Route exact path={ROUTE_PRIVACY} component={Privacy}/>
          <Route component={PageNotFound} />
        </Switch>
      </Container>
      <Footer />
    </BrowserRouter>
  </AppContextProvider>
);

ReactDOM.render(routing, document.getElementById("react"));
