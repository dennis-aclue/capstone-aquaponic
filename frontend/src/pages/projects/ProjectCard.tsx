import {Link, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import axios from "axios";

const ProjectCard = () => {
    const params = useParams()

    const [project, setProject] = useState([]);

    useEffect(() => {
        const singleProjectUrl = `/api/projectCard/${params.projectId}`
        console.log("SingleProjectURL: " + singleProjectUrl)
        console.log("Params: " + params.id)
        axios.get(singleProjectUrl)
            .then((response) => {
                setProject(response.data)
            })
            .catch((error) => console.log("Get project with projectId ERROR: " + error))
    }, [params])

    return <>
        <h2>Project Nr: {}</h2>
        {project && (
            <div
                className='project__card'>
                <p>
                    Project name:
                    <span className='normal'>
						</span>
                </p>
                <p>
                    Short project description:
                    <span className='normal'>
						</span>
                </p>
            </div>
        )}
        <nav>
            <Link to="/projectOverview">Navigate to project overview</Link>
        </nav>
    </>
}

export default ProjectCard
