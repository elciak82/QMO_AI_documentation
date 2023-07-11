import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.softserve.taf.models.enums.HttpStatus;
import com.softserve.taf.models.placeholder.user.UserDto;
import com.softserve.taf.services.common.AbstractWebEndpoint;

/**
 * This class is an implementation of AbstractWebEndpoint and it provides operations
 * for creating, updating, retrieving individual and all users from the server.
 */
public class UserEndpoint extends AbstractWebEndpoint {

    /**
     * Default logger to print logs.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Endpoint URL for users.
     */
    private static final String USERS_END = "/users";

    /**
     * Endpoint URL for a specific user resource.
     */
    private static final String USERS_RESOURCE_END = "/users/{userID}";

    /**
     * Constructor for UserEndpoint.
     *
     * @param specification RequestSpecification for the endpoint.
     */
    public UserEndpoint(RequestSpecification specification) {
        super(specification);
    }

    /**
     * Creates a new user using the provided UserDto object.
     *
     * @param userDto User data transfer object containing data for the user.
     * @return The created user as a UserDto object.
     */
    public UserDto create(UserDto userDto) {
        return create(userDto, HttpStatus.CREATED)
                .extract().as(UserDto.class);
    }

    /**
     * Creates a new user using the provided UserDto object and validates the response status.
     *
     * @param userDto User data transfer object containing data for the user.
     * @param status  Expected HttpStatus after creation.
     * @return ValidatableResponse that includes the response of the operation.
     */
    public ValidatableResponse create(UserDto userDto, HttpStatus status) {
        LOGGER.info("Create new User");
        return post(
                this.specification,
                USERS_END,
                userDto)
                .statusCode(status.getCode());
    }

    /**
     * Updates a specific user using the provided id and UserDto object.
     *
     * @param id      ID of the user to be updated.
     * @param userDto User data transfer object containing new data for the user.
     * @return The updated user as a UserDto object.
     */
    public UserDto update(int id, UserDto userDto) {
        return update(userDto, id, HttpStatus.OK)
                .extract().as(UserDto.class);
    }

    /**
     * Updates a specific user using the provided id and UserDto object and validates the response status.
     *
     * @param userDto User data transfer object containing new data for the user.
     * @param id      ID of the user to be updated.
     * @param status  Expected HttpStatus after the update.
     * @return ValidatableResponse that includes the response of the operation.
     */
    public ValidatableResponse update(UserDto userDto, int id, HttpStatus status) {
        LOGGER.info("Update User by id [{}]", id);
        return put(
                this.specification,
                USERS_RESOURCE_END,
                userDto,
                id)
                .statusCode(status.getCode());
    }

    /**
     * Retrieves a specific user by ID.
     *
     * @param id ID of the user to be retrieved.
     * @return The retrieved user as a UserDto object.
     */
    public UserDto getById(String id) {
        return getById(id, HttpStatus.OK)
                .extract().as(UserDto.class);
    }

    /**
     * Retrieves a specific user by ID and validates the response status.
     *
     * @param id     ID of the user to be retrieved.
     * @param status Expected HttpStatus after the retrieval.
     * @return ValidatableResponse that includes the response of the operation.
     */
    public ValidatableResponse getById(String id, HttpStatus status) {
        LOGGER.info("Get User by id [{}]", id);
        return get(
                this.specification,
                USERS_RESOURCE_END,
                id)
                .statusCode(status.getCode());
    }

    /**
     * Retrieves all users.
     *
     * @return A list of all users as UserDto objects.
     */
    public List<UserDto> getAll() {
        return List.of(getAll(HttpStatus.OK).extract().as(UserDto[].class));
    }

    /**
     * Retrieves all users and validates the response status.
     *
     * @param status Expected HttpStatus after the retrieval.
     * @return ValidatableResponse that includes the response of the operation.
     */
    public ValidatableResponse getAll(HttpStatus status) {
        LOGGER.info("Get all Users");
        ValidatableResponse response = get(this.specification, USERS_END);
        response.statusCode(status.getCode());
        return response;
    }
}
