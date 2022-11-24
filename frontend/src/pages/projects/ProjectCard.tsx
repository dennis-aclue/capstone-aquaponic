import {Link, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import axios from "axios";

const ProjectCard = () => {
    const params = useParams()

    const [project, setProject] = useState([]);

    const [projectData, setProjectData] = useState({
        shortDescription: "",
        projectName: "",
        projectId: "",
        projectVisibility: "",
    })

    useEffect(() => {
        const singleProjectUrl = `/api/projects/projectCard/${params.projectId}`
        console.log("SingleProjectURL: " + singleProjectUrl)
        console.log("Params: " + params.id)
        axios.get(singleProjectUrl)
            .then((response) => {
                setProject(response.data)
                setProjectData(response.data)
            })
            .catch((error) => console.log("Get project with projectId ERROR: " + error))
    }, [params])


    return <div className="flexColumnCenter">
        <header className="headerStandardStyle">
            <h2>Project Nr: {projectData.projectId}</h2>
        </header>
        <nav className="navbar">
            <Link to="/">Home</Link>
            <Link to="/projectOverview">Projects</Link>
        </nav>
        {project && (
            <section
                className='project__card'>
                <p>
                    Project name: {projectData.projectName}
                </p>
                <p>
                    Short project description: {projectData.shortDescription}
                </p>
                <p>
                    Project visibility: {projectData.projectVisibility.toString()}
                </p>
            </section>
        )}
    </div>
}

export default ProjectCard
