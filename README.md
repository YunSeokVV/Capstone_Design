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




![](https://private-user-images.githubusercontent.com/43668299/336834822-53346bf2-c3fa-4a49-bdf4-93c5e356599a.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MTc4MzI3NTAsIm5iZiI6MTcxNzgzMjQ1MCwicGF0aCI6Ii80MzY2ODI5OS8zMzY4MzQ4MjItNTMzNDZiZjItYzNmYS00YTQ5LWJkZjQtOTNjNWUzNTY1OTlhLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDA2MDglMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwNjA4VDA3NDA1MFomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPThjZGE3MDMyMzJjOTkyMzdhMDBmYzQ4ZjZmYzczNDM1MDliN2Y1OWI5ZDY0YzdmYmE1OTdiOTRkZWQyYTY5MGYmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.6kf5pS1qqdnZ6DyCqiKiZKnhhwU0itRMSpKF6aywmP0)



![](https://private-user-images.githubusercontent.com/43668299/336834976-6d651fd9-dcba-4cd9-abbf-31d9605c11fd.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MTc4MzI3NTAsIm5iZiI6MTcxNzgzMjQ1MCwicGF0aCI6Ii80MzY2ODI5OS8zMzY4MzQ5NzYtNmQ2NTFmZDktZGNiYS00Y2Q5LWFiYmYtMzFkOTYwNWMxMWZkLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDA2MDglMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwNjA4VDA3NDA1MFomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWQ0ZjgzNDBhM2Q5ZTZiMTA3YzZhMmFiODMxMjQzYzgwMjU1ZjFmODYyNDhiYjljMmQ5MmNjMDMwNzY5OWE5NTImWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.-TfJVMW5f433GOk7dwdJYuo9r7EdBXqd6sRC5Lfg2vo)



![](https://private-user-images.githubusercontent.com/43668299/336836017-5c91002b-2777-4fdb-b006-6c7e2ea53442.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MTc4MzI3NTAsIm5iZiI6MTcxNzgzMjQ1MCwicGF0aCI6Ii80MzY2ODI5OS8zMzY4MzYwMTctNWM5MTAwMmItMjc3Ny00ZmRiLWIwMDYtNmM3ZTJlYTUzNDQyLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDA2MDglMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwNjA4VDA3NDA1MFomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWRlYTdiNjA5YzQ1YmIwZGYxYTc3YTdiNmMxOGM0MDcxOGYyNGI5ZDNlOTk4YzAzZTI0YmEzZTIxMTE2M2U5NDQmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.RikiOKJjxv9oSf55D5vDr5FmS0xEsNBtzGIdF51Be-s)



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

![](https://private-user-images.githubusercontent.com/43668299/337859220-3d332044-1b17-4dfa-90b7-bcc3884d1510.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MTc4MzI2MzAsIm5iZiI6MTcxNzgzMjMzMCwicGF0aCI6Ii80MzY2ODI5OS8zMzc4NTkyMjAtM2QzMzIwNDQtMWIxNy00ZGZhLTkwYjctYmNjMzg4NGQxNTEwLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDA2MDglMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwNjA4VDA3Mzg1MFomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWUwOWI1YzZkNDYyMjIwZTE4MDNjYjExODM1ZDc0ZjdkZmIzMmJkZTc2ZGQ2MThhMWRjZWUzOTI2OGNmMDk4OTkmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.yDHtCpU1IMIe3Ni9OZNs-OY3a_3M2ecJBA4pW3rwXsk)

# 4. 시연영상 링크

https://www.youtube.com/watch?v=KgPOE8p0VKs

