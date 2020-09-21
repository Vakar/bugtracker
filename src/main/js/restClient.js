import axios from "axios";

/** APPLICATION ROOT PATH **/
const root = () => window.location.origin.toString();

/** SERVER AUTHORIZED USER API STARTS **/
const PATH_USER = `${root()}/user`;
const PATH_LOGOUT = `${root()}/logout`;

function authorizedUser(callback) {
  axios
    .get(PATH_USER)
    .then((res) => callback(res.data))
    .catch(() => console.warn("You are not authorized!"));
}

function logout(callback) {
  axios
    .post(PATH_LOGOUT)
    .then(() => callback())
    .catch(() => console.error("Can't logout properly!"));
}

/** SERVER AUTHORIZED USER API ENDS **/

/** SERVER REST API STARTS **/
const PATH_USERS = `${root()}/users`;
const pathProjects = (userId) => `${PATH_USERS}/${userId}/projects`;
const pathBugs = (userId, projectId) =>
  `${pathProjects(userId)}/${projectId}/bugs`;

function projectsFindAll(userId, callback) {
  if (userId > 0) {
    axios
      .get(`/users/${userId}/projects`)
      .then((res) => callback(res.data))
      .catch(() => console.error("Can't get list of projects!"));
  }
}

function projectsFind(userId, projectId, callback) {
  axios
    .get(`${pathProjects(userId)}/${projectId}`)
    .then((res) => callback(res.data))
    .catch(() => console.error("Can't get information about project."));
}

function projectsSave(userId, project, callback) {
  axios
    .post(`${pathProjects(userId)}`, project)
    .then((res) => callback(res.data))
    .catch(() => console.error("Can't save project!"));
}

function projectsDelete(userId, projectId, callback) {
  axios
    .delete(`${pathProjects(userId)}/${projectId}`)
    .then(() => callback())
    .catch(() => console.error("Can't delete project."));
}

function bugsSave(userId, projectId, bug, callback) {
  axios
    .post(`${pathBugs(userId, projectId)}`, bug)
    .then((res) => callback(res.data))
    .catch(() => console.error("Can't save bug."));
}

function bugsDelete(userId, projectId, bugId, callback) {
  axios
    .delete(`${pathBugs(userId, projectId)}/${bugId}`)
    .then(callback(bugId))
    .catch(() => console.error(`Can't remove bug`));
}

export {
  authorizedUser,
  logout,
  projectsFindAll,
  projectsFind,
  projectsSave,
  projectsDelete,
  bugsSave,
  bugsDelete,
};
