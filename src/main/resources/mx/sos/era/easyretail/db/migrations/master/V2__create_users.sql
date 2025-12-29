CREATE TABLE usuarios (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(200) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    rol VARCHAR(20) NOT NULL
);

INSERT INTO usuarios(usuario, password, nombre, rol)
VALUES(
       'SUPERVISOR',
       '$2a$12$ByuSmxezOXUy1kegvI7jyOKTCzExFtELhXsThzaGZqfI0d13S8r12',
       '',
        'ADMIN'
      );