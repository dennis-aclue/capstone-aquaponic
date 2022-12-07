import React, {useEffect, useState} from 'react';

export default function MemberOverview() {

    const [getUserName, setUserName] = useState("");
    const [getPassword, setPassword] = useState("");
    const [getFirstName, setFirstName] = useState("");
    const [getLastName, setLastName] = useState("");
    const [getEmail, setEmail] = useState("");
    const [getRole, setRole] = useState("");

    useEffect(() => {
            setUserName(JSON.parse(localStorage.getItem('user') || '{}').username);
            setPassword(JSON.parse(localStorage.getItem('user') || '{}').password);
            setFirstName(JSON.parse(localStorage.getItem('user') || '{}').firstName);
            setLastName(JSON.parse(localStorage.getItem('user') || '{}').lastName);
            setEmail(JSON.parse(localStorage.getItem('user') || '{}').email);
            setRole(JSON.parse(localStorage.getItem('user') || '{}').role);
        }
        , []);

    return (
        <>
            <ul>
                <h1>Username: {getUserName}</h1>
                <p>
                    Password: {getPassword.substring(1, 5)}********
                </p>
                <p>
                    Firstname: {getFirstName}
                </p>
                <p>
                    Lastname: {getLastName}
                </p>
                <p>
                    Email: {getEmail}
                </p>

                <p>
                    Role: {getRole}
                </p>
            </ul>
        </>
    )
}
