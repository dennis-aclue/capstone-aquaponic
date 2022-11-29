import {Link} from "react-router-dom";
import React, {useEffect, useState} from "react"
import axios from "axios";
import '../../style/projects/freeGallery.css';

export default function FreeGallery() {

    const [projects, setProjects] = useState([]);

    useEffect(() => {
        axios.get('api/projects/freeGallery')
            .then((response) => {
                setProjects(response.data)
            })
            .catch((error) => console.log("Get all projects ERROR: " + error))
    }, [])

    const freeGallery = () => {
        if (projects && projects.length === 0) {
            return <section>
                <ul className="projectCard">
                    <li className="projectCard__element projectCard__Element--Highlight">
                        <p className="projectCard__element data">No visible project</p>
                        <p className="projectCard__element data">Nothing to see here</p></li>
                </ul>
            </section>
        } else {
            return <section>
                <ul className="projectCard">{projects.map((projects: any) => <Link
                    to={"/freeProjectCard/" + projects.projectId}>
                    <li className="projectCard__element projectCard__Element--Highlight" key={projects.projectId}>
                        <p className="projectCard__element data">{projects.projectName}</p>
                        <p className="projectCard__element data">{projects.shortDescription.substring(0, 20)}...</p>
                    </li>
                </Link>)}</ul>
            </section>
        }
    }

    return <div className="flexColumnCenter">
        <header className="headerStandardStyle">
            <h1>Inspire your own project</h1>
        </header>
        <nav className="navbar">
            <Link to="/">Home</Link>
        </nav>
        {freeGallery()}
    </div>

}
