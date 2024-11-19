package Domain.Validators;

import Domain.Friend;

public class FriendValidator implements Validator<Friend>{

    @Override
    public void validate(Friend friend) throws ValidationException {
        if(friend.getId().getFirst().equals(friend.getId().getSecond())){
            throw new ValidationException("The id's cannot be the same!");
        }
    }
}
