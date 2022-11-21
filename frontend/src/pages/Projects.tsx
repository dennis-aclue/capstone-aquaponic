import React, {FormEvent} from 'react';
import axios from "axios";
import {Link} from "react-router-dom";

export default function Projects() {

    const [newProject, setNewProject] = React.useState(
        {
            shortDescription: "",
            projectName: ""
        }
    );

    const baseUrl = '/api/projects/';

    const postNewProject = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        axios.post(baseUrl, newProject)
            .then(function (response) {
                console.log(response)
            })
            .catch((e) => console.log("POST ERROR: " + e))
        setNewProject({projectName: "", shortDescription: ""});
    }

    function handleChange(event: any) {
        setNewProject({
            ...newProject,
            [event.target.name]: event.target.value
        })
    }

    return <>
        <h2>Projects</h2>
        <form onSubmit={postNewProject}>
            <h3>Please insert your project name and description</h3>
            <br/>
            <label>
                Project name:
                <input type="text"
                       id="projectName"
                       name="projectName"
                       value={newProject.projectName}
                       onChange={handleChange}
                       placeholder="project name"
                />
            </label>
            <br/>
            <label>
                Short project description:
                <input type="text"
                       id="projectDescription"
                       name="shortDescription"
                       value={newProject.shortDescription}
                       onChange={handleChange}
                       placeholder="project description"
                />
                <br/><br/>
            </label>
            <button>add new project</button>
        </form>
        <Link to="/about">Go to the about page</Link><br />
        <Link to="/">Go to the home page</Link><br />
    </>
}