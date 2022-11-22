import {Link} from "react-router-dom";

export default function About() {
    return <>
        <h2>This is the about page.</h2>
        <nav>
            <Link to="/">Go to the home page</Link>
        </nav>
    </>
}
