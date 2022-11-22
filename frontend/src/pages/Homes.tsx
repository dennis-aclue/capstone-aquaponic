import React from 'react';
import {Link} from "react-router-dom";

export interface IHomePageProps {}
const HomePage: React.FunctionComponent<IHomePageProps> = () => {

    return <nav>
        <p>This is the landing page.</p>
        <Link to="/about">Go to the about page</Link><br />
        <Link to="/projects">Go to the project page</Link><br />
    </nav>
};
export default HomePage;
