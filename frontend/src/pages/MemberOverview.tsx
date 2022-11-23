import {Link} from "react-router-dom";

export default function Member(props: any) {
    const {username}=props.props;
    return <>
        <h2>Welcome {username} to your member page!</h2>
        <nav>
            <Link to="/">Go to the home page</Link>
            <Link to="/about">Go to the about page</Link><br/>
            <Link to="/projectOverview">Go to your private project overview</Link>
        </nav>
    </>
}
