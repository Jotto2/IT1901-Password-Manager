package app.database;

import java.util.ArrayList;

public interface DatabaseTalker {



    /*
     * userExists checks if a user with a given username exists
     * 
     * @param username username of a user
     * 
     * @return true if a user with the username exists in the database
     */
    public boolean userExists(String username);

    /*
     * checkPassword checks if the password matches the username in the database
     * 
     * 
     * @param username username to be checked
     * @param password password to be checked against username
     * 
     * @return true if the user exists and the password is correct
     */
    public boolean checkPassword(String username, String password);


    /*
     * 
     * insertUser stores a new user in the databse
     * 
     * @param username the username of the new user
     * @param password the password of the new user
     * 
     * @return true if the username was unique and the user was stores successfully
     */
    public boolean insertUser(User user);


    /*
     * 
     * deleteUser deletes a user from the database
     * 
     * @param username the username of the user that is to be deleted
     * 
     * @return true if the user existed and the user was deleted
     */
    public void deleteUser(String username);


    /*
     * getProfiles returns the profiles of a user
     * 
     * @param username the username of the user
     * @param password the password of the user
     * 
     * @return a list of profile objects that has been stored by the user
     */
    public ArrayList<Profile> getProfiles(String username, String password);


    /*
     * 
     * insertProfile stores a new profile
     * 
     * 
     * @param username the user which the profile should be stored with
     * @param profile the new profile object that is to be saved
     * 
     * @return boolean telling wether the operation was succesful or not
     * 
     */
    public boolean insertProfile(String username, Profile profile);


    /*
     * deleteProfile deletes a profile
     * 
     * 
     * 
     * @param username the username that owns the profile
     * 
     * @param profile the profile which is to be deleted
     * 
     * 
     * 
     */
    public void deleteProfile(String username, Profile profile);
}