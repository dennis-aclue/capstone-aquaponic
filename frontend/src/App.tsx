import React, {useState} from 'react';
import {Route, Routes} from "react-router";
import HomePage from "./pages/Homes";
import ProjectOverview from "./pages/projects/ProjectOverview";
import AddProject from "./pages/projects/AddProject";
import ProjectCard from "./pages/projects/ProjectCard";
import FreeGallery from "./pages/projects/FreeGallery";
import FreeProjectCard from "./pages/projects/FreeProjectCard";
import Registration from "./pages/users/Registration";
import Login from "./pages/users/Login";
import MemberOverview from "./pages/users/MemberOverview";

export default function App() {

    const [userName, setUserDetails] = useState<string>();

    if (userName === undefined) {
        return <>
            <main>
                <Routes>
                    <Route path={"/"} element={<HomePage/>}></Route>
                    <Route path="/registration" element={<Registration/>}/>
                    <Route path="/login" element={<Login/>}/>
                    <Route path="/freeGallery" element={<FreeGallery/>}/>
                    <Route path="/freeProjectCard/:projectId" element={<FreeProjectCard/>}/>
                </Routes>
            </main>
        </>;
    }

    return <>
        <main>
            <Routes>
                <Route path="/" element={<HomePage/>}/>
                <Route path="/memberOverview" element={<MemberOverview/>}/>
                <Route path="/projectOverview" element={<ProjectOverview/>}/>
                <Route path="/addProject" element={<AddProject/>}/>
                <Route path="/projectCard/:projectId" element={<ProjectCard/>}/>
            </Routes>
        </main>
        <footer className="flexColumnCenter">
            <p>Aquaponic by Dennis Völkl 2022 © ®</p>
        </footer>
    </>;
}
