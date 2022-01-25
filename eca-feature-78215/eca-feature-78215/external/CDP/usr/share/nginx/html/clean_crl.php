<?php
    # Если кнопка нажата
    if( isset( $_POST['clean_crl'] ) )
    {
        array_map("unlink", glob("/home/crl/*/*.crl"));
        echo 'Все CRL удалены!';
    }
?>
