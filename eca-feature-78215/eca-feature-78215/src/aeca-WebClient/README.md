После клонирования репозитория на машину фронтенд-разработчика необходимо установить зависимости командой:
`npm install`

### Локальный запуск приложения для разработки

`npm start`

### Production сборка

`npm run build`

### Информация для devops

для деплоя фронтенд-приложения необходимо следовать следующему алгоритму:
1) установить зависимости `npm install` (для работы с менеджером пакетов необходимо наличие на машине NodeJS)
2) собрать приложение командой `npm run build`
3) сборщик создаст папку `build`, где будут все файлы продакшн-сборки
4) добавить туда файл `.htaccess` из каталога `public` (необходим для работы роутинга на веб-сервере, проверялся на apache, предположительно что в nginx тоже будет работать)
5) все содержимое папки скопировать в каталог `www` на веб-сервере

## Visual Studio Code

### TypeScript

Указать путь к sdk TypeScript:

```json
{
  "typescript.tsdk": "node_modules/typescript/lib"
}
```

### eslint

Для работы линтера в редакторе vscode, необходимо установить пакеты [eslint](https://marketplace.visualstudio.com/items?itemName=dbaeumer.vscode-eslint), [prettier](https://marketplace.visualstudio.com/items?itemName=esbenp.prettier-vscode) и добавить следующие строки в [глобальный settings.json](https://code.visualstudio.com/docs/getstarted/settings#_settings-file-locations) или в окружение проекта - `.vscode/settings.json`.

_settings.json_

```json
{
  "eslint.alwaysShowStatus": true,
  "eslint.workingDirectories": ["./src"],
  "eslint.validate": ["typescript", "typescriptreact"],
  "editor.codeActionsOnSave": {
    "source.fixAll.eslint": true
  }
}
```
