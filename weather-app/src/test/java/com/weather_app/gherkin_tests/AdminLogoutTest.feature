Feature: Shkyçja e adminit nga sistemi

  Si një admin i kyçur në sistem
  Dua të kem mundësinë të largohem (logout)
  Që të sigurohem se askush nuk mund të hyjë në sistem pa u kyçur përsëri

  Skenari: Shkyçja e suksesshme nga dashboard-i i adminit
    Duke qenë se jam në faqen e kyçjes për admina
    Kur vendos emailin "admin@gmail.com" dhe fjalëkalimin "admin"
    Dhe klikoj butonin "Sign In"
    Atëherë duhet të ridrejtohem në dashboard-in e adminit

    Kur klikoj butonin "Log out"
    Atëherë duhet të ridrejtohem në faqen e kyçjes ose faqen kryesore
    Dhe nuk duhet të kem më qasje në dashboard-in e adminit pa u kyçur përsëri
