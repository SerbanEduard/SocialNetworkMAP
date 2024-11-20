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

    public void logInUser(String firstName, String lastName){
        Optional<User> user = userDBRepository.logIn(firstName, lastName);
        if(user.isPresent()){
            return;
        }
        else{
            throw new IllegalArgumentException("User not found!");
        }
    }


}
