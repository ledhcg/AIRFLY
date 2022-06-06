<?php
$servername = "localhost";
$database = "u305683932_airplay";
$username = "u305683932_airplay";
$password = "Lc01031999";
// Create connection
$conn = mysqli_connect($servername, $username, $password, $database);
// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}
// // echo "Connected successfully";
// mysqli_close($conn);
?>