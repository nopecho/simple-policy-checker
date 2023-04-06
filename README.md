# Simply Domain Policy Server

### overview

```json
{
  "name": "정책",
  "description": "정책 설명",
  "statements": [
    {
      "supportFactorKeys": [
        "factor_variable1",
        "factor_variable2"
      ],
      "actions": [
        {
          "name": "정책 만족시 수행 되야하는 액션",
          "type": "WEBHOOK",
          "template": {
            "method": "POST",
            "url": "http://foo.api.server/bar",
            "body": "{\"foo\":\"$factor_variable1\", \"bar\":\"$factor_variable2\"}"
          }
        }
      ],
      "conditions": [
        {
          "name": "정책 조건",
          "description": "조건 설명",
          "specs": [
            {
              "actual": {
                "description": "비교값 설명",
                "operatorType": "STRING",
                "value": {
                  "method": "GET",
                  "url": "http://foo.api.server/bars=$factor_variable1",
                  "body": "",
                  "accessField": "response.json.access.field"
                }
              },
              "operator": "string.equals",
              "expect": {
                "operatorType": "STRING",
                "value": "expected value"
              }
            }
          ]
        }
      ]
    }
  ]
}
```

위 json 은 정책 설정을 위한 예시 형식 입니다.
<br>

* `statements`: *정책의 세부 사항*
    * `supportFactorKeys`: *해당 구문이 지원하는 특정 factor 변수*
    * `actions`: *정책이 충족될 때 실행되어야 하는 액션*
      * `template`: *액션을 수행하기 위한 템플릿*
    * `conditions` : *정책의 조건*
      * `specs`: *조건의 세부 사항*

statements 배열은 정책의 세부 사항을 담고 있습니다. 

supportFactorKeys: factor_variable1과 factor_variable2라는 두 개의 변수를 포함하고 있습니다.

actions 배열은 정책이 충족될 때 실행되어야 하는 액션을 담고 있습니다.
액션의 이름(name), 유형(type), 템플릿(template)이 있습니다.

템플릿은 HTTP POST 메서드를 사용하여 "http://foo.api.server/bar" URL로 요청을 보내며,
요청 본문(body)에는 factor_variable1과 factor_variable2 값을 포함합니다.

conditions 배열은 정책을 충족하기 위한 조건들을 담고 있습니다.

specs 배열은 조건의 세부 사항을 담고 있습니다.

actual 객체는 비교할 값에 대한 정보를 포함하고 있습니다. 
값은 HTTP GET 메서드를 사용하여 "http://foo.api.server/bars" URL로 요청을 보내고, 응답에서 특정 필드를 가져옵니다.

operator 는 비교 연산자를 나타냅니다. 여기서는 문자열 비교(string.equals)를 사용합니다.

expect 객체는 예상되는 값에 대한 정보를 포함하고 있습니다.
