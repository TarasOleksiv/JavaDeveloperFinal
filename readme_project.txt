_______________________________________________________________________________

Проект доступний на heroku за наступним лінком:

    https://account-work-time.herokuapp.com/...

        swagger:    https://account-work-time.herokuapp.com/swagger-ui.html
                    username: admin
                    password: admin

        rest api:   https://account-work-time.herokuapp.com/api/...
                    (див. документацію).
_______________________________________________________________________________

            Структура проекту:

documentation :
    -- AppScheme.pdf : діаграми проекту
    -- DBscheme.pdf : схема бази
    -- REST_API_review.pdf : опис api проекту з допомогою swagger,
                                swagger можна запустити за лінком вище
    -- Spring_security.pdf : опис security
                                Basic Authentication
                                roles, users, passwords, permissions

src/main/java/...../ :
    -- json : спеціальні серіалайзери та десеріалайзери для деяких сутностей
    -- model : моделі змаплені з таблицями БД
    -- report.entity : моделі, не зв'язані з таблицями БД;
                        використовуються для генерації звітів
    -- repository : jpa репозиторії
    -- rest : rest контролери
    -- security :
        -- SecurityConfig : клас конфігурації spring security
    -- service : сервіси
        -- email :
            -- EmailService : сервіс відправки пошти
        -- scheduler :
            -- SchedulerService : сервіс шедулера з використанням крону
        -- .... :
            решта сервісів
    -- swagger :
        -- SwaggerConfig : файл конфігурації swagger
    -- utils :
        -- PasswordEncoderGenerator : клас для генерації хешованих стрінгів
                                      (використаний для генерації закриптованих паролів для стартового скрипта заливки даних у базу)
    -- validator : класи для валідації

    -- resources
        -- database - скрипти ініціалізації БД:
            -- initDB.sql - створює всі необхідні таблиці на базі.
            -- populateDB.sql - заповнює даними таблиці.
        -- application.properties - файл конфігурації проекту :
            -- spring.datasource - конфіг конекту до бази
            -- spring.jpa - тип діалекту
            -- spring.mail - конфіг пошти

readme_project.txt - даний файл з описом структури проекту.

__________________________________________________________________________________________________________________

            Демонстрація роботи проекту.

    Проект доступний за лінком
        https://account-work-time.herokuapp.com/...

    Для ознайомленням з api можна проглянути документацію, описану вище,
        а також, відкрити лінк swagger.

    Робота всіх api перевірялась з допомогою Postman.


