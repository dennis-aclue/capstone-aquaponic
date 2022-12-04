import {Link, useNavigate} from "react-router-dom";
import React, {useEffect} from "react"

export default function MemberOverview() {

    const navigate = useNavigate();

    useEffect(() => {
    }, [])


    return <div className="flexColumnCenter">

        <header className="headerStandardStyle">
            <h1>Member Overview</h1>
        </header>
        <nav className="navbar">
            <Link to="/">Home</Link>
            <Link to="/freeGallery">Gallery</Link>
        </nav>
        <section>
            <button onClick={() => navigate("")}>Projects</button>
            <button onClick={() => navigate("/projectOverview")}>Projects</button>
        </section>

    </div>
}
