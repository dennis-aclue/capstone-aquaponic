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
                 <ol>{projects.map((projects: any) => <Link to={`/projectCard/${projects.projectId}`}
                                                       state={{
                                                           projectId: projects.projectId
                                                       }}>
                <li key={projects.projectName}>{projects.projectName}</li>
                 </Link>)}</ol>

            {/*{projects &&
                projects.map((projects: any) => (
                //single user card
                <div className="project__card" key={projects.projectName}>
                    <Link to={`/projectCard/${projects.projectId}`}>
                        <p>
                            Project name:
                            <span className="normal"> {projects.projectName}</span>
                        </p>
                    </Link>
                </div>
            ))}*/}

            {/*     <ol>{projects.map((projects: any) => <Link to={"/projectCard/" + projects.projectId}
                                                       state={{
                                                           from: "exampleProject",
                                                           projectName: projects.projectName
                                                       }}>
                <li key={projects.projectName}>{projects.projectName}</li>
            </Link>)}</ol>*/}
        </section>

        <button onClick={() => navigate("/addProject")}>add new project</button>
        <Link to="/">Go to the home page</Link>
        <Link to="/about">Go to the about page</Link>
        <Link to="/member">Go to your member side</Link>
    </>
}
