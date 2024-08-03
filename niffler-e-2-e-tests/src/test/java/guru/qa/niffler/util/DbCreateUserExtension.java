package guru.qa.niffler.util;

import guru.qa.niffler.data.entity.UserAuthEntity;
import guru.qa.niffler.data.entity.UserEntity;
import guru.qa.niffler.data.repository.UserRepository;
import guru.qa.niffler.data.repository.UserRepositorySpringJdbc;
import guru.qa.niffler.data.repository.lodding.JsonAllureAppender;
import guru.qa.niffler.jupiter.extension.AbstractCreateUserExtension;
import guru.qa.niffler.model.UserJson;

public class DbCreateUserExtension extends AbstractCreateUserExtension {

    UserRepository userRepository = new UserRepositorySpringJdbc();

    private final JsonAllureAppender jsonAppender = new JsonAllureAppender();

    @Override
    public UserJson createUser(UserJson user) {
        UserAuthEntity userAuthEntity = UserAuthEntity.fromJson(user);
        userRepository.createUserInAuth(userAuthEntity);
        jsonAppender.logJson("userAuthEntity", userAuthEntity);

        UserEntity userEntity = UserEntity.fromJson(user);
        userRepository.createUserInUserdata(userEntity);
        jsonAppender.logJson("userEntity", userEntity);
        return user;
    }
}