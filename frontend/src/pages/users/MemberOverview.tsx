import React, {useEffect, useState} from 'react';
import axios from "axios";

export default function MemberOverview() {

    const [id, setId] = useState<String>("");
    const [startEditing, setStartEditing] = useState<boolean>(true);

    const [userData, setUserData] = useState({
        id: "",
        username: "",
        firstName: "",
        lastName: "",
        email: "",
        role: "",
    })

    useEffect(() => {
        const id = (JSON.parse(sessionStorage.getItem('user') || '{}').id);
        setId(id);
        axios.get("/user/getUserData/" + id)
            .then((response) => {
                localStorage.setItem('user', JSON.stringify(response));
                setUserData(response.data)
            })
            .catch((error) => console.log("Get user data ERROR: " + error))
    }, []);

    function cancelEdit() {
        setStartEditing(true);
        window.location.reload();
    }

    function updateUser() {
        axios.put(`/user/updateUser/` + id, userData)
            .then(() => {
                    setStartEditing(true);
                }
            )
            .catch((error) => console.log("Update user ERROR: " + error))
    }

    return <div className="flexColumnCenter">
        <header className="headerStandardStyle">
        </header>
        <h1>Member Overview</h1>
        <form className='project__card'>
            <p className="projectCard__element label">Username</p>
            <p className="projectCard__element inputTernary">{userData.username}</p>
            <p className="projectCard__element label">First name</p>
            <input
                className="projectCard__element input"
                disabled={startEditing}
                type="text"
                id="firstName"
                name="firstName"
                value={userData.firstName}
                onChange={(e) => setUserData({...userData, firstName: e.target.value})}
            />
            <p className="projectCard__element label">Last name</p>
            <input
                className="projectCard__element input"
                disabled={startEditing}
                type="text"
                id="lastName"
                name="lastName"
                value={userData.lastName}
                onChange={(e) => setUserData({...userData, lastName: e.target.value})}
            />
            <p className="projectCard__element label">Email</p>
            <input
                className="projectCard__element input"
                disabled={true}
                type="text"
                id="email"
                name="email"
                value={userData.email}
            />
            <p className="projectCard__element label">Role</p>
            <input
                className="projectCard__element input"
                disabled={true}
                type="text"
                id="role"
                name="role"
                value={userData.role}
            />

        </form>

        {startEditing && (
            <button onClick={() => setStartEditing(false)}>Edit user</button>
        )}

        {!startEditing && (
            <div>
                <button onClick={cancelEdit}>cancel edit</button>
                <button onClick={updateUser}>save</button>
            </div>
        )}

    </div>

}
