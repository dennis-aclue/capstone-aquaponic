import * as React from 'react';

export const About = () => {
    return <div className="flexColumnCenter">
        <header>
            <h1>About</h1>
        </header>
        <main className="about">
            <p>Danke an alle</p>
            <div> - Vielen Dank an alle, die mich bei der Entwicklung dieses Projekts unterstützt haben.
                <br/> Marc und Chris und GRUPPE 5, sowie das Team von <a href="https://www.neuefische.de">neue
                    fische.de</a>
                - Vielen Dank an meinen neuen Arbeitgeber Aclue, das er mit die Zeit eingeräumt hat um dieses Projekt zu
                entwickeln, sowie Kollegen, die mich unterstützt haben. <a href="https://www.aclue.de/">"Aclue GmbH"</a>
                - Besonderen Dank an meine Familie und Freunde, die mich immer unterstützt haben und mir die Zeit
                eingeräumt haben um dieses Projekt zu entwickeln.
                <br/>
                - Vielen Dank an meinen Schreibtischstuhl, das er mich die Stunden getragen hat!
                <br/>
            </div>

            <div>
                <p>Technische Details der Applikation</p>

                <p>Frontend</p>
                <ul>
                    <li>React</li>
                </ul>

                <p>Backend</p>
                <ul>
                    <li>Java</li>
                    <li>Spring Boot</li>
                    <li>Spring Security</li>
                    <li>Spring Data JPA</li>
                    <li>Spring Web</li>
                    <li>JWT</li>
                    <li>Mockito</li>
                    <li>Spring Boot Starter Test</li>
                    <li>Spring Boot Starter Web</li>
                    <li>Spring Boot Starter Security</li>
                </ul>

                <p>Sonstiges</p>
                <ul>
                    <li>GitHub</li>
                    <li>Postman</li>
                    <li>IntelliJ</li>
                    <li>MongoDb</li>
                    <li>SonarCloud</li>
                    <li>AWS EC2</li>
                    <li>React Hook Form Error Message</li>
                </ul>
            </div>

            <p>Technische Informationen</p>
            <p>Version: 1.0.0</p>
            <div>
                Inhalte: <br/>
                <ul>
                    <li>Ohne Login, alle freigegebene Projekte ansehen</li>
                    <li>Login möglich, mit JWT Token</li>
                    <li>Registrierung möglich</li>
                    <li>Übersicht aller eigenen Projekte</li>
                    <li>Anlegen eines Projektes</li>
                    <li>Projekt bearbeiten</li>
                    <li>Projekt löschen</li>
                    <li>Ändern von Vor- und Nachnamen</li>
                    <li>User löschen, nur wenn Projekte gelöscht sind</li>
                    <li>Projekt freigeben</li>
                    <li>Projekt nicht mehr freigeben</li>
                    <li>Passwort reset, nicht eingeloggt</li>
                </ul>
                WICHTIG: <br/>
                !!! Diese Seite befindet sich im Testmodus. Es kann jederzeit vorkommen, das diese nicht erreichbar ist,
                sowie dass alle Daten gelöscht werden !!!
                <br/>
                Stand: 2022-12-19
            </div>

        </main>
    </div>

};
