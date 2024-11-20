package Repository;

import Domain.Friend;
import Domain.Tuple;
import Domain.User;
import Domain.Validators.Validator;
import Utils.Status;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class FriendDBRepository implements Repository<Tuple<Integer, Integer>, Friend> {
    String url;
    String username;
    String password;
    Validator<Friend> validator;

    public FriendDBRepository(String url, String username, String password, Validator<Friend> validator){
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    public Friend createFriendFromResultSet(ResultSet resultSet){
        try{
            Friend friend = new Friend(resultSet.getTimestamp("time_added").toLocalDateTime(), Status.valueOf(resultSet.getString("status")));
            Tuple<Integer, Integer> id = new Tuple<>(resultSet.getInt("id_user1"), resultSet.getInt("id_user2"));
            friend.setId(id);
            return friend;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Friend> findOne(Tuple<Integer, Integer> id) {
        try(Connection connection = DriverManager.getConnection(url, username, password);
        ResultSet resultSet = connection.createStatement().executeQuery(String.format(
                "SELECT * FROM friends WHERE id_user1 = ? AND id_user2 = ?",
                id.getFirst(), id.getSecond()))){
            if(resultSet.next()){
                return Optional.ofNullable(createFriendFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Friend> findAll() {
        List<Friend> friends = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(url, username, password);
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM friends")){
            while(resultSet.next()) {
                friends.add(createFriendFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return friends;
    }

    @Override
    public Optional<Friend> save(Friend entity) {
        String sql = "INSERT INTO friends (id_user1, id_user2, time_added, status) VALUES (?, ?, ?, ?)";
        validator.validate(entity);
        try(Connection connection = DriverManager.getConnection(url, username, password)){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, entity.getId().getFirst());
            preparedStatement.setInt(2, entity.getId().getSecond());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(entity.getDateaAdded()));
            preparedStatement.setString(4, entity.getStatus().toString());

            preparedStatement.execute();
            return Optional.empty();
        } catch (SQLException e) {
            return Optional.ofNullable(entity);
        }
    }

    @Override
    public Optional<Friend> update(Friend entity) {
        String sql = "UPDATE friends SET status = ? WHERE id_user1 = ? AND id_user2 = ?";
        validator.validate(entity);
        try(Connection connection = DriverManager.getConnection(url, username, password)){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, entity.getStatus().toString());
            preparedStatement.setInt(2, entity.getId().getFirst());
            preparedStatement.setInt(3, entity.getId().getSecond());
            if(preparedStatement.executeUpdate() > 0){
                return Optional.empty();
            }
            return Optional.ofNullable(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Friend> delete(Tuple<Integer, Integer> id) {
        String sql = "DELETE FROM friends WHERE id_user1 = ? AND id_user2 = ?";
        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            Optional<Friend> friend = findOne(id);
            if(friend.isPresent()){
                preparedStatement.setInt(1, id.getFirst());
                preparedStatement.setInt(2, id.getSecond());
                preparedStatement.execute();
            }
            return friend;
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public Integer size() {
        return 0;
    }
}
