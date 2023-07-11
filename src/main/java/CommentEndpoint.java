import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.softserve.taf.models.enums.HttpStatus;
import com.softserve.taf.models.placeholder.comment.CommentDto;
import com.softserve.taf.services.common.AbstractWebEndpoint;

/**
 * This class is an implementation of AbstractWebEndpoint and it provides operations
 * for creating, updating, retrieving individual and all comments from the server.
 */
public class CommentEndpoint extends AbstractWebEndpoint {

    /**
     * Default logger to print logs.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Endpoint URL for comments.
     */
    private static final String COMMENTS_END = "/comments";

    /**
     * Endpoint URL for a specific comment resource.
     */
    private static final String COMMENTS_RESOURCE_END = "/comments/{commentID}";

    /**
     * Constructor for CommentEndpoint.
     * @param specification RequestSpecification for the endpoint.
     */
    public CommentEndpoint(RequestSpecification specification) {
        super(specification);
    }

    /**
     * Creates a new comment using the provided CommentDto object.
     * @param commentDto Comment data transfer object containing data for the comment.
     * @return The created comment as a CommentDto object.
     */
    public CommentDto create(CommentDto commentDto) {
        return create(commentDto, HttpStatus.CREATED)
                .extract().as(CommentDto.class);
    }

    /**
     * Creates a new comment using the provided CommentDto object and validates the response status.
     * @param commentDto Comment data transfer object containing data for the comment.
     * @param status Expected HttpStatus after creation.
     * @return ValidatableResponse that includes the response of the operation.
     */
    public ValidatableResponse create(CommentDto commentDto, HttpStatus status) {
        LOGGER.info("Create new Comment");
        return post(
                this.specification,
                COMMENTS_END,
                commentDto)
                .statusCode(status.getCode());
    }

    /**
     * Updates a specific comment using the provided id and CommentDto object.
     * @param id ID of the comment to be updated.
     * @param commentDto Comment data transfer object containing new data for the comment.
     * @return The updated comment as a CommentDto object.
     */
    public CommentDto update(int id, CommentDto commentDto) {
        return update(commentDto, id, HttpStatus.OK)
                .extract().as(CommentDto.class);
    }

    /**
     * Updates a specific comment using the provided id and CommentDto object and validates the response status.
     * @param commentDto Comment data transfer object containing new data for the comment.
     * @param id ID of the comment to be updated.
     * @param status Expected HttpStatus after the update.
     * @return ValidatableResponse that includes the response of the operation.
     */
    public ValidatableResponse update(CommentDto commentDto, int id, HttpStatus status) {
        LOGGER.info("Update Comment by id [{}]", id);
        return put(
                this.specification,
                COMMENTS_RESOURCE_END,
                commentDto,
                id)
                .statusCode(status.getCode());
    }

    /**
     * Retrieves a specific comment by ID.
     * @param id ID of the comment to be retrieved.
     * @return The retrieved comment as a CommentDto object.
     */
    public CommentDto getById(int id) {
        return getById(id, HttpStatus.OK)
                .extract().as(CommentDto.class);
    }

    /**
     * Retrieves a specific comment by ID and validates the response status.
     * @param id ID of the comment to be retrieved.
     * @param status Expected HttpStatus after the retrieval.
     * @return ValidatableResponse that includes the response of the operation.
     */
    public ValidatableResponse getById(int id, HttpStatus status) {
        LOGGER.info("Get Comment by id [{}]", id);
        return get(
                this.specification,
                COMMENTS_RESOURCE_END,
                String.valueOf(id))
                .statusCode(status.getCode());
    }

    /**
     * Retrieves all comments.
     * @return A list of all comments as CommentDto objects.
     */
    public List<CommentDto> getAll() {
        return List.of(getAll(HttpStatus.OK).extract().as(CommentDto[].class));
    }

    /**
     * Retrieves all comments and validates the response status.
     * @param status Expected HttpStatus after the retrieval.
     * @return ValidatableResponse that includes the response of the operation.
     */
    public ValidatableResponse getAll(HttpStatus status) {
        LOGGER.info("Get all Comments");
        ValidatableResponse response = get(this.specification, COMMENTS_END);
        response.statusCode(status.getCode());
        return response;
    }
}
