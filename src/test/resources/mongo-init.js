db.createUser(
    {
        user: "testcontainer_user",
        pwd: "testcontainer_pwd",
        roles: [
            {
                role: "readWrite",
                db: "testcontainer_db"
            }
        ]
    }
);