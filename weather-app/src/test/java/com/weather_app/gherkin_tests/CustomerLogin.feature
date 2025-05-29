Feature: Kyçja e klientit në sistem

  Si një klient i regjistruar
  Dua të kem mundësinë të kyçem në sistem
  Që të kem akses në funksionalitetet e faqes

  Skenari: Klienti hyn në sistem me kredencialet e sakta
    Duke qenë se klienti është në faqen e kyçjes "/client/sign-in"
    Kur ai vendos emailin "user@gmail.com"
    Dhe vendos fjalëkalimin "admin"
    Dhe klikon butonin "Sign In"
    Atëherë ai duhet të ridrejtohet në faqen "/client/home"
    Dhe mund të kërkoj motin për ndonjë qytet