Feature: Shfaqja e qyteteve të preferuara nga admini

  Si një administrator i sistemit
  Dua të mund të kyçem në panelin e administrimit
  Dhe të shoh listën e qyteteve të preferuara nga përdoruesit
  Që të analizoj preferencat e tyre

  Skenari: Admini shikon qytetet e preferuara të përdoruesve
    Duke qenë se Admini hap faqen e kyçjes në "/admin/sign-in"
    Kur ai vendos emailin "admin@gmail.com" dhe fjalëkalimin "admin"
    Dhe klikon butonin "Sign In"
    Atëherë duhet të ridrejtohet te faqja "/admin/dashboard"

    Kur klikon në menunë ose linkun "Favorites"
    Atëherë duhet të shfaqet tabela me titullin "Favorites View"
    Dhe tabela duhet të përmbajë kolonat "Customer" dhe "Favorite Cities"
