import React from 'react';
import {Route, Routes} from "react-router";
import HomePage from "./pages/Homes";
import ProjectOverview from "./pages/projects/ProjectOverview";
import AddProject from "./pages/projects/AddProject";
import ProjectCard from "./pages/projects/ProjectCard";
import FreeGallery from "./pages/projects/FreeGallery";
import FreeProjectCard from "./pages/projects/FreeProjectCard";

export default function App() {
    return <>
        <main>
            <Routes>
                <Route path="/" element={<HomePage/>}/>
                <Route path="/freeGallery" element={<FreeGallery/>}/>
                <Route path="/projectOverview" element={<ProjectOverview/>}/>
                <Route path="/addProject" element={<AddProject/>}/>
                <Route path="/projectCard/:projectId" element={<ProjectCard/>}/>
                <Route path="/freeProjectCard/:projectId" element={<FreeProjectCard/>}/>
            </Routes>
        </main>
        <footer className="flexColumnCenter">
            <p>Aquaponic by Dennis Völkl 2022 © ®</p>
        </footer>
    </>;
}
