import {Link} from "react-router-dom";
import React, {useEffect} from "react"

export default function MemberOverview() {

    useEffect(() => {
    }, [])


    return <div className="flexColumnCenter">

        <header className="headerStandardStyle">
            <h1>Member Overview</h1>
        </header>
        <nav className="navbar">
            <Link to="/">Home</Link>
            <Link to="/projectOverview">Projects</Link>
            <Link to="/freeGallery">Gallery</Link>
        </nav>

    </div>
}
