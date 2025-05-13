package oopproject.rpmsfinal;

public class SessionNative {
    private static String userType;
    private static String username;
    private static String userId;
    private static User user;
    private static String createUserType;
    private static String notifyUserType;

    public static void setUserType(String type) {
        userType = type;
    }
    public static String getUserType() {
        return userType;
    }

    public static void setCreateUserType(String type) { createUserType = type;}
    public static String getCreateUserType() {
        return createUserType;
    }

    public static void setNotifyUserType(String type) { notifyUserType = type;}
    public static String getNotifyUserType() {
        return notifyUserType;
    }


    public static String getUsername() {
        return username;
    }
    public static void setUsername(String name) {
        username = name;
    }

    public static String getUserId() {
        return userId;
    }
    public static void setUserId(String id) {
        userId = id;
    }

    public static User getCurrentUser(){
        return user;
    }
    public static void setCurrentUser(User newUser){
        user = newUser;
    }


}

