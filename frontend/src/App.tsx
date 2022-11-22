import React from 'react';
import {Route, Routes} from "react-router";
import HomePage from "./pages/Homes";
import MemberOverview from "./pages/MemberOverview";
import About from "./pages/About";
import ProjectOverview from "./pages/ProjectOverview";
import AddProject from "./pages/AddProject";

export default function App() {
    return <>
        <h1>Welcome everybody!</h1>
        <main>
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/member" element={<MemberOverview />} />
                    <Route path="/about" element={<About />} />
                    <Route path="/projectOverview" element={<ProjectOverview />} />
                    <Route path="/addProject" element={<AddProject />} />
                </Routes>
        </main>
        <footer>Aquaponic footer</footer>
    </>;
}
