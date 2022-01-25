<?php
session_start(); 
?>
<!DOCTYPE html>
<html>
<head>
  <title>File Upload</title>
</head>
<body>
  <?php
    if (isset($_SESSION['message']) && $_SESSION['message'])
    {
      printf('<b>%s</b>', $_SESSION['message']);
      unset($_SESSION['message']);
    }
  ?>
  <form method="POST" action="upload.php" enctype="multipart/form-data">
    <div>
      <span>Upload CRL File via POST:</span>
      <input type="file" name="uploadedFile" />
    </div>

    <input type="submit" name="uploadBtn" value="Upload CRL" />
  </form>

  <p></p>

  <form method="POST" action="upload_crt.php" enctype="multipart/form-data">
    <div>
      <span>Upload CRT File via POST:</span>
      <input type="file" name="uploadedCRTFile" />
    </div>

    <input type="submit" name="uploadCRTBtn" value="Upload CRT" />
  </form>

  <p></p>
  <p></p>

  <form method="POST" action="clean_crl.php" enctype="multipart/form-data">
    <input type="submit" name="clean_crl" value="Очистить CRL" />
  </form>


  <p></p>

  <form method="POST" action="clean_crt.php" enctype="multipart/form-data">
    <input type="submit" name="clean_crt" value="Очистить CRT" />
  </form>


</body>
</html>
