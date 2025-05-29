Feature: Heqja e qytetit nga lista e të preferuarave

  Si një përdorues i kyçur në sistem
  Dua të heq një qytet nga lista ime e të preferuarave

  Skenari: Përdoruesi heq qytetin nga lista e të preferuarave
    Duke qenë se jam në faqen e kyçjes "/client/sign-in"
    Kur vendos emailin "user@gmail.com" dhe fjalëkalimin "admin"
    Dhe klikon butonin "Sign In"
    Atëherë duhet të ridrejtohem në faqen "/client/home"

    Kur kërkoj për qytetin "London"
    Dhe klikoj butonin "Search"
    Atëherë duhet të shfaqen rezultatet e parashikimit të motit

    Kur klikoj butonin "Remove from Favorites"
    Atëherë duhet të ndryshojë përsëri në "Add to Favorites"
    Dhe qyteti duhet të hiqet nga lista ime e të preferuarave
