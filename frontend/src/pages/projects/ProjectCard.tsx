import {Link, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import axios from "axios";

const ProjectCard = () => {
    const params = useParams()

    const [project, setProject] = useState([]);

    const [projectData, setProjectData] = React.useState({
        shortDescription: "",
        projectName: "",
        projectId: "",
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


    return <>
        <h2>Project Nr: {projectData.projectId}</h2>
        {project && (
            <section
                className='project__card'>
                <p>
                    Project name: {projectData.projectName}
                </p>
                <p>
                    Short project description: {projectData.shortDescription}
                </p>
            </section>
        )}
        <nav>
            <Link to="/projectOverview">Navigate to project overview</Link>
        </nav>
    </>
}

export default ProjectCard
