CREATE DATABASE IF NOT EXISTS `chess` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE `position` (
    position_id INT AUTO_INCREMENT PRIMARY KEY,
    lettering VARCHAR(1) NOT NULL,
    numbering VARCHAR(5) NOT NULL
);

CREATE TABLE piece (
    piece_id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(6) NOT NULL,
    camp VARCHAR(5) NOT NULL
);

CREATE TABLE piece_position (
    piece_position_id INT AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE piece_position_entry (
    piece_position_id INT,
    position_id INT,
    piece_id INT,
    PRIMARY KEY (piece_position_id, position_id),
    FOREIGN KEY (piece_position_id) REFERENCES piece_position(piece_position_id),
    FOREIGN KEY (position_id) REFERENCES `position`(position_id),
    FOREIGN KEY (piece_id) REFERENCES piece(piece_id)
);

CREATE TABLE chess_game (
    chess_game_id INT AUTO_INCREMENT PRIMARY KEY,
    piece_position_id INT,
    status_value INT,
    FOREIGN KEY (piece_position_id) REFERENCES piece_position(piece_position_id)
);
