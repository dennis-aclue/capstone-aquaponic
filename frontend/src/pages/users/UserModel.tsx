export type UserModel = {
    firstName: string;
    lastName: string;
    username: string;
    profilePicture: string;
    email: string;
    lastLogin: string;
    role: string;
    authorities: string[];
    joinDate: string;
    isActivated: boolean;
    isLocked: boolean;
}