import React from 'react';
import {Route, Routes} from "react-router";
import HomePage from "./pages/Homes";
import MemberOverview from "./pages/MemberOverview";
import About from "./pages/About";
import ProjectOverview from "./pages/projects/ProjectOverview";
import AddProject from "./pages/projects/AddProject";
import ProjectCard from "./pages/projects/ProjectCard";

export default function App() {
    return <>
        <main>
            <Routes>
                <Route path="/" element={<HomePage/>}/>
                <Route path="/member" element={<MemberOverview props={{username: "Dennis"}}/>}/>
                <Route path="/about" element={<About/>}/>
                <Route path="/projectOverview" element={<ProjectOverview/>}/>
                <Route path="/addProject" element={<AddProject/>}/>
                <Route path="/projectCard/:projectId" element={<ProjectCard />}/>
            </Routes>
        </main>
        <footer>Aquaponic footer</footer>
    </>;
}
