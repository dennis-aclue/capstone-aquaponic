package de.voelkldennis.backend.jwt.constant;
import static de.voelkldennis.backend.jwt.constant.UserImplConstant.*;

public record Authority() {

    public static final String[] USER_AUTHORITIES = {USER_READ};
    public static final String[] MANAGER_AUTHORITIES = {USER_READ, USER_UPDATE};
    public static final String[] ADMIN_AUTHORITIES = {USER_READ, USER_CREATE, USER_UPDATE};
    public static final String[] SUPER_ADMIN_AUTHORITIES = {USER_READ, USER_CREATE, USER_UPDATE, USER_DELETE};
}
