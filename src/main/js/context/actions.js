import { RESET, SET_PROJECTS, SET_USER } from "./actionTypes";

export const reset = () => ({ type: RESET });
export const setContextUser = (user) => ({ type: SET_USER, user });
export const setContextProjects = (projects) => ({ type: SET_PROJECTS, projects });