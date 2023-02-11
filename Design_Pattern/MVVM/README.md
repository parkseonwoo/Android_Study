MVVM 패턴

   -  Model-View-ViewModel로 애플리케이션을 세 가지의 계층으로 구분한 방법론
   -  View는 ViewModel을 알고 있지만, ViewModel은 View를 알지 못함
   -  Data-binding을 사용하여 더 쉬운 테스트 및 모듈화의 이점을 제공
   -  View + Model을 연결하기 위해 작성하는 Glue 코드 양을 줄여줌
   

![image](https://user-images.githubusercontent.com/46809199/217699387-904b4cc8-7b62-4555-bed4-a8a0f6b4bd85.png)


MMVM 아키텍처 패턴의 계층

  -  View: 애플리케이션에서 사용자가 직접 보는 화면(UI)을 담당하는 계층
  -  ViewModel: 애플리케이션에서 View와 Model 사이에 존재하며 서로간의 중재를 하는 역할
     -  View-ViewModel: 사용자와 뷰의 상호작용(클릭, 키보드 등착 등)을 수신하여 이에 대한 처리를 View와 Model을 연결하고 있는 데이터 바인딩을 통해 서로간을 연결
     -  Model-ViewModel: 사용자의 데이터의 변경이 발생하는 ㄴ경우 데이터를 가져오거나 갱신한 뒤 View에게 전달하여 사용자에게 전달하는 역할 수행
   -  Model: 애플리케이션에서 데이터를 저장하고 처리하는 계층
   
   
   ![image](https://user-images.githubusercontent.com/46809199/217706960-39b42b2b-4e89-4ce2-87e5-292309f901eb.png)
   - 참고: https://developer.android.com/topic/libraries/architecture/viewmodel?hl=ko

MVVM 아키텍처 패턴의 흐름

   -  전체흐름 - 사용자 Action -> View -> ViewModel -> Model -> ViewModel -> View -> 사용자
   
MVVM 아키텍처 패턴 흐름 - 상세 흐름

   - 사용자가 입력한 값이 View를 통해 들어온다(사용자 -> View)
   - View에 입력값이 들어오면 ViewModel로 입력 값을 전달한다(View -> ViewModel)
   - 전달받은 ViewModel은 Model에게 데이터 요청을 보낸다(ViewModel -> Model)
   - Model은 ViewModel에게 요청받은 데이터를 Response을 한다(ViewMode -> Model)
   - ViewModel은 그 값을 처리하여 내부에 저장한다
   - View는 ViewModel과의 '데이터 바인딩'을 통해 화면상에 표출한다(View <-> ViewModel)
   
 

장점

   -  View와 Model의 사이의 의존성이 없다
   -  Command 패턴 혹은 데이터 바인딩을 사용하여 View와 ViewModel사이의 의존성이 없다
   -  프로젝트 파일의 유지 관리하고 쉽게 변경 할 수 있다

단점

   -  View와 Model을 설계하는데 쉽지 않다
   -  이 디자인 아키텍처 패턴은 소규모 프로젝트에는 적합하지 않다
   -  데이터 바인딩 로직이 너무 복잡하면 애플리케이션의 디버깅이 어려워 질 수 있다
   
   
안드로이드 아키텍쳐 컴포넌트(Android Architecture Component, AAC)

   -  구글에서 안드로이드 아키텍쳐 컴포넌트라는 것을 제공하는데, 이는 앱의 구조가 더 튼튼하고, 유지 보수성이 좋고, 테스트하기 용이하도록 도와주는 라이브러리 모음
   -  이는 디자인 패턴의 목표와 같기 때문에 AAC를 활용하면 MVVM 패턴을 더 쉽게 구성 가능
   -  참조: https://developer.android.com/topic/libraries/architecture?hl=ko

   ![image](https://user-images.githubusercontent.com/46809199/217704250-42980907-391b-4543-94ab-04df194c38db.png)
   

   -  AAC의 대표적인 컴포넌트로는 위에 나와있는 LiveData, ViewModelm Room과 Databinding이 있다
   -  아래에서 위로 올라가는 "back-up"점선 죽, 아래의 데이터를 위에서 관찰하고 있다가 변경시 Observer가 그것을 감지해서 자동으로 처리해주기 떄문에 가능
   -  이것을 도와주는 것이 LiveData
   
   
View

   - UI Controller(Activity, Fragment 등)
   - 철저하게 UI를 그리는 부분과 사용자의 상호작용을 담당하며, 추가적으로 OS와의 통신도 한다
      - OS 통신 ex) 다른 Activity를 켰다가 되돌아왔을 때 결과가 전달됐다면 일단 Activity에서 그것을 받음
      
ViewModel

  - 구성 변경(Configuration Change: 화면 회전, 언어 변경)으로 액티비티가 종료됐다가 재생성돼도 살아남아서 데이터를 보관
  - 이것이 핵심인데 View와 완전하게 분리도기 때문
  - 따라서 유닛 테스트가 더 용이해지고ㅡ 구성 변경으로 생길 수 있는 문제를 사전에 방지
  
LiveData

   - 관찰이 가능한 데이터 홀더 클래스
   - 뷰에서 뷰모델의 LiveData를 관찰하면, 데이터 변경시 자동으로 옵저버에게 알림
   - 옵저버의 생명주기 인식
      - 생명주기가 활성화되어 있지 않을 때는 알림이 가지 않다가 활성화 되었을 떄 보관해놨던 알림 전달 -> 메모리를 효율적으로 사용할 수 있고 접근 불가능한 상태의 View에 접근하는 에러 방지
      - 생명주기가 destroy되면 자동 제거 -> 메모리 누수 현상 방지
      
Repository

   - 뷰모델과 상호작용하기 위한 API 담당할 클래스
   - 로컬 DB나 외부에서 앱에 필요한 데이터 가져옴
   - 뷰모델은 DB에 직접 접근하지 않고 리포지토리에 요청하여 데이터를 처리
   
Room

   - Sqlite를 편하게 쓰도록 도와주는 라이브러리 -> 로컬 DB를 더 쉽게 구성 가능
   
  
   
참고 블로그
- https://adjh54.tistory.com/60
- https://best-human-developer.tistory.com/72
- https://velog.io/@heetaeheo/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-AAC

