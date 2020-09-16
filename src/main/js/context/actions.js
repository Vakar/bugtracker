import { RESET, SET_BUGS, SET_PROJECTS, SET_USER } from "./actionTypes";

export const reset = () => ({ type: RESET });
export const setUser = (user) => ({ type: SET_USER, user });
export const setProjects = (projects) => ({ type: SET_PROJECTS, projects });
export const setBugs = (bugs) => ({ type: SET_BUGS, bugs });
