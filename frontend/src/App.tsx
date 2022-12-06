import React, {useEffect, useState} from 'react';
import {Link, Navigate, Outlet, Route, Routes, useNavigate} from "react-router-dom";
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

function App() {

    const [currentUserToken, setCurrentUserToken] = useState(undefined);
    const [startLogOut, setStartLogOut] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        const token = AuthService.getCurrentToken();
        if (token) {
            setCurrentUserToken(token);
            console.log("token: " + token);
        }
    }, []);


    const logOut = () => {
        setCurrentUserToken(undefined);
        setStartLogOut(true)
        navigate("/");
        AuthService.logout();
    };


    const askToLogOut = () => {
        setStartLogOut(false);
    };

    const logIn = () => {
        setCurrentUserToken(undefined);
        navigate("/login");
    };

    const Navigation = () => (
        <nav className="nav">
            <Link to="/">Home</Link>
            {currentUserToken && startLogOut && (
                <Link to={"#"} onClick={askToLogOut}>Logout</Link>
            )
            }
            {!startLogOut && (
                <p className="popup">
                    <p className="popup-inner">
                        <h1>Do you really want to logout<br/></h1>
                        <button className="popup-inner__element button" onClick={() => setStartLogOut(true)}>cancel
                        </button>
                        <button className="popup-inner__element button" onClick={logOut}>logout</button>
                    </p>
                </p>
            )}
            {currentUserToken === undefined &&
                <Link to={"/login"} onClick={logIn}>Login</Link>
            }
            {currentUserToken === undefined &&
                <Link to="/registration">Registration</Link>
            }
            <Link to="/freeGallery">Gallery</Link>
        </nav>
    );

    const [user, setUser] = useState(null);


    /*    Configure Protected Routes with Token

    const handleLogin = () =>
            AuthService.getCurrentToken().then((response) => {
                setUser(response.data);
            }
        );
    */
    const ProtectedRoute = ({redirectPath = '/'}) => {
        if (!user) {
            return <Navigate to={redirectPath} replace/>;
        }
        return <Outlet/>;
    };

    return <>
        <main>
            <Routes>
                <Route index element={<HomePage/>}/>
                <Route path={"/"} element={<HomePage/>}/>
                <Route path="/registration" element={<Registration/>}/>
                <Route path="/login" element={<Login/>}/>
                <Route path="/freeGallery" element={<FreeGallery/>}/>
                <Route path="/freeProjectCard/:projectId" element={<FreeProjectCard/>}/>
                <Route path="/memberOverview" element={<MemberOverview/>}/>
                <Route element={<ProtectedRoute/>}>
                    <Route path="/projectOverview" element={<ProjectOverview/>}/>
                    <Route path="/addProject" element={<AddProject/>}/>
                    <Route path="/projectCard/:projectId" element={<ProjectCard/>}/>
                </Route>
                <Route path="*" element={<p>There's nothing here: 404!</p>}/>
            </Routes>
            <Navigation/>
        </main>
        <footer className="flexColumnCenter">
            <p>Aquaponic by Dennis Völkl 2022 © ®</p>
        </footer>
    </>
        ;
}

export default App;
