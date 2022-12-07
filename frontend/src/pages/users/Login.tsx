import {Link, useNavigate} from "react-router-dom";
import React, {useState} from "react"
import AuthService from "../auth.service";

const Login = () => {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const navigate = useNavigate();

    const handleLogin = async (e: any) => {
        e.preventDefault();
        try {
            await AuthService.login(username, password).then(
                () => {
                    navigate("/");
                    window.location.reload();
                },
                (error) => {
                    console.log("Login Error", error);
                }
            );
        } catch (err) {
            console.log("Login Error", err);
        }
    };

    return (
        <div className="flexColumnCenter">

            <header className="headerStandardStyle">
                <h1>User Login</h1>
            </header>
            <nav className="navbar">
                <Link to="/">Home</Link>
                <Link to="/freeGallery">Gallery</Link>
            </nav>
            <form className="addProjectForm" onSubmit={handleLogin}>
                <label className="formFieldLabel" htmlFor={"username"}>
                    Username:
                </label>
                <input
                    type="text"
                    id="username"
                    className="formFieldInputUsername"
                    placeholder="Enter your username"
                    name="username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />
                <label className="formFieldLabel" htmlFor={"password"}>
                    Password:
                </label>
                <input
                    type="text"
                    id="password"
                    className="formFieldInputPassword"
                    placeholder="Enter your password"
                    name="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />

                <p>
                    <button type="submit">Log in</button>
                </p>
            </form>

            <Link to="/registration">
                Registration page
            </Link>

        </div>)
}

export default Login;
