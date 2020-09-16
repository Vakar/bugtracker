import * as React from "react";

import { RESET, SET_BUGS, SET_PROJECTS, SET_USER } from "./actionTypes";

let AppContext = React.createContext({});

const initialState = {
  user: {
    id: 1,
    name: "Leonid Vakar",
  },
  projects: [],
  bugs: [],
};

let reducer = (state, action) => {
  switch (action.type) {
    case RESET:
      return initialState;
    case SET_USER:
      return { ...state, user: action.user };
    case SET_PROJECTS:
      return { ...state, projects: action.projects };
    case SET_BUGS:
      return { ...state, bugs: action.bugs };
  }
};

function AppContextProvider(props) {
  let [state, dispatch] = React.useReducer(reducer, initialState);
  return (
    <AppContext.Provider value={{ state, dispatch }}>
      {props.children}
    </AppContext.Provider>
  );
}

export { AppContext, AppContextProvider };
