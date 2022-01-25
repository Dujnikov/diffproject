<?php
session_start();

$message = ''; 
//if (isset($_POST['uploadBtn']) && $_POST['uploadBtn'] == 'Upload')
//{
  if (isset($_FILES['uploadedCRTFile']) && $_FILES['uploadedCRTFile']['error'] === UPLOAD_ERR_OK)
  {
    // get details of the uploaded file
    $fileTmpPath = $_FILES['uploadedCRTFile']['tmp_name'];
    $fileName = $_FILES['uploadedCRTFile']['name'];
    $fileSize = $_FILES['uploadedCRTFile']['size'];
    $fileType = $_FILES['uploadedCRTFile']['type'];
    $fileNameCmps = explode(".", $fileName);
    $fileExtension = strtolower(end($fileNameCmps));

    // sanitize file-name
    //$newFileName =  $fileName . '.' . $fileExtension;

    // check if file has one of the following extensions
    $allowedfileExtensions = array('crt');

    if (in_array($fileExtension, $allowedfileExtensions))
    {
      // directory in which the uploaded file will be moved
      $uploadFileDir = '/home/crt/';
      $dest_path = $uploadFileDir . $fileName;

      if(move_uploaded_file($fileTmpPath, $dest_path)) 
      {
        $message ='File upload success!';
      }
      else 
      {
        $message = 'There was some error moving the file to upload directory. Please make sure the upload directory is writable by web server.';
      }
    }
    else
    {
      $message = 'Upload failed. Allowed file types: ' . implode(',', $allowedfileExtensions);
    }
  }
  else
  {
    $message = 'There is some error in the file upload. Please check the following error.<br>';
    $message .= 'Error:' . $_FILES['uploadedCRTFile']['error'];
  }
//}
$_SESSION['message'] = $message;
header("Location: index.php");
