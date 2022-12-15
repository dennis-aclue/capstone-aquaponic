import React, {useEffect, useState} from 'react';
import {Link, Route, Routes, useNavigate} from "react-router-dom";
import HomePage from "./pages/Home";
import ProjectOverview from "./pages/projects/ProjectOverview";
import AddProject from "./pages/projects/AddProject";
import ProjectCard from "./pages/projects/ProjectCard";
import FreeGallery from "./pages/projects/FreeGallery";
import FreeProjectCard from "./pages/projects/FreeProjectCard";
import MemberOverview from "./pages/users/MemberOverview";
import AuthService from "./pages/auth.service";
import Login from "./pages/users/Login";
import Registration from "./pages/users/Registration";
import {Impressum} from "./pages/Impressum";
import {About} from "./pages/About";
import {AquaponicInfo} from "./pages/AquaponicInfo";

function App() {

    const [currentUserToken, setCurrentUserToken] = useState("null");
    const [startLogOut, setStartLogOut] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        const token = AuthService.getCurrentToken();
        //console.log("Token: " + token);
        if (token) {
            setCurrentUserToken(token);
        }
    }, [startLogOut]);

    const logOut = () => {
        setCurrentUserToken("null");
        setStartLogOut(false)
        navigate("/");
        AuthService.logout();
    };

    const askToLogOut = () => {
        setStartLogOut(true);
    };

    const logIn = () => {
        navigate("/login");
    };

    const Navigation = () => (
        <nav className="nav">
            <Link to="/">Home</Link>
            {currentUserToken !== "null" ?
                <>
                    <Link to={"#"} onClick={askToLogOut}>Logout</Link>
                </>
                : <>
                    <Link to={"/login"} onClick={logIn}>Login</Link>
                    <Link to="/registration">Registration</Link>

                </>
            }
            <Link to="/freeGallery">Gallery</Link>
            <Link to="/impressum">Impressum</Link>
            <Link to="/about">About</Link>
            <Link to="/aquaponicInfo">AquaponicInfo</Link>
        </nav>
    );

    const logInNavigation = () => {
        if (currentUserToken !== "null") {
            return (
                <nav className="nav">
                    <Link to="/projectOverview">Projects</Link>
                    <Link to="/memberOverview">Member</Link>
                </nav>
            );
        }
    };

    return <>
        <main>

            <div>{logInNavigation()}</div>

            <Routes>
                <Route index element={<HomePage/>}/>
                <Route path={"/"} element={<HomePage/>}/>
                <Route path="/registration" element={<Registration/>}/>
                <Route path="/login" element={<Login/>}/>
                <Route path="/freeGallery" element={<FreeGallery/>}/>
                <Route path="/freeProjectCard/:projectId" element={<FreeProjectCard/>}/>
                <Route path="/impressum" element={<Impressum/>}/>
                <Route path="/about" element={<About/>}/>
                <Route path="/AquaponicInfo" element={<AquaponicInfo/>}/>

                <Route path="/memberOverview" element={<MemberOverview/>}/>
                <Route path="/projectOverview" element={<ProjectOverview/>}/>
                <Route path="/addProject" element={<AddProject/>}/>
                <Route path="/projectCard/:projectId" element={<ProjectCard/>}/>
                <Route path="*" element={<p>There's nothing here: 404!</p>}/>
            </Routes>
            <Navigation/>

            {startLogOut && (
                <p className="popup">
                    <p className="popup-inner">
                        <h1>Do you really want to logout<br/></h1>
                        <button className="popup-inner__element button" onClick={() => setStartLogOut(false)}>cancel
                        </button>
                        <button className="popup-inner__element button" onClick={logOut}>logout</button>
                    </p>
                </p>
            )}

        </main>
        <footer className="flexColumnCenter">
            <p>Aquaponic by Dennis Völkl 2022 © ®</p>
        </footer>
    </>
        ;
}

export default App;
