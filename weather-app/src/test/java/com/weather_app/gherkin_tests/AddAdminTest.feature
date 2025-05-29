Feature: Shtimi i një administratori të ri në sistem

  Si një administrator ekzistues
  Dua të shtoj një administrator të ri
  Që të menaxhoj më mirë aksesin në sistem

  Skenari: Shtimi i një administratori të ri përmes panelit të administratës
    Duke qenë se jam në faqen e kyçjes për administratorë
    Kur vendos emailin "admin@gmail.com" dhe fjalëkalimin "admin"
    Dhe klikoj butonin "Sign In"
    Atëherë duhet të ridrejtohem në panelin e administratës

    Kur navigoj te faqja "Admins"
    Dhe klikoj butonin "Add"
    Atëherë duhet të hapet forma për shtimin e një administratori të ri

    Kur plotësoj fushat me të dhëna valide të rastësishme
    Dhe klikoj butonin "Save"
    Atëherë administratori i ri duhet të shfaqet në listën e administratorëve
    Dhe duhet të shfaqet një mesazh suksesi
