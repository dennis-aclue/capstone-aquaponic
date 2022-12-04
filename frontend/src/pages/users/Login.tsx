import {Link, useNavigate} from "react-router-dom";
import React, {FormEvent, useEffect, useState} from "react"
import axios from "axios";

export default function Login() {

    const navigate = useNavigate();
    const loginUrl = '/user/login';
    const [messageStatus, setMessageStatus] = useState('')
    const [isLoading, setIsLoading] = useState(false);

    const setGoToMemberOverview = () => {
        navigate("/memberOverview")
    }

    useEffect(() => {
    }, [])

    const [loginUser, setLoginUser] = useState(
        {
            username: "",
            password: ""
        }
    );

    function postNewUser(event: FormEvent<HTMLFormElement>) {
        event.preventDefault()
        axios.post(loginUrl, loginUser)
            .then(function (response) {
                setIsLoading(true)
            })
            .then(() => setTimeout(() => setGoToMemberOverview(), 500))
            .catch((e) => console.log("POST ERROR: " + e))
    }

    function handleChange(event: any) {
        setLoginUser({
            ...loginUser,
            [event.target.name]: event.target.value
        })
    }

    return <div className="flexColumnCenter">

        <header className="headerStandardStyle">
            <h1>User Login</h1>
        </header>
        <nav className="navbar">
            <Link to="/">Home</Link>
            <Link to="/freeGallery">Gallery</Link>
        </nav>
        <form className="addProjectForm" onSubmit={postNewUser}>
            <label className="formFieldLabel" htmlFor={"username"}>
                Username:
            </label>
            <input
                type="text"
                id="username"
                className="formFieldInputUsername"
                placeholder="Enter your username"
                name="username"
                value={loginUser.username}
                onChange={handleChange}
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
                value={loginUser.password}
                onChange={handleChange}
            />

            <p className="addProjectButton">
                <button disabled={isLoading}>Login</button>
            </p>
        </form>

        <Link to="/registration">
            Registration page
        </Link>

    </div>
}
