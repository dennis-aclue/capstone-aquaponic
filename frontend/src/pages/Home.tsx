import React from "react";
import '../style/home.css';

export default function HomePage() {

    return <div className="flexColumnCenter">
        <header>
            <h1>Welcome everybody!</h1>
        </header>
        <main className="mainPage">
            <a className="MainLogoLink" href="https://de.vecteezy.com/" rel="noopener noreferrer">
                <img className="mainLogo" src={process.env.PUBLIC_URL + '/aquaponicMain.jpg'} alt="Aquaponic"/>
            </a>
            <h2>Safe our world resources</h2> <br/>
            <p className="mainText">Please feel free to share your knowledge about aquaponic</p>
        </main>
    </div>
};
