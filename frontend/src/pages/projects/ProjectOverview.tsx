import {Link, useNavigate} from "react-router-dom";
import React from "react"
import {useEffect, useState} from "react";
import axios from "axios";


export default function ProjectOverview() {

    const navigate = useNavigate();
    const baseUrl = '/api/projects/';

    useEffect(() => {
        getAllProjects()
    }, [])

    const [projects, setProjects] = useState([]);

    const getAllProjects = () => {
        axios.get(baseUrl)
            .then((response) => {
                setProjects(response.data)
            })
            .catch((error) => console.log("Get all projects ERROR: " + error))
    }

    return <>
        <h2>Project Overview</h2>
        <section>
                 <ol>{projects.map((projects: any) => <Link to={"/projectCard/" + projects.projectId}
                                                       state={{
                                                           projectId: projects.projectId
                                                       }}>
                <li key={projects.projectId}>{projects.projectName}</li>
                 </Link>)}</ol>
        </section>

        <button onClick={() => navigate("/addProject")}>add new project</button>
        <Link to="/">Go to the home page</Link>
        <Link to="/about">Go to the about page</Link>
        <Link to="/member">Go to your member side</Link>
    </>
}
