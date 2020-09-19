import React from "react";
import { RESET, SET_PROJECTS, SET_USER } from "./actionTypes";

const AppContext = React.createContext({});

const DEFAULT_USER = {
  id: 0,
  name: "",
};
Object.freeze(DEFAULT_USER);

const initContext = {
  user: DEFAULT_USER,
  projects: [],
};

let reducer = (context, action) => {
  switch (action.type) {
    case RESET:
      return initContext;
    case SET_USER:
      return { ...context, user: action.user };
    case SET_PROJECTS:
      return { ...context, projects: action.projects };
  }
};

function AppContextProvider(props) {
  let [context, dispatch] = React.useReducer(reducer, initContext);
  return (
    <AppContext.Provider value={{ context, dispatch }}>
      {props.children}
    </AppContext.Provider>
  );
}

export { AppContext, AppContextProvider, DEFAULT_USER };
