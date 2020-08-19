### Условие (Свободная касса)
Требуется реализовать сервис для получения данных об офисах Альфа-Банка и их загруженности.
Сервис должен предоставлять REST API на http://IP:8082.
Файл с описанием API – api.json.
Так как мы не сомневаемся, что вы умейте гуглить, дали вам немного подсказок для задачи и ссылок на статьи, чтобы экономить бесценное время, которого и так немного.

Данные лежат в PostgreSQL.

- Для запуска PostgreSQL:
```
mkdir task3
cd task3
wget https://raw.githubusercontent.com/evgenyshiryaev/alfa-battle-resources/master/task3/docker-compose.yml
wget https://raw.githubusercontent.com/evgenyshiryaev/alfa-battle-resources/master/task3/Dockerfile
wget https://raw.githubusercontent.com/evgenyshiryaev/alfa-battle-resources/master/task3/init_db.sql
docker-compose up -d
```
- Для остановки (в папке task3):
```
docker-compose down
Адрес: IP:5432
DB: alfa_battle
Auth: alfa_battle / qwe123
```
* IP - внешний IP виртуальной машины.
* Все ресурсы лежат тут: https://github.com/evgenyshiryaev/alfa-battle-resources/tree/master/task3

## Задачи
# 1 Получение данных офиса по id

Запрос: GET http://IP:8082/branches/{id}
Ответ:
- 200 Branches
- 404 ErrorResponse (если офис не найден)

```
Пример: GET http://IP:8082/branches/612
200
{
  "id": 612,
  "title": "Мясницкий",
  "lon": 37.6329,
  "lat": 55.7621,
  "address": "Мясницкая ул., 13, стр. 1"
}
```
```
Пример: GET http://IP:8082/branches/1
404
{
  “status”: “branch not found”
}
```

# 2 Получение ближайшего к указанным координатам офиса (по-умному, т.е. считать расстояние между двумя точками на сфере)

Необходимо найти самый ближайший офис к точки основываясь на координатах пользователя Latitude Longitude.

Запрос: GET http://IP:8082/branches/lat=string&lon=string
Ответ: 200 Branches

```
Пример: GET http://IP:8082/branches/lat=55.773284&lon=37.624125
200
{
  "id": 631,
  "title": "Цветной Бульвар",
  "lon": 37.6227,
  "lat": 55.7695,
  "address": "Цветной бул., 16/1",
  "distance": 430
}
```

Distance - дистанция указывается в метрах, при дробных числах, необходимо округлить.

# 3 Предсказание загруженности офиса
Необходимо на основе данных предсказать загруженность любого из офисов в определенный час, а именно необходимо предсказать, сколько клиент потратит время находясь в очереди, ожидая пока его обслужат.

При расчете предсказания использовать медиану.

Запрос: GET http://IP:8082/branches/{id}/predict?dayOfWeek=int&hourOfDay=int
Ответ:
- 200 BranchesWithPredicting
- 404 ErrorResponse (если офис не найден)

```
Пример: GET http://IP:8082/branches/612/predict?dayOfWeek=1&hourOfDay=14
200
{
  "id": 612,
  "title": "Мясницкий",
  "lon": 37.6329,
  "lat": 55.7621,
  "address": "Мясницкая ул., 13, стр. 1",
  "dayOfWeek": 1,
  "hourOfDay": 14,
  "predicting": 117
}
```
dayOfWeek - число, где понедельник это 1, а воскресенье 7
hourOfDay - число от 0 до 23
Predicting - время ожидания, которое надо считать в секундах, а результат округлять.

```
Пример: GET http://IP:8082/branches/1/predict?dayOfWeek=1&hourOfDay=14
404
{
  “status”: “branch not found”
}
```
Предсказание загруженности в данном случае предлагаем считать через медиану.
Библиотека, которая вам может в этом помочь ```apache-commons-math3```
