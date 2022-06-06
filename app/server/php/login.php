<?php

require_once "connect.php";
header('Content-Type: application/json; charset=utf-8');

$action = $_POST["action"];

// $action = "login";

$data = [];
if (isset($action)) {
    switch ($action) {
        case "login": {
            $data = [
                "isSuccess" => false,
                "isExists" => false
            ];
            $email = $_POST["email"];
            $password = md5($_POST["password"]);
            
            // $email = "mail@ledinhcuong.com";
            // $password = md5("Lc01031999");
            
            if (isset($email) && isset($password)) {
                $sql = "SELECT * FROM users WHERE email='$email' AND password='$password'";
                $query = mysqli_query($conn, $sql);
                $rows = mysqli_num_rows($query);
                $data_user = mysqli_fetch_array($query);
                if ($rows > 0) {
                    $data = [
                        "isSuccess" => true,
                        "isExists" => true,
                        "name" => $data_user["name"],
                        "email" => $data_user["email"],
                        "phone" => $data_user["phone"],
                        "score" => $data_user["score"],
                        "birds_killed" => $data_user["birds_killed"],
                        "time" => $data_user["time"]
                    ];
                }
            }
            break;
        }
        case "create": {
            $data = [
                "isSuccess" => false
            ];
            
            $name = $_POST["name"];
            $email = $_POST["email"];
            $password = md5($_POST["password"]);
            $phone = $_POST["phone"];
            
            // $name = "Cuong";
            // $email = "mail@ledinhcuong.com";
            // $password = md5("Lc01031999");
            // $phone = "+79837336161";
            
            if (isset($email) 
                && isset($password)
                && isset($name)
                && isset($phone)) {
                $sql = "INSERT INTO users (
                    name,
                    phone,
                    email,
                    password
                    )
                    VALUES (
                    '$name',
                    '$phone',
                    '$email',
                    '$password'
                    )";
                $query = mysqli_query($conn, $sql);
                if($query){
                    $data = [
                        "isSuccess" => true
                    ];
                }
            }
            break;
        }
    }
}
    
echo json_encode($data);
