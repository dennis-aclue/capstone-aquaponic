import axios from "axios";

const login = (username: string, password: string) => {
    return axios.post("/user/login", {
        username,
        password
    })
        .then((response) => {
                localStorage.setItem('user', JSON.stringify(response.data));
                if (response.headers.jwttoken) {
                    localStorage.setItem('jwt-token', JSON.stringify(response.headers.jwttoken));
                    sessionStorage.setItem('jwt-token', JSON.stringify(response.headers.jwttoken));
                }
                return response.data;
            }
        );
}

const getUserFromLocalCache = () => {
    return JSON.parse(localStorage.getItem('user') || '{}');
}

const logout = () => {
    sessionStorage.removeItem("jwt-token");
    localStorage.clear();
    console.log("logout: " + getCurrentToken());
};

const getCurrentToken = () => {
    //return JSON.parse(localStorage.getItem('jwt-token') || sessionStorage.getItem('jwt-token')
    return JSON.parse(sessionStorage.getItem('jwt-token') || "null");
};


const authService = {
    login,
    logout,
    getCurrentToken,
    getUserFromLocalCache,
};

export default authService;
