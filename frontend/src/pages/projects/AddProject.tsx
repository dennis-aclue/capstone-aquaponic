import React, {FormEvent, useState} from 'react';
import axios from "axios";
import {Link, useNavigate} from "react-router-dom";
import '../../style/projects/addProject.css'

export default function AddProject() {

    const [newProject, setNewProject] = useState(
        {
            shortDescription: "",
            projectName: ""
        }
    );

    const projectsUrl = '/api/projects/';
    const [messageStatus, setMessageStatus] = useState('')
    const navigate = useNavigate();
    const [buttonText, setButtonText] = useState('save new project');
    const [isLoading, setIsLoading] = useState(false);

    const setBackToProjectOverview = () => {
        navigate("/projectOverview")
    }

    const postNewProject = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        axios.post(projectsUrl, newProject)
            .then(function (response) {
                setButtonText('saving in progress');
                setIsLoading(true)
                if (response.status === 200 || response.status === 201) {
                    setMessageStatus(' New Project ' + newProject.projectName + ', successfully created.');
                }
            })
            .then(() => setTimeout(() => setBackToProjectOverview(), 2000))
            .catch((e) => console.log("POST ERROR: " + e))

        setNewProject({projectName: "", shortDescription: ""});
    }

    function handleChange(event: any) {
        setNewProject({
            ...newProject,
            [event.target.name]: event.target.value
        })
    }

    return <div className="flexColumnCenter">

        <header className="headerStandardStyle">
            <h1>Projects</h1>
        </header>
        <nav className="addProject__navbar navbar">
            <Link to="/">Home</Link>
            <Link to="/projectOverview">Projects</Link>
        </nav>
        <form className="addProjectForm" onSubmit={postNewProject}>
            <br/>
            {!isLoading &&
                <> <label htmlFor="projectName">
                    Project name:</label>
                    <input className="addProjectForm__input"
                           type="text"
                           id="projectName"
                           name="projectName"
                           value={newProject.projectName}
                           onChange={handleChange}
                           placeholder="project name"
                    />
                    <p></p>
                </>

            }
            {!isLoading &&
                <>
                    <label className="addProject_label" htmlFor="shortDescription">Short project description:</label>
                    <input className="addProjectForm__input"
                           type="text"
                           id="shortDescription"
                           name="shortDescription"
                           value={newProject.shortDescription}
                           onChange={handleChange}
                           placeholder="project description"
                    />
                </>
            }
            <p className="addProjectButton">
                <button disabled={isLoading}>{buttonText}</button>
            </p>
        </form>

        {!isLoading &&
            <button onClick={() => navigate("/projectOverview")}>Cancel</button>
        }
        {messageStatus && <p>{messageStatus}</p>}
    </div>
}
