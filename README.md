# Simply Domain Policy Server

## overview

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

`statements` 배열은 정책의 세부 사항을 담고 있습니다. 

`supportFactorKeys`: factor_variable1과 factor_variable2라는 두 개의 변수를 포함하고 있습니다.

`actions` 배열은 정책이 충족될 때 실행되어야 하는 액션을 담고 있습니다.

`template` 은 HTTP POST 메서드를 사용하여 "http://foo.api.server/bar" URL로 요청을 보내며,
요청 본문(body)에는 factor_variable1과 factor_variable2 값을 포함합니다.

`conditions` 배열은 정책을 충족하기 위한 조건들을 담고 있습니다.

`specs` 배열은 조건의 세부 사항을 담고 있습니다.

`actual` 객체는 비교할 값에 대한 정보를 포함하고 있습니다. 
값은 HTTP GET 메서드를 사용하여 "http://foo.api.server/bars" URL로 요청을 보내고, 응답에서 특정 필드를 가져옵니다.

`operator` 는 비교 연산자를 나타냅니다. 여기서는 문자열 비교(string.equals)를 사용합니다.

`expect` 객체는 예상되는 값에 대한 정보를 포함하고 있습니다.

---

## 사용 사례
이 정책 형식은 다양한 시나리오에 적용할 수 있습니다. 예를 들어 다음과 같은 상황에서 사용할 수 있습니다.
<br>

1. 특정 API로부터 받은 데이터가 기대한 값과 일치할 때, 다른 서비스에 알림을 보내거나 데이터를 저장하는 등의 작업을 수행하고 싶은 경우


2. 사용자의 특정 행동에 대한 로그를 수집하고, 해당 행동이 특정 기준에 부합할 때 외부 서비스에 전송하고자 하는 경우


3. 여러 조건을 동시에 만족시키는 경우에만 특정 액션을 수행하도록 하여, 다양한 업무 프로세스를 구현하고자 하는 경우


## 확장성
이 정책 형식은 확장성을 고려하여 설계되었습니다. 다음과 같은 방법으로 정책을 확장하거나 수정할 수 있습니다.
<br>

1. 추가 조건을 만들어 `conditions` 배열에 추가함으로써, 다양한 조건을 검사할 수 있습니다. 이를 통해 더욱 복잡한 시나리오를 구현할 수 있습니다.


2. `actions` 배열에 새로운 액션을 추가하여, 조건이 충족되었을 때 여러 가지 액션을 동시에 수행하도록 할 수 있습니다. 이렇게 하면, 한 가지 이벤트에 대해 다양한 처리를 동시에 수행할 수 있습니다.


3. `specs` 내의 `operator`를 변경함으로써, 다양한 비교 연산자를 사용하여 조건 검사를 할 수 있습니다. 예를 들어, 숫자형 데이터를 비교하고 싶은 경우 `number.equals`, `number.greaterThan` 등의 연산자를 사용할 수 있습니다.


## 한계 및 개선 방향
본 정책 형식은 유연하고 확장성이 높다는 장점이 있지만, 몇 가지 한계도 존재합니다.
<br>

1. 복잡한 조건 처리의 어려움: 이 정책 형식은 단순한 조건 검사에는 적합하지만, 조건 간의 관계가 복잡해질수록 구현이 어려워질 수 있습니다. 이런 경우, 정책의 구조를 개선하거나 추가적인 연산자를 도입하여 복잡한 조건 처리를 간소화할 수 있습니다.


2. 성능 이슈: 외부 API 호출을 통한 조건 검사는 네트워크 지연이나 API 서버의 처리 속도에 영향을 받을 수 있습니다. 이로 인해 정책 처리 성능이 저하될 수 있으며, 이를 개선하기 위해 캐싱, 배치 처리 등의 방법을 적용할 수 있습니다.


3. 보안 측면: 정책에서 사용하는 외부 API는 인증이 필요할 수 있습니다. 이를 위해 API 키나 OAuth 등의 인증 방식을 적용할 수 있으며, 정책 형식에 인증 정보를 포함하는 방법을 고려할 수 있습니다. 그러나 인증 정보의 노출 위험이 있으므로, 이를 안전하게 관리하는 방법도 함께 고려해야 합니다.