import {Link, useNavigate, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import axios from "axios";
import '../../style/projects/projectCard.css';

const ProjectCard = () => {
    const params = useParams()
    const navigate = useNavigate();
    const [project, setProject] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [selectVisibility, setSelectVisibility] = useState('no');
    const [projectData, setProjectData] = useState({
        shortDescription: "",
        projectName: "",
        projectId: "",
        projectVisibility: "",
    })

    useEffect(() => {
        const singleProjectUrl = `/api/projects/projectCard/${params.projectId}`
        axios.get(singleProjectUrl)
            .then((response) => {
                setProject(response.data)
                setProjectData(response.data)
            })
            .catch((error) => console.log("Get project with projectId ERROR: " + error))
    }, [params])


    function deleteProject() {
        const deleteProjectUrl = `/api/projects/${params.projectId}`
        axios.delete(deleteProjectUrl)
            .then((response) => {
                setIsLoading(false)
                navigate("/projectOverview")
            })
            .catch((error) => console.log("Delete project with projectId ERROR: " + error))
    }

    const changeVisibility = (event: any) => {
        setSelectVisibility(event.target.value);
    };

    return <div className="flexColumnCenter">
        <header className="headerStandardStyle"></header>
        <nav className="navbar">
            <Link to="/">Home</Link>
            <Link to="/projectOverview">Projects</Link>
        </nav>
        {project && isLoading && (
            <section className='project__card'>
                <p className="projectCard__element label">Project name:</p>
                <p className="projectCard__element data">{projectData.projectName}</p>
                <p className="projectCard__element label">Short project description:</p>
                <p className="projectCard__element data">{projectData.shortDescription}</p>
                <p className="projectCard__element label">Project visibility</p>
                <p className="projectCard__element radio">
                    <input
                        disabled={isLoading}
                        type="radio"
                        id="yes"
                        name="visibility"
                        value="yes"
                        checked={selectVisibility === 'yes'}
                        onChange={changeVisibility}
                    />
                    <label htmlFor="yes">True</label></p>
                <p className="projectCard__element radio">
                    <input
                        disabled={isLoading}
                        type="radio"
                        id="no"
                        name="visibility"
                        value="no"
                        onChange={changeVisibility}
                        checked={selectVisibility === 'no'}
                    />
                    <label htmlFor="no">False</label></p>
            </section>
        )}
        <p>
            <button onClick={() => setIsLoading(false)}>delete project</button>
        </p>
        <p>
            <button className="close-btn" onClick={() => navigate("/projectOverview")}>close</button>
        </p>
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
