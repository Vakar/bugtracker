import React, {useContext, useEffect, useState} from "react";
import {Link} from "react-router-dom";
import {AppContext} from "../../context/AppContext";
import {setContextProjects} from "../../context/actions";
import {projectsFindAll} from "../../restClient";
import {
  DropdownItem,
  DropdownMenu,
  DropdownToggle,
  Nav,
  NavItem,
  UncontrolledDropdown,
} from "reactstrap";

export default function Navigation(props) {
  const {context, dispatch} = useContext(AppContext);
  const [projects, setProjects] = useState([]);

  useEffect(() => projectsFindAll(context.user.id, callback), [context.user]);
  const callback = (data) => dispatch(setContextProjects(data));
  useEffect(() => setProjects(context.projects), [context.projects]);

  return (
      <Nav className="ml-auto" navbar>
        <NavItem>
          <Link className="nav-link" to="/">
            Home
          </Link>
        </NavItem>
        <UncontrolledDropdown nav inNavbar>
          <DropdownToggle nav caret>
            Projects
            <span className="badge badge-light ml-1">{projects.length}</span>
          </DropdownToggle>
          <DropdownMenu right>
            {projects.map((p, index) => {
              return (
                  <DropdownItem key={index}>
                    <Link className="dropdown-item" to={`/project/${p.id}`}>
                      {p.title}
                    </Link>
                  </DropdownItem>
              );
            })}
          </DropdownMenu>
        </UncontrolledDropdown>
      </Nav>
  );
}
