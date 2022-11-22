import React, {FormEvent, useState} from 'react';
import axios from "axios";
import {useNavigate} from "react-router-dom";

export default function AddProject() {

    const [newProject, setNewProject] = React.useState(
        {
            shortDescription: "",
            projectName: ""
        }
    );

    const baseUrl = '/api/projects/';
    const [messageStatus, setMessageStatus] = useState('')
    const navigate = useNavigate();
    const [buttonText, setButtonText] = useState('save new project');
    const [isLoading, setIsLoading] = React.useState(false);

    const setBackHome = () => {
        navigate("/projectOverview")
    }

    const postNewProject = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        axios.post(baseUrl, newProject)
            .then(function (response) {
                setButtonText('saving in progress');
                setIsLoading(true)
                console.log(response)
                if (response.status === 200 || response.status === 201) {
                    setMessageStatus(' New Project ' + newProject.projectName + ', successfully created.');
                }
            })
            .then(() => setTimeout(() => setBackHome(), 4000))
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
            <button disabled={isLoading}>{buttonText}</button>
            {!isLoading &&
                <button onClick={() => navigate("/projectOverview")}>Cancel</button>
            }
            {messageStatus && <p>{messageStatus}</p>}
        </form>
    </>
}
