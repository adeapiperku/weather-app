Feature: Regjistrimi i klientit në sistem

  Si një përdorues i ri
  Dua të kem mundësinë të regjistrohem në sistem
  Që të kem akses në shërbimet e aplikacionit

  Skenari: Përdoruesi regjistrohet me të dhëna të vlefshme
    Duke qenë se përdoruesi është në faqen e regjistrimit "/client/sign-up"
    Kur ai plotëson emrin "Emri"
    Dhe ai plotëson mbiemrin "Mbiemri"
    Dhe ai plotëson një email unik
    Dhe ai vendos fjalëkalimin "TestPassword123!"
    Dhe ai klikon butonin "Sign Up"
    Atëherë ai duhet të ridrejtohet në faqen e kyçjes "/client/sign-in"
    Dhe nëse ka të dhënat e sakta të ridrejtohet në "/client/home"