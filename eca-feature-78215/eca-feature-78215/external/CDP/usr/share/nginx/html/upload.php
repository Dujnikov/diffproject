<?php
session_start();

$message = ''; 
//if (isset($_POST['uploadBtn']) && $_POST['uploadBtn'] == 'Upload')
//{
  if (isset($_FILES['uploadedFile']) && $_FILES['uploadedFile']['error'] === UPLOAD_ERR_OK){
	  $caName = $_GET['CaName'];
	  // $CRLNameSuffix = $_GET['CRLNameSuffix'];
	  // $DeltaCRLAllowed = $_GET['DeltaCRLAllowed'];
    // get details of the uploaded file
    $fileTmpPath = $_FILES['uploadedFile']['tmp_name'];
    $fileName = $_FILES['uploadedFile']['name'];
    $fileSize = $_FILES['uploadedFile']['size'];
    $fileType = $_FILES['uploadedFile']['type'];
    $fileNameCmps = explode(".", $fileName);
    $fileExtension = strtolower(end($fileNameCmps));
// 	$dateNow = date('_d.m.Y-H:i');
	$fileName = $caName . '.' . $fileExtension;

    // sanitize file-name
    //$newFileName =  $fileName . '.' . $fileExtension;

    // check if file has one of the following extensions
    $allowedfileExtensions = array('crt','crl');

    if (in_array($fileExtension, $allowedfileExtensions)){
		if(strcasecmp($fileExtension, 'crl') == 0){
			$dir = './uploaded/crl/' . $caName . '/';
			if(!is_dir($dir)) {
				mkdir($dir, 0777, true);
			}
		  // directory in which the uploaded file will be moved
		  $dest_path = $dir . $fileName;

			  if(move_uploaded_file($fileTmpPath, $dest_path)){
				$message ='File upload success!';
			  }
			  else{
				$message = 'There was some error moving the file to upload directory. Please make sure the upload directory is writable by web server.';
			  }
		}
		if(strcasecmp($fileExtension, 'crt') == 0){
			$dir = './uploaded/crt/' . $caName . '/';
			if(!is_dir($dir)){
				mkdir($dir, 0777, true);
			}
		  // directory in which the uploaded file will be moved
		  $dest_path = $dir . $fileName;

			  if(move_uploaded_file($fileTmpPath, $dest_path)){
				$message ='File upload success!';
			  }
			  else{
				$message = 'There was some error moving the file to upload directory. Please make sure the upload directory is writable by web server.';
			  }
		}
	}
	else{
	  $message = 'Upload failed. Allowed file types: ' . implode(',', $allowedfileExtensions);
	}
  }
  else{
    $message = 'There is some error in the file upload. Please check the following error.<br>';
    $message .= 'Error:' . $_FILES['uploadedFile']['error'];
  }
//}
$_SESSION['message'] = $message;
header("Location: index.php");
