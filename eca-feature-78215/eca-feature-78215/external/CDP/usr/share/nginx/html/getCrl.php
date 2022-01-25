<?php

	$caName = $_GET['CaName'];
	$file = './uploaded/crl/' . $caName . '/' . $caName . '.crl';

	if(!file_exists($file)){ // file does not exist
		die('file not found');
	} else {
		$fileNameCrl = $caName . '.crl';
		header("Cache-Control: public");
		header("Content-Description: File Transfer");
		header("Content-Disposition: attachment; filename=$fileNameCrl");
		header("Content-Type: application/zip");
		header("Content-Transfer-Encoding: binary");
			// read the file from disk
		readfile($file);
	}

