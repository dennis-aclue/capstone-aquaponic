import {Link, useNavigate} from "react-router-dom";
import React, {FormEvent, useEffect, useState} from "react"
import axios from "axios";

export default function Registration() {

    const navigate = useNavigate();
    const registrationUrl = '/user/register';
    const [messageStatus, setMessageStatus] = useState('')
    const [errorMessageStatus, setErrorMessageStatus] = useState('')
    const [isLoading, setIsLoading] = useState(false);

    const setBackToHome = () => {
        navigate("/")
    }

    useEffect(() => {
    }, [])

    const [newUser, setNewUser] = useState(
        {
            firstName: "",
            lastName: "",
            username: "",
            email: ""
        }
    );

    function postNewUser(event: FormEvent<HTMLFormElement>) {
        event.preventDefault()
        axios.post(registrationUrl, newUser)
            .then(function () {
                setIsLoading(true)
                setErrorMessageStatus('')
                setMessageStatus(' New username: ' + newUser.username + ', successfully created. Please check you eMail for further instructions.');
                setTimeout(() => {
                        setBackToHome()
                    }
                    , 4000)
            })
            .catch(function (error) {
                setErrorMessageStatus('Username or email already exists')
            })
    }

    function handleChange(event: any) {
        setNewUser({
            ...newUser,
            [event.target.name]: event.target.value
        })
    }

    return <div className="flexColumnCenter">

        <header className="headerStandardStyle">
            <h1>User registration</h1>
        </header>
        <nav className="navbar">
            <Link to="/">Home</Link>
            <Link to="/freeGallery">Gallery</Link>
        </nav>
        {errorMessageStatus !== '' && <p className="errorMessage">{errorMessageStatus}</p>}
        <form className="addProjectForm" onSubmit={postNewUser}>
            {!isLoading && <>
                <label className="formFieldLabel" htmlFor={"firstName"}>
                    First Name:
                </label>
                <input
                    type="text"
                    id="firstName"
                    className="formFieldInputFirstName"
                    placeholder="Enter your first name"
                    name="firstName"
                    value={newUser.firstName}
                    onChange={handleChange}
                    required/>
            </>
            }
            {!isLoading && <>
                <label className="formFieldLabel" htmlFor={"lastName"}>
                    Last Name:
                </label>
                <input
                    type="text"
                    id="lastName"
                    className="formFieldInputLastName"
                    placeholder="Enter your last name"
                    name="lastName"
                    value={newUser.lastName}
                    onChange={handleChange}
                    required/>
            </>
            }
            {!isLoading && <>
                <label className="formFieldLabel" htmlFor={"username"}>
                    Username
                </label>
                <input
                    type="text"
                    id="username"
                    className="formFieldInputUsername"
                    placeholder="Enter your username"
                    name="username"
                    value={newUser.username}
                    onChange={handleChange}
                    required/>
            </>
            }
            {!isLoading && <>
                <label className="formFieldLabel" htmlFor="email">
                    E-Mail Address:
                </label>
                <input
                    type="email"
                    id="email"
                    className="formFieldInput"
                    placeholder="Enter your email"
                    name="email"
                    value={newUser.email}
                    onChange={handleChange}
                    required/>
            </>
            }
            {!isLoading && <>
                <p className="addProjectButton">
                    <button disabled={isLoading}>Sign Up</button>
                </p>
            </>
            }
        </form>

        {!isLoading &&
            <Link to="/login">
                I'm already member
            </Link>
        }
        <div>
            {messageStatus !== '' && <p className="successMessage">{messageStatus}</p>}
        </div>

    </div>
}
