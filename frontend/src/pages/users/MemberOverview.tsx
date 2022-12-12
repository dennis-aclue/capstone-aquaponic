import React, {useEffect, useState} from 'react';
import axios from "axios";
import {useNavigate} from "react-router-dom";

export default function MemberOverview() {

    const [dbId, setDbId] = useState();
    const [userId, setUserId] = useState();
    const [startEditing, setStartEditing] = useState<boolean>(true);
    const [isLoading, setIsLoading] = useState(true);
    const navigate = useNavigate();
    let [countProjects, setCountProjects] = useState(0);

    const [userData, setUserData] = useState({
        username: "",
        firstName: "",
        lastName: "",
        email: "",
        role: "",
    })

    useEffect(() => {
        let db_Id = (JSON.parse(sessionStorage.getItem('user') || '{}').id);
        setDbId(db_Id)
        let user_Id = (JSON.parse(sessionStorage.getItem('user') || '{}').userId);
        setUserId(user_Id);

        axios.get("/user/getUserData/" + db_Id)
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
        axios.put("/user/updateUser/" + dbId, userData)
            .then(() => {
                setStartEditing(true);
                }
            )
            .catch((error) => console.log("Update user ERROR: " + error))
    }

    function deleteAccount() {
        axios.get('/api/projects/userProjectOverview/' + userId)
            .then((response) => {
                countProjects = (response.data.length)
            })
            .then(() => {
                setCountProjects(countProjects);
            })
            .catch((error) => console.log("Get all projects ERROR: " + error))

        if (countProjects > 0) {
            return (<>
                "You can't delete your account, because you have {countProjects} open projects! Please delete your
                projects first!"
                <button className="popup-inner__element button" onClick={() => setIsLoading(true)}>OK</button>
            </>)
        } else {
            return (<>
                Do you really want to delete profile?<br/>
                "{userData.username}" <br/>
                <div className="popup-buttons">
                    This cannot be undone!
                    <button onClick={completeDeleteAccount}>Yes</button>
                </div>
                <button className="popup-inner__element button" onClick={() => setIsLoading(true)}>cancel</button>
            </>)
        }
    }

    function completeDeleteAccount() {
        axios.delete(`/user/delete/` + dbId)
            .then(() => {
                    sessionStorage.removeItem("jwt-token");
                    localStorage.clear();
                    navigate("/login");
                    setIsLoading(false);
                }
            )
            .catch((error) => console.log("Delete user ERROR: " + error))
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
                <button onClick={() => setIsLoading(false)}>delete user</button>
            </div>
        )}


        {!isLoading && (
            <p className="popup">
                <p className="popup-inner">
                    <>{deleteAccount()}</>
                </p>
            </p>
        )}

    </div>

}
