import axios from "axios";

/** APPLICATION ROOT PATH **/
const root = () => window.location.origin.toString();

/** SERVER AUTHORIZED USER API STARTS **/
const PATH_USER = `${root()}/rest/user`;
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
const PATH_USERS = `${root()}/rest/users`;
const pathProjects = (userId) => `${PATH_USERS}/${userId}/projects`;
const pathBugs = (userId, projectId) =>
  `${pathProjects(userId)}/${projectId}/bugs`;

function projectsFindAll(userId, callback) {
  if (userId > 0) {
    axios
      .get(pathProjects(userId))
      .then((res) => callback(res.data))
      .catch(() => {
        console.error("Can't get list of projects!");
        window.location = "/";
      });
  }
}

function projectsFind(userId, projectId, callback) {
  axios
    .get(`${pathProjects(userId)}/${projectId}`)
    .then((res) => callback(res.data))
    .catch(() => {
      console.error("Can't get information about project.");
      window.location = "/";
    });
}

function projectsSave(userId, project, callback) {
  axios
    .post(`${pathProjects(userId)}`, project)
    .then((res) => callback(res.data))
    .catch(() => {
      console.error("Can't save project!");
      window.location = "/";
    });
}

function projectsDelete(userId, projectId, callback) {
  axios
    .delete(`${pathProjects(userId)}/${projectId}`)
    .then(() => callback())
    .catch(() => {
      console.error("Can't delete project.");
      window.location = "/";
    });
}

function bugsSave(userId, projectId, bug, callback) {
  axios
    .post(`${pathBugs(userId, projectId)}`, bug)
    .then((res) => callback(res.data))
    .catch(() => {
      console.error("Can't save bug.");
      window.location = "/";
    });
}

function bugsDelete(userId, projectId, bugId, callback) {
  axios
    .delete(`${pathBugs(userId, projectId)}/${bugId}`)
    .then(callback(bugId))
    .catch(() => {
      console.error(`Can't remove bug`);
      window.location = "/";
    });
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
