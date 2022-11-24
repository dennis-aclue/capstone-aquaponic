import {Link, useNavigate} from "react-router-dom";
import React, {useEffect, useState} from "react"
import axios from "axios";
import '../../style/projects/projectOverview.css'

export default function ProjectOverview() {

    const navigate = useNavigate();
    const projectsUrl = '/api/projects/';

    useEffect(() => {
        getAllProjects()
    }, [])

    const [projects, setProjects] = useState([]);

    const getAllProjects = () => {
        axios.get(projectsUrl)
            .then((response) => {
                setProjects(response.data)
            })
            .catch((error) => console.log("Get all projects ERROR: " + error))
    }

    return <div className="flexColumnCenter">
        <header className="headerStandardStyle">
            <h1>Project Overview</h1>
        </header>
        <nav className="navbar">
            <Link to="/">Home</Link>
        </nav>
        <section>
            <ul className="projectCard">{projects.map((projects: any) => <Link
                to={"/projectCard/" + projects.projectId}>
                <li className="projectCard__element projectCard__Element--Highlight" key={projects.projectId}>
                    <h4>Project
                        name: {projects.projectName}</h4>
                    <p>{projects.shortDescription.substring(0, 20)}...</p></li>
            </Link>)}</ul>
        </section>

        <button onClick={() => navigate("/addProject")}>add new project</button>

    </div>
}
