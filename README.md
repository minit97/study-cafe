# study-cafe
- 스터디 카페 관리 및 사용을 위한 웹개발 (api)


# ERD 구조

[👉 ERD Cloud에서 직접 보기](https://www.erdcloud.com/d/uSj93GWqSBAGewqYB)

![img_1.png](img_1.png)

# Spring REST Docs

- 프로덕션 코드에 영향이 없다.
- 장점이자 단점인 테스트 코드가 성공해야 문서 작성이 가능하다.

![img.png](img.png)

- 빌드 시 resources - static - docs 파일에 html 파일 생성

# 주요 기능

### 요약

<table align="center"><!-- 팀원 표 -->
  <tr>
   <th>
    공통
   </th>
   <th>
    사용자
   </th>
   <th >
    관리자
   </th>
   </tr>
  <tr>
   <td align="left" width="350px" class="공통">
    - 회원가입, 로그인
    <br/>
    - 게시판 등록, 조회, 수정, 삭제
   </td>
   <td align="left" width="350px" class="사용자">
    - 정기권 구매
    <br/>
    - 자리 선택
    <br/>
    - 자리 퇴장
   </td>
   <td align="left" width="350px" class="관리자">
    - 지점 등록, 삭제
    <br/>
    - 자리 등록, 삭제
    <br/>
    - 정기권 등록, 삭제
   </td>
  </tr>
</table>