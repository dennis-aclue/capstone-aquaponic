import {Link, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import axios from "axios";
import '../../style/projects/freeProjectCard.css';


const FreeProjectCard = () => {
    const params = useParams()
    const [projectData, setProjectData] = useState({
        shortDescription: "",
        username: "",
        projectName: "",
        projectId: "",
    })

    useEffect(() => {
        const singleProjectUrl = `/api/projects/projectCard/${params.projectId}`
        axios.get(singleProjectUrl)
            .then((response) => {
                console.log(response.data)
                setProjectData(response.data)
            })
            .catch((error) => console.log("Get project with projectId ERROR: " + error))
    }, [params])

    return <div className="flexColumnCenter">
        <header className="headerStandardStyle"></header>
        <nav className="navbar">
            <Link to="/">Home</Link>
        </nav>

        <form className='project__card'>
            <p className="projectCard__element label">Username</p>
            <p className="projectCard__element data">{projectData.username}</p>
            <p className="projectCard__element label">Project name</p>
            <p className="projectCard__element data">{projectData.projectName}</p>
            <p className="projectCard__element label">Short description</p>
            <p className="projectCard__element data">{projectData.shortDescription}</p>
        </form>
        <nav className="navbar">
            <Link to="/freeGallery">Gallery</Link>
        </nav>
    </div>

}

export default FreeProjectCard
