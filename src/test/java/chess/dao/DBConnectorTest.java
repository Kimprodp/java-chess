package chess.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class DBConnectorTest {

    DBConnector dbConnector = new DBConnector();

    @Test
    public void connection() {
        try (final var connection = dbConnector.getConnection()) {
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
