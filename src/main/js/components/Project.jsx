import React from "react";
import {useState, useEffect} from "react";
import axios from "axios";

function Project() {

    const [project, setProject] = useState({});

    useEffect(() => {
        axios.get('users/1/projects/1')
            .then(res => setProject(res.data));
    }, []);

    return (
        <section about="project data">
            <h1>{project.title}</h1>
            <p>{project.description}</p>
        </section>
    );
}

export default Project;