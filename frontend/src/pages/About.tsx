import React from 'react';
import {Link} from "react-router-dom";

export interface IAboutPageProps {}
const HomePage: React.FunctionComponent<IAboutPageProps> = () => {
    return <div>
        <p>This is the about page.</p>
        <p>capstone neuefische</p>
        <Link to="/">Home</Link><br />
        <Link to="/projects">Go to the project page</Link><br />
    </div>
};
export default HomePage;