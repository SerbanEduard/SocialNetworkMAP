package Service;

import Domain.Friend;
import Domain.Tuple;
import Domain.User;
import Repository.Repository;
import Repository.UserDBRepository;

import java.util.Optional;

public class Service {
    private UserDBRepository userDBRepository;
    private Repository<Tuple<Integer, Integer>, Friend> friendDBRepository;

    public Service(UserDBRepository userDBRepository, Repository<Tuple<Integer, Integer>, Friend> friendDBRepository) {
        this.userDBRepository = userDBRepository;
        this.friendDBRepository = friendDBRepository;
    }

    public Iterable<Friend> getAllFriends(User user){
        //return friendDBRepository.findAllFriends();
        return null;
    }

    public User logInUser(String firstName, String lastName){
        Optional<User> user = userDBRepository.logIn(firstName, lastName);
        if(user.isPresent()){
            return user.get();
        }
        else{
            throw new IllegalArgumentException("User not found!");
        }
    }

    public User signUpUser(String firstName, String lastName){
        if(!userDBRepository.userExist(firstName, lastName)){
            User user = new User(firstName, lastName);
            Optional<User> savedUser = userDBRepository.save(user);
            if(savedUser.isPresent()){
                return user;
            }
        }
        else{
            throw new IllegalArgumentException("User already exists!");
        }
        return null;
    }


}
