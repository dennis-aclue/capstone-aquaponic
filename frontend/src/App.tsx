import React from 'react';
import {Route, Routes} from "react-router";
import About from "./pages/About";
import HomePage from "./pages/Homes";
import Projects from "./pages/Projects";

export default function App() {
    return <>
        <h1>Welcome everybody!</h1>
        <main>
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="projects" element={<Projects />} />
                    <Route path="about" element={<About />} />
                </Routes>
        </main>
        <footer></footer>
    </>;
}
