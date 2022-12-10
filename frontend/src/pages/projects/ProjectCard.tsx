import {useNavigate, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import axios from "axios";
import '../../style/projects/projectCard.css';

const ProjectCard = () => {
    const params = useParams()
    const navigate = useNavigate();
    const [isLoading, setIsLoading] = useState(true);
    const [startEditing, setStartEditing] = useState(true);
    const [isChecked, setIsChecked] = useState(true);
    const [projectData, setProjectData] = useState({
        shortDescription: "",
        projectName: "",
        projectId: "",
        projectVisibility: false,
    })

    useEffect(() => {
        const singleProjectUrl = `/api/projects/projectCard/${params.projectId}`
        axios.get(singleProjectUrl)
            .then((response) => {
                setProjectData(response.data)
            })
            .catch((error) => console.log("Get project with projectId ERROR: " + error))
    }, [params])


    function deleteProject() {
        const deleteProjectUrl = `/api/projects/${params.projectId}`
        axios.delete(deleteProjectUrl)
            .then(() => {
                setIsLoading(false)
                navigate("/projectOverview")
            })
            .catch((error) => console.log("Delete project with projectId ERROR: " + error))
    }

    function cancelEdit() {
        setStartEditing(true)
        window.location.reload();
    }

    function updateProject() {
        const updateProjectUrl = `/api/projects/update/${params.projectId}`
        axios.put(updateProjectUrl, projectData)
            .then(() => {
                setStartEditing(false)
                window.location.reload();
            })
            .catch((error) => console.log("Update project with projectId ERROR: " + error))
    }

    const checkHandler = () => {
        setIsChecked(!isChecked)
        setProjectData({...projectData, projectVisibility: isChecked})
    }

    return <div className="flexColumnCenter">
        <header className="headerStandardStyle"></header>
        <form className='project__card'>
            <p className="projectCard__element label">Project name</p>
            <input
                className="projectCard__element input"
                disabled={startEditing}
                type="text"
                id="projectName"
                name="projectName"
                value={projectData.projectName}
                onChange={(e) => setProjectData({...projectData, projectName: e.target.value})}
            />
            <p className="projectCard__element label">Short description</p>
            <input
                className="projectCard__element input"
                disabled={startEditing}
                type="text"
                id="shortDescription"
                name="shortDescription"
                value={projectData.shortDescription}
                onChange={(e) => setProjectData({...projectData, shortDescription: e.target.value})}
            />
            <p className="projectCard__element label">Project visibility</p>
            <p className="projectCard__element radio">
                <input
                    disabled={startEditing}
                    type="checkbox"
                    id="projectVisibility"
                    name="projectVisibility"
                    checked={projectData.projectVisibility}
                    onChange={checkHandler}
                />
                <span
                    className="projectCard__element label">{projectData.projectVisibility ? "visible for all" : "only visible for you"}</span>
            </p>

        </form>

        {startEditing && (
            <div>
                <button onClick={() => setIsLoading(false)}>delete project</button>
                <button className="close-btn" onClick={() => navigate("/projectOverview")}>close</button>
            </div>
        )}
        {!startEditing && (
            <div>
                <button onClick={cancelEdit}>cancel edit</button>
                <button onClick={updateProject}>save</button>
            </div>
        )}
        {startEditing && (
            <button onClick={() => setStartEditing(false)}>edit project</button>
        )}
        {!isLoading && (
            <p className="popup">
                <p className="popup-inner">
                    <h1>Do you really want to delete your<br/>
                        "{projectData.projectName}" <br/>
                        project?</h1>
                    <button className="popup-inner__element button" onClick={() => setIsLoading(true)}>cancel</button>
                    <button className="popup-inner__element button" onClick={deleteProject}>ok</button>
                </p>
            </p>
        )}
    </div>

}

export default ProjectCard
