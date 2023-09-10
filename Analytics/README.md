## Poetry

В этом проекте используется менеджер пакетов Poetry
```bash
poetry install
poetry run python -m myvote_analytics
```

Эта команда запустит сервер

Вы можете найти документацию на `/api/docs`.

## Docker

Вы можете запустить проект используя следующую команду:

```bash
docker-compose -f deploy/docker-compose.yml --project-directory . up --build
```

Если вы хотите использовать докер для разработки добавте `-f deploy/docker-compose.dev.yml` в докер команду.
Например как тут:

```bash
docker-compose -f deploy/docker-compose.yml -f deploy/docker-compose.dev.yml --project-directory . up --build
```

Эта команда запустит приложение на 8008

Если вы изменяете файлы `poetry.lock` или `pyproject.toml` То нужно пересобрать контейнер:

```bash
docker-compose -f deploy/docker-compose.yml --project-directory . build
```
## Configuration

Это приложение может быть сконфигурировано через переменные окружения

вы можете создать `.env` файл в корневой папке проекта и разместить там свои переменные.

Все переменные должны начинаться с префикса "MYVOTE_ANALYTICS_".

Например в вашем файле "myvote_analytics/settings.py" a variable named like
`random_parameter`, вы должны указать переменную "MYVOTE_ANALYTICS_RANDOM_PARAMETER"
что сконфигурировать значение.

пример.env файла:
```bash
MYVOTE_ANALYTICS_RELOAD="True"
MYVOTE_ANALYTICS_PORT="8000"
MYVOTE_ANALYTICS_ENVIRONMENT="dev"
```
