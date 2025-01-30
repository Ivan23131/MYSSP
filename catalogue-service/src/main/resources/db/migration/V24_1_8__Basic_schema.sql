CREATE TABLE "user" (
    id SERIAL PRIMARY KEY,  -- Уникальный идентификатор пользователя
    username VARCHAR(50) UNIQUE NOT NULL,  -- Уникальное имя пользователя
    email VARCHAR(100) UNIQUE NOT NULL,  -- Уникальный email пользователя
    password_hash VARCHAR(255) NOT NULL,  -- Хэш пароля пользователя
    first_name VARCHAR(50),  -- Имя пользователя
    last_name VARCHAR(50),  -- Фамилия пользователя
    date_of_birth DATE,  -- Дата рождения пользователя
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP  -- Дата и время создания записи
);
