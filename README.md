## RUN

### Local Develop Setting

1. `make start local`
2. `make run`  or  Application start To IDE

> 로컬 SQS 로 메세지를 보내고 싶다면?
> `make send-message`

## API

### 리워드 프로그램 생성 API

* 요청 URL
```shell
POST /rewards/programs
```
* 요청 바디
```json
{
  "group": {
    "name": "리워드 프로그램 이름",
    "description": "리워드 프로그램 설명",
    "key": "XXX리워드"
  },
  "target": "USER | GUEST",
  "definition": {
    "reward": {
      "type": "POINT | Other Type...",
      "expire": {
        "type": "INFINITY | STATIC | DYNAMIC",
        "expiredAt": "2024-03-22T10:00:00", // (STATIC 타입에만 해당)
        "additionalInfo": { // (DYNAMIC 타입에만 해당)
          "queryType": "HTTP",
          "queryMethod": "POST",
          "query": "http://redash.nopecho.co.kr/api/queries/887/results",
          "queryBody": "{\"parameters\":{\"clubId\":\"$productId$\"}}",
          "accessField": "query_result.data.rows.expired_at"
        }
      },
      "amount": 5000
    },
    "delivery": {
      "type": "DIRECT | RESERVE",
      "additionalInfo": { // (RESERVE 타입에만 해당)
        "queryType": "HTTP",
        "queryMethod": "POST",
        "query": "http://redash.nopecho.co.kr/api/queries/887/results",
        "queryBody": "{\"parameters\":{\"clubId\":\"$productId$\"}}",
        "accessField": "query_result.data.rows.duedate"
      }
    }
  },
  "policyIdList": [1, 2],
  "startedAt": "2023-03-04T10:00:00",
  "endedAt": "2024-03-22T10:00:00"
}

```
* 응답 바디

201 created
```json
{
  "rewardProgramId": 12345
}

```

400 bad request
```json
{
  "message" : "ERROR MESSAGE!"
}
```