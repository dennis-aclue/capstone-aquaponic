import {Link} from "react-router-dom";
import React from "react";
import '../style/homes.css';

export default function HomePage() {

    return <div className="flexColumnCenter">
        <header>
            <h1>Welcome everybody!</h1>
        </header>
        <main className="mainPage">
            <img className="mainLogo" src={process.env.PUBLIC_URL + '/aquaponicMain.png'} alt="Aquaponic"/>
            <h2>Welcome!</h2> <br/>
            <p className="mainText">Please feel free to share your knowledge to make the world a little bit better</p>
            <nav className="navbar">
                <Link to="/freeGallery">Gallery</Link>
                <Link to="/projectOverview">Projects</Link>
            </nav>
        </main>
    </div>
};
