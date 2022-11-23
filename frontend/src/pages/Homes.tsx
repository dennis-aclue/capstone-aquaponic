import {Link} from "react-router-dom";
import React from "react";

export default function HomePage() {

    return <>
        <h1>Welcome everybody!</h1>
        <h2>This is the landing page.</h2>
        <nav>
            <Link to="/about">Go to the about page</Link><br/>
            <Link to="/member">Navigate to your private page</Link><br/>
        </nav>
    </>
};
