package Domain.Validators;

import Domain.User;

public class UserValidator implements Validator<User>{

    @Override
    public void validate(User user) throws ValidationException {
        System.out.println("merge");
        try{
            if(user.getFirstName().equals("") || user.getLastName().equals("")){
                throw new ValidationException("First name or last name is empty!");
            }
            Integer.parseInt(user.getFirstName());
            Integer.parseInt(user.getLastName());
            throw new ValidationException("First name or last name can't be numbers!");
        }
        catch (NumberFormatException e){

        }
    }
}
