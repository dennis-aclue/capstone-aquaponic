import {Link, useNavigate} from "react-router-dom";

export default function ProjectOverview() {

    const navigate = useNavigate();

    return <>
        <h2>Projects</h2>
        <button onClick={() => navigate("/addProject") }>add new project</button>
        <Link to="/">Go to the home page</Link>
        <Link to="/about">Go to the about page</Link>
        <Link to="/member">Go to your member side</Link>
    </>
}
