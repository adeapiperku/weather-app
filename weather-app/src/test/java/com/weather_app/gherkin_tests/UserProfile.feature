Feature: Pamja e profilit të përdoruesit

  Si një përdorues i kyçur në sistem
  Dua të shoh profilin tim

  Skenari: Përdoruesi shikon faqen e profilit pas kyçjes
    Duke qenë se përdoruesi është në faqen e kyçjes "/client/sign-in"
    Kur ai vendos emailin "user@gmail.com"
    Dhe vendos fjalëkalimin "admin"
    Dhe klikon butonin "Sign In"
    Atëherë ai duhet të ridrejtohet në faqen "/client/home"
    Kur ai navigon në faqen "/client/profile"
    Atëherë duhet të shfaqet titulli "User Profile"
    Dhe shfaqen të dhënat personale