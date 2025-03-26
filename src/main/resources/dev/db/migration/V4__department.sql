CREATE TABLE department (
                            id INT PRIMARY KEY AUTO_INCREMENT,
                            department_code VARCHAR(30) NOT NULL UNIQUE,
                            name VARCHAR(100) NOT NULL UNIQUE,
                            address VARCHAR(200),
                            created_at DATETIME,
                            updated_at DATETIME
);
