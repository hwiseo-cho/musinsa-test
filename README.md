## MUSINSA Backend Engineer (Core Biz Platform 파트너서비스/플랫폼팀) 과제

### 개발 환경
- Language : Java
- JDK : 17
- DateBase : H2
- Build Tool : Gradle

### 실행 및 테스트 방법
- 실행 
    -     ./gradlew build -x test 
          docker-compose up --build -d

- 테스트 방법
  - 프로그램 실행 시 자동으로 DB schema.sql, data.sql 실행하여 테스트 환경 구축
  - Postman을 사용하여 테스트 진행
  - Musinsa.postman_collection.json를 postman에 copy
### 구현 과정
#### 1. 고려 사항
   - 카테고리 데이터는 자주 수정이 되지 않는다는 전제
   - API 응답에서 성곰, 실패 모두 같은 프레임으로 나오도록 CommonResponse 작성
   - 예외 처리 에러는 MusinsaException, 그 외 에러는 RuntimeException으로 throw
   - 조회 성능 향상을 위한 캐싱 기능
#### 2. 요구사항 기능
   1. 구현 1) - 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
      - 카테고리 별 최저 가격 브랜드, 가격 조회 쿼리 작성
      - 중복 된 값이 있을 경우 브랜드 이름을 내림차순으로 정렬하여 상위 1개 표시
      - 받아온 결과 값을 stream()을 사용하여 합계 금액 도출
      - DTO로 변환 후 응답
   <br/><br/>
   2. 구현 2) - 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품 가격, 총액을 조회하는 API
      - 브랜드 별 상품 합계 금액중 가장 낮은 금액의 브랜드 1개만 가져오는 쿼리 작성
      - 받아온 결과 값을 stream()을 사용하여 브랜드, 카테고리 목록, 합계 도출
      - DTO 파일에 @JsonProperty를 사용하여 응답값 한글로 표시
   <br/><br/>
   3. 구현 3) - 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
      - GET 방식으로 전달 받은 카테고리 경로 변수를 인자 값으로 쿼리 조회
      - 받아온 결과 값을 DTO로 변환 후 응답
   <br/><br/>
   4. 구현 4) 브랜드 및 상품을 추가 / 업데이트 / 삭제하는 API
      - 상품과 브랜드 각각 추가/업데이트/삭제 작성
      - RESTful HTTP 메소드 활용
        - 추가: POST
        - 업데이트: PATCH 
        - 삭제: DELETE
      - 상품 업데이트의 경우 가격만 업데이트 가능하도록 작성
      - 브랜드 삭제의 경우 상품 데이터가 남아있다면 삭제 불가하도록 작성

