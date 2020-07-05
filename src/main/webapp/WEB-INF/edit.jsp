<html>
<head>
    <title>Edit page</title>
</head>
<body>
<h2> Editing username: ${username} </h2><br>
<h2> Editing first name: ${first_name}</h2><br>
<h2> Editing last name: ${last_name}</h2><br>
<h2> Editing last name: ${dob}</h2><br>
<a href="/"> Back</a>
<form method="post">
    <p>${error}</p>
    <input type="hidden" name="edit_form" value="change_username"/>
    <input type="text" name="new_username"  placeholder="New username" required />
    <input type="submit" name="edit_username" value="edit" required/>
</form>

<form method="post">
    <p>${password_error}</p>
    <input type="password" name="new_password"  placeholder="New password" required />
    <input type="password" name="confirm_password"  placeholder="Confirm password" required />
    <input type="submit" name="edit_password" value="edit" required/>
</form>

<form method="post">
    <input type="text" name="new_first_name"  placeholder="New first name" required />
    <input type="submit" name="edit_first_name" value="edit" required/>
</form>

<form method="post">
    <input type="text" name="new_last_name"  placeholder="New last name" required />
    <input type="submit" name="edit_last_name" value="edit" required/>
</form>

<form method="post">
    <input type="text" name="new_dob"  placeholder="New dob" required />
    <input type="submit" name="edit_dob" value="edit" required/>
</form>


</body>
</html>