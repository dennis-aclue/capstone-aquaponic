import {Link} from "react-router-dom";
import React from "react";
import '../style/home.css';

export default function HomePage() {

    const Navigation = () => (
        <nav className="nav">
            <Link to="/projectOverview">Project Overview</Link>
        </nav>
    );

    return <div className="flexColumnCenter">
        <header>
            <h1>Welcome everybody!</h1>
            <Navigation/>
        </header>
        <main className="mainPage">
            <img className="mainLogo" src={process.env.PUBLIC_URL + '/aquaponicMain.png'} alt="Aquaponic"/>
            <h2>Welcome!</h2> <br/>
            <p className="mainText">Please feel free to share your knowledge about aquaponic</p>
        </main>
    </div>
};
