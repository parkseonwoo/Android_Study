MVC 패턴
- 사용자 입력은 컨트롤러(Activity)를 통해 들어오며 컨트롤러는 모델과 상호작용을 통해 View(xml)를 업데이트 한다
- 이떄 뷰는 모델을 참조하게 된다

![image](https://user-images.githubusercontent.com/46809199/218265519-16e66464-a02d-4be9-a2c0-34c9ddc4b0f9.png)

특징
   - Controller(Activity)
      - 앱을 묶어주는 접착체 역할(Activity, Fragment)
      - 사용자에게 입력을 받아 해당하는 모델을 선택하여 처리
      - 모델의 데이터 변화에 따라 뷰를 선택
   - View(XML)
      - 사용자에게 제공되는 UI
      - UI, 앱과의 상호작용에서 컨트롤러와의 통신
      - 사용자가 어떤 액션을 하든 무엇을 해야할지 모름
   - Model
      - 데이터 + 상태 + 비지니스 로직
      - View나 컨트롤러에 묶이지 않아 재사용 가능


장점
   - Model과 View를 분리
   - Model의 비종속성으로 재사용 가능
   - 구현하기 가장 쉬움

단점
   - 컨트롤러가 View에 결합되며, View의 확장이 될 수 있음
   - 규모가 커질수록 컨트롤러에 많은 코드가 쌓여 비대화 문제 발생
   - View와 Model 사이의 업데이트를 위해 직/간접적으로 참조 이로 인해 서로간의 의존성을 완벽하게는 없앨 수 없음


참고 사이트
- https://gitsu.tistory.com/38
