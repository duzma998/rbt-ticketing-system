
DO
$$
BEGIN
    IF
NOT EXISTS (SELECT 1 FROM users WHERE role = 'ADMIN') THEN
        INSERT INTO users (
            username,
            email,
            password_encrypted,
            first_name,
            last_name,
            role,
            created_at,
            updated_at
        ) VALUES (
            'testadmin',
            'testadmin12312@ticketing.system',
            '$2a$10$Ys0Z8C6YtocCUrLlhbaxvOOWVrgSRqXd7ZfjUNnN2ZNUgkTzu3vkK',
            'Test1',
            'Admin1',
            'ADMIN',
            CURRENT_TIMESTAMP,
            CURRENT_TIMESTAMP
        );

        RAISE NOTICE 'Admin successfully created';
ELSE
        RAISE NOTICE 'Admin already exists';
END IF;
END $$;