import {Link} from "react-router-dom";
import React from "react";

export default function HomePage() {

    return <div className="flexColumnCenter">
        <header className="headerStandardStyle">
            <h1>Welcome everybody!</h1>
        </header>
        <h2>This is the landing page.</h2>
        <nav className="navbar">
            <Link to="/projectOverview">Projects</Link>
        </nav>
    </div>
};
