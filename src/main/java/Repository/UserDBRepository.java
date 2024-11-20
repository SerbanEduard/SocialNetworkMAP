package Repository;

import Domain.User;
import Domain.Validators.Validator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDBRepository implements Repository<Integer, User>{
    private String url;
    private String username;
    private String password;
    private Validator<User> validator;

    public UserDBRepository(String url, String username, String password, Validator<User> validator){
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    public User createUserFromResultSet(ResultSet resultSet){
        try{
            User user = new User(resultSet.getString("first_name"), resultSet.getString("last_name"));
            user.setId(resultSet.getInt("id"));
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<User> logIn(String firstName, String lastName){
        String sql = "SELECT * FROM users WHERE first_name = ? AND last_name = ?";
        try(Connection connection = DriverManager.getConnection(url, username, password)){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return Optional.ofNullable(createUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findOne(Integer id) {
        try(Connection connection = DriverManager.getConnection(url, username, password);
        ResultSet resultSet = connection.createStatement().executeQuery(String.format("SELECT * FROM users WHERE id = %d", id))){
            if(resultSet.next()){
                return Optional.ofNullable(createUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<User> findAll() {
        List<User> users = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(url, username, password);
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM users")){
            while(resultSet.next()){
                users.add(createUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public Optional<User> save(User entity) {
        String sql = "INSERT INTO users (first_name, last_name) VALUES (?, ?)";
        validator.validate(entity);
        try(Connection connection = DriverManager.getConnection(url, username, password)){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());

            preparedStatement.execute();
            return Optional.empty();
        } catch (SQLException e) {
            return Optional.ofNullable(entity);
        }
    }

    @Override
    public Optional<User> update(User entity) {
        String sql = "UPDATE users SET first_name = ?, last_name = ? WHERE id = ?";
        validator.validate(entity);
        try(Connection connection = DriverManager.getConnection(url, username, password)){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setInt(3, entity.getId());
            if(preparedStatement.executeUpdate() > 0){
                return Optional.empty();
            }
            return Optional.ofNullable(entity);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> delete(Integer id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            Optional<User> user = findOne(id);
            if(user.isPresent()){
                preparedStatement.setInt(1, id);
                preparedStatement.execute();
            }
            return user;
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public Integer size() {
        return 0;
    }
}
