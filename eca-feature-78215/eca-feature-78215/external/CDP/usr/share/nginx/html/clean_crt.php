<?php
    # Если кнопка нажата
    if( isset( $_POST['clean_crt'] ) )
    {
        array_map("unlink", glob("/home/crt/*/*.crt"));
        echo 'Все корневые сертификаты удалены!';
    }
?>
