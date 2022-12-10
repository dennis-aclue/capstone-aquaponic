import {Link, useNavigate} from "react-router-dom";
import React, {useEffect, useState} from "react"
import axios from "axios";
import '../../style/projects/projectOverview.css'

export default function ProjectOverview() {

    const navigate = useNavigate();

    useEffect(() => {
        let userId = (JSON.parse(sessionStorage.getItem('user') || '{}  ').userId);
        getAllProjects(userId)
    }, [])

    const [projects, setProjects] = useState([]);

    const getAllProjects = (userId: string) => {
        axios.get('/api/projects/userProjectOverview/' + userId)
            .then((response) => {
                setProjects(response.data)
            })
            .catch((error) => console.log("Get all projects ERROR: " + error))
    }

    return <div className="flexColumnCenter">
        <header className="headerStandardStyle">
            <h1>Project Overview</h1>
        </header>
        <section>
            <ul className="projectCard">{projects.map((projects: any) => <Link
                to={"/projectCard/" + projects.projectId}>
                <li className="projectCard__element projectCard__Element--Highlight" key={projects.projectId}>
                    <p key="projectName" className="projectCard__element data">{projects.projectName}</p>
                    <p key="shortDescription"
                       className="projectCard__element data">{projects.shortDescription.substring(0, 20)}...</p></li>
            </Link>)}</ul>
        </section>

        <div>
            <button onClick={() => navigate("/addProject")}>add new project</button>
        </div>

    </div>
}
