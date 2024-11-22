package Service;

import DTO.FriendDTO;
import Domain.Friend;
import Domain.Tuple;
import Domain.User;
import Repository.FriendDBRepository;
import Repository.UserDBRepository;
import Utils.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Service {
    private UserDBRepository userDBRepository;
    private FriendDBRepository friendDBRepository;

    public Service(UserDBRepository userDBRepository, FriendDBRepository friendDBRepository) {
        this.userDBRepository = userDBRepository;
        this.friendDBRepository = friendDBRepository;
    }

    public List<Friend> getAllFriends(User user){
        return friendDBRepository.findAllFriends(user, 1);
    }

    public void deleteFriend(int idLoggedUser, FriendDTO friendDTO){
        Tuple<Integer, Integer> idFriend = new Tuple<>(idLoggedUser, friendDTO.getId());
        friendDBRepository.delete(idFriend);
    }

    public User getUserById(Integer id){
        Optional<User> user = userDBRepository.findOne(id);
        if(user.isPresent()){
            return user.get();
        }
        else{
            throw new IllegalArgumentException("User not found!");
        }
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

    public List<User> getNonFriendedUsers(User user){
        List<Friend> friends = friendDBRepository.findAllFriends(user, 0);
        List<Integer> friendsId = friends.stream().map(friend -> friend.getId().getSecond()).toList();
        List<User> users = (List<User>) userDBRepository.findAll();
        users.removeIf(user1 -> friendsId.contains(user1.getId()));
        users.remove(user);
        return users;
    }

    public List<User> getPendingUsers(User user){
        List<Friend> friends = friendDBRepository.findAllRequests(user);
        List<Integer> friendsId = friends.stream().map(friend -> friend.getId().getFirst()).toList();
        List<User> users = (List<User>) userDBRepository.findAll();
        users.removeIf(user1 -> !friendsId.contains(user1.getId()));
        users.remove(user);
        return users;
    }

    public void updateStatus(int idLoggedUser, int idFriend){
        Tuple<Integer, Integer> id = new Tuple<>(idFriend, idLoggedUser);
        Friend friend = new Friend(LocalDateTime.now(), Status.ACCEPTED);
        friend.setId(id);
        friendDBRepository.update(friend);
    }

    public void declineFriend(int idLoggedUser, int idFriend){
        friendDBRepository.delete(new Tuple<>(idFriend, idLoggedUser));
    }

    public void addFriend(int idLoggedUser, int idFriend){
        Friend friend = new Friend(LocalDateTime.now(), Status.PENDING);
        friend.setId(new Tuple<>(idLoggedUser, idFriend));
        friendDBRepository.save(friend);
    }

}
