import React, {FormEvent, useEffect, useState} from 'react';
import axios from "axios";
import {Link, useNavigate} from "react-router-dom";
import '../../style/projects/addProject.css'

export default function AddProject() {

    const [userId, setUserId] = useState("")
    const [shortDescription, setShortDescription] = useState("")
    const [projectName, setProjectName] = useState("")
    const [username, setUsername] = useState("")

    useEffect(() => {
        let userId = (JSON.parse(sessionStorage.getItem('user') || '{}  ').userId);
        setUserId(userId);
        console.log("userId: " + userId)
        let username = (JSON.parse(sessionStorage.getItem('user') || '{}  ').username);
        setUsername(username);
        console.log("username: " + username)
    }, []);

    const [messageStatus, setMessageStatus] = useState('')
    const navigate = useNavigate();
    const [buttonText, setButtonText] = useState('save new project');
    const [isLoading, setIsLoading] = useState(false);

    const setBackToProjectOverview = () => {
        navigate("/projectOverview")
    }

    const postNewProject = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        axios.post('/api/projects/addProject', {
            userId: userId,
            username: username,
            projectName: projectName,
            shortDescription: shortDescription,
            projectVisibility: false,
        })
            .then(function (response) {
                setButtonText('saving in progress');
                setIsLoading(true)
                if (response.status === 200 || response.status === 201) {
                    setMessageStatus(' New Project ' + projectName + ', successfully created.');
                }
            })
            .then(() => setTimeout(() => setBackToProjectOverview(), 2000))
            .catch((e) => console.log("POST ERROR: " + e))

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
                           value={projectName}
                           onChange={(e) => setProjectName(e.target.value)}
                           placeholder="project name"
                    />
                </>

            }
            {!isLoading &&
                <>
                    <label className="addProject_label" htmlFor="shortDescription">Short project description:</label>
                    <input className="addProjectForm__input"
                           type="text"
                           id="shortDescription"
                           name="shortDescription"
                           value={shortDescription}
                           onChange={(e) => setShortDescription(e.target.value)}
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
