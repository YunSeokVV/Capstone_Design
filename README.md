# <p align="center">   QR코드 모바일 결제 시스템 </p>

##### 

<p align="center">   오프라인 쇼핑을 할때 쇼핑카트 없이도 소비자용 앱만으로 원하는 상품을 구매할 수 있는 소비자용 서비스 앱입니다. </p>

담당 역할 : 팀장 <br>
개발 인원 : 3명 <br>
개발 기간 : 6개월<br>
기여도 : 100%

**목차**

1. 앱의기능
2. 사용한 기술들
3. 서비스 구조
4. 시연영상 링크



#  1. 앱의 기능
![](https://raw.githubusercontent.com/YunSeokVV/Capstone_Design/master/preview/features1.png)

![](https://raw.githubusercontent.com/YunSeokVV/Capstone_Design/master/preview/features2.png)

![](https://raw.githubusercontent.com/YunSeokVV/Capstone_Design/master/preview/features3.png)



# 기능 상세설명

**1.장바구니**

 QR코드로 인식한 상품을 장바구니에 담습니다.

**2.QR코드 인식**

 상품의 QR코드를 인식합니다.

**3.상품 상세화면**

 QR코드에 적힌 정보를 토대로 상품의 상세화면을 볼 수 있습니다.

 상품의 개수를 설정하고 그에 따른 가격을 확인합니다.

**4.배송지 설정**

 사용자가 배송지 주소를 설정합니다. DAUM 주소 API 를 사용했습니다.

**5.결제**

 사용자가 장바구니에 담았던 상품을 결재합니다.

 부트페이 API를 사용했습니다.

**6.구매내역**

 사용자가 자신의 구매내역을 확인할 수 있습니다.

**7.상세 구매내역**

 상세 구매내역을 확인할 수 있습니다.



# 2. 사용한 기술 & 라이브러리

- Language : Java
- BootPay
- Zxing(QR코드 스)
- DAUM 주소 API

# 3. 서비스 구조

![](https://raw.githubusercontent.com/YunSeokVV/Capstone_Design/master/preview/ServiceArchitecture.png)

**상품 QR코드** 
- 기존의 방식이던 상품 바코드에 비해 QR코드는 담을 수 있는 정보의 양이 앞도적으로 많습니다. 그리고 바코드와 다르게 상하좌우 관계없이 인식할 수 있습니다.

**소비자용 앱**
- 앱을 활용하면 사용자는 따로 장바구니가 쇼핑카트를 들고 다니면서 물건을 챙기지 않아도 됩니다. 앱으로 구매할 상품을 설정하면 됩니다.
  
**결제 API (BootPay API)**
- 소비자용앱에서 장바구니에 모아둔 상품들을 결제합니다.
  
**배송관리용 웹서버**
- 소비자의 주문정보와 구매내역등을 저장하고 있는 웹서버입니다.
  
**관리자용 웹**
- 소비자의 주문정보를 처리할 수 있는 관리자용 웹페이지입니다. 판매하고 있는 상품도 확인할 수 있습니다.

# 4. 시연영상 링크

https://www.youtube.com/watch?v=KgPOE8p0VKs

