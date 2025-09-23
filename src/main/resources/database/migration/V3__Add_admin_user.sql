
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
            'system',
            'admin@ticketing.system',
            '$2a$10$Ys0Z8C6YtocCUrLlhbaxvOOWVrgSRqXd7ZfjUNnN2ZNUgkTzu3vkK',
            'Technical',
            'Admin',
            'ADMIN',
            CURRENT_TIMESTAMP,
            CURRENT_TIMESTAMP
        );

        RAISE NOTICE 'Admin successfully created';
ELSE
        RAISE NOTICE 'Admin already exists';
END IF;
END $$;