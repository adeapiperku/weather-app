Feature: Shtimi i qytetit në listën e të preferuarave

  Si një përdorues i kyçur në sistem
  Dua të shtoj një qytet në listën time të preferuar

  Skenari: Shtimi i një qyteti në të preferuara pas kërkimit
    Duke qenë se jam në faqen e kyçjes
    Kur vendos emailin "user@gmail.com" dhe fjalëkalimin "admin"
    Dhe klikoj butonin "Sign in"
    Atëherë duhet të ridrejtohem në faqen kryesore

    Kur kërkoj për qytetin "London"
    Dhe klikoj butonin "Search"
    Atëherë duhet të shfaqen rezultatet e parashikimit të motit

    Kur klikoj butonin "Add to favorites"
    Atëherë butoni duhet të ndryshojë në "Remove from favorites"
    Dhe qyteti duhet të shtohet në listën time të të preferuarave
