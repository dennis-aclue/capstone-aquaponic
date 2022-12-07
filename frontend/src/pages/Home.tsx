import React from "react";
import '../style/home.css';

export default function HomePage() {

    return <div className="flexColumnCenter">
        <header>
            <h1>Welcome everybody!</h1>
        </header>
        <main className="mainPage">
            <img className="mainLogo" src={process.env.PUBLIC_URL + '/aquaponicMain.png'} alt="Aquaponic"/>
            <h2>Welcome!</h2> <br/>
            <p className="mainText">Please feel free to share your knowledge about aquaponic</p>
        </main>
    </div>
};
