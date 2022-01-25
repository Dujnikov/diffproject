<?php

	$caName = $_GET['CaName'];
	$file = './uploaded/crt/' . $caName . '/' . $caName . '.crt';

	if(!file_exists($file)){ // file does not exist
		die('file not found');
	} else {
		$fileNameCrt = $caName . '.crt';
		header("Cache-Control: public");
		header("Content-Description: File Transfer");
		header("Content-Disposition: attachment; filename=$fileNameCrt");
		header("Content-Type: application/zip");
		header("Content-Transfer-Encoding: binary");
			// read the file from disk
		readfile($file);
	}

