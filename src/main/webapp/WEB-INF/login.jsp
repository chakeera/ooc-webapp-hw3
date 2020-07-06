<html>
<head>
    <style>
        body {
            background-image: url('https://image.freepik.com/free-vector/english-primrose-flower-leaves-small-petal-seamless-random-repeat-vector-pattern-background_58717-198.jpg');
        }
    </style>
    <title>Edit page</title>
</head>
<body>
<h1 style="text-align:center">Welcome!!!</h1>
<div align="center">
<form action="/login" method="post">
    Username:<br/>
    <input type="text" name="username"/>
    <br/>
    Password:<br/>
    <input type="password" name="password">
    <br><br>
    <input type="submit" value="Submit">
</form>
    <p>${error}</p>
</div>
</body>
</html>