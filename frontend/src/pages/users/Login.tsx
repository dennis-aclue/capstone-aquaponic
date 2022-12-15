import {Link, useNavigate} from "react-router-dom";
import React, {useState} from "react"
import AuthService from "../auth.service";

const Login = () => {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [email, setEmail] = useState("");
    const [startEditing, setStartEditing] = useState(true);
    const [errorMessageStatus, setErrorMessageStatus] = useState('');
    const [passwordShown, setPasswordShown] = useState(false);

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
                    setErrorMessageStatus('Username or password is wrong')
                }
            );
        } catch (err) {
            console.log("Login Error", err);
        }
    };

    const handleChangePassword = () => {
        AuthService.resetPassword(email);
        setStartEditing(true)
        navigate("/login")
    }

    const togglePassword = () => {
        setPasswordShown(!passwordShown);
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
                    required/>
                <label className="formFieldLabel" htmlFor={"password"}>
                    Password:
                </label>
                <input
                    type={passwordShown ? "text" : "password"}
                    id="password"
                    className="formFieldInputPassword"
                    placeholder="Enter your password"
                    name="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required/>
                <div className="errorMessage">
                    {errorMessageStatus && <div className="errorMessage">{errorMessageStatus}</div>}
                </div>
                <p className="addProjectButton">
                    <button type="submit">Log in</button>
                </p>
            </form>
            <p>
                <button onClick={togglePassword}>Show Password</button>
            </p>

            <Link onClick={() => setStartEditing(false)} to={"#"}>Forgot password ?</Link>

            <Link to="/registration">
                Registration page
            </Link>

            {!startEditing && (
                <p className="popup">
                    <p className="popup-inner">
                        <h1>Do you really want to reset your password?</h1>
                        <form className="addProjectForm" onSubmit={handleChangePassword}>
                            <label className="formFieldLabel" htmlFor={"email"}>
                                Your email address:
                            </label>
                            <input
                                type="text"
                                id="email"
                                className="formFieldInputEmail"
                                placeholder="Enter your email"
                                name="email"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                required/>
                        </form>
                        <button className="popup-inner__element button" onClick={() => setStartEditing(true)}>cancel
                        </button>
                        <button className="popup-inner__element button" onClick={handleChangePassword}>Reset password
                        </button>
                    </p>
                </p>
            )}

        </div>)
}

export default Login;
