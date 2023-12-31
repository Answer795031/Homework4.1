CREATE TABLE car (
    id BIGSERIAL primary key,
    car_brand TEXT,
    car_model TEXT,
    car_price BIGSERIAL
);
CREATE TABLE person (
    id BIGSERIAL primary key,
    person_name TEXT,
    person_age INT,
    driver_license BOOL,
    car_id BIGSERIAL,
    FOREIGN KEY (car_id) REFERENCES car(id)
);