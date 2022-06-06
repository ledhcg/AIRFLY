<?php

require_once "connect.php";
header('Content-Type: application/json; charset=utf-8');

$action = $_POST["action"];
// $action = "update";
if (isset($action)) {
    switch ($action) {
        case "get-all": {
                $sql = "SELECT * FROM users ORDER BY score DESC, birds_killed DESC";
                $query = mysqli_query($conn, $sql);
                $data = mysqli_fetch_all($query, MYSQLI_ASSOC);
                echo json_encode($data);
            break;
        }
        case "update": {
                $email = $_POST["email"];
                $score = $_POST["score"];
                $birds_killed = $_POST["birds_killed"];
                
                // $email = "mail@ledinhcuong.com";
                // $score = 0;
                // $birds_killed = 0;
                
                $sql = "UPDATE users SET 
                    score = '$score',
                    birds_killed ='$birds_killed',
                    time = NOW()
                    WHERE email='$email'";
                if(mysqli_query($conn, $sql)){
                   $data = [
                        "isSuccess" => true,
                        "isExists" => true,
                        "email" => $email,
                        "score" => $score,
                        "birds_killed" => $birds_killed,
                    ];
                    echo json_encode($data);
                } else {
                    $data = [
                        "isSuccess" => false,
                        "isExists" => false,
                    ];
                    echo json_encode($data);
                }
                
            break;
        }
    }
}
