Feature: Shfaqja e qyteteve të preferuara nga përdoruesi

  Si një përdorues i kyçur në sistem
  Dua të shoh listën e qyteteve të mia të preferuara në profil
  Që të kem një përmbledhje të qyteteve që më pëlqejnë

  Scenario: Përdoruesi shikon qytetet e preferuara në profilin e tij
    Duke qenë se jam në faqen e kyçjes "/client/sign-in"
    Kur vendos emailin "user@gmail.com" dhe fjalëkalimin "admin"
    Dhe klikon butonin "Sign In"
    Atëherë duhet të ridrejtohem në faqen "/client/home"

    Kur klikon në linkun e profilit "/client/profile"
    Dhe klikon butonin "View Favorite Cities"
    Atëherë duhet të shfaqet një dialog me titull "Favorite Cities"

    Kur lista e qyteteve të preferuara nuk është bosh
    Atëherë duhet të shfaqen qytetet e preferuara në listë

    Kur lista e qyteteve të preferuara është bosh
    Atëherë duhet të shfaqet mesazhi "No favorite cities"

    Dhe kur klikon butonin "Close"
    Atëherë dialogu duhet të mbyllet
