import axios from "axios";


const login = (username: string, password: string) => {
    return axios.post("/user/login", {
        username,
        password
    })
        .then((response) => {
                if (response.headers.jwttoken) {
                    localStorage.setItem('jwt-token', JSON.stringify(response.headers.jwttoken));
                    sessionStorage.setItem('jwt-token', JSON.stringify(response.headers.jwttoken));
                }
                return response.data;
            }
        );
}


const logout = () => {
    localStorage.removeItem("jwt-token");
    sessionStorage.removeItem("jwt-token");
    console.log("logout: " + getCurrentToken());
};

const getCurrentToken = () => {
    //return JSON.parse(localStorage.getItem('jwt-token') || sessionStorage.getItem('jwt-token')
    return JSON.parse(sessionStorage.getItem('jwt-token') || '{}');
};


const authService = {
    login,
    logout,
    getCurrentToken,
};

export default authService;