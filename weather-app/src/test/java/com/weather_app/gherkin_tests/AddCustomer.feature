Feature: Menaxhimi i klientëve në panelin admin

  Si një administrator i sistemit
  Dua të mund të kyçem në panelin e administrimit
  Dhe të shoh listën e users

  Skenari: Shto një user të ri në sistem
    Duke qenë se Admini hap faqen e kyçjes në "/admin/sign-in"
    Kur ai vendos emailin "admin@gmail.com" dhe fjalëkalimin "admin"
    Dhe klikon butonin "Sign In"
    Atëherë duhet të ridrejtohet te faqja "/admin/dashboard"
    Kur klikon në linkun "Customers"
    Atëherë duhet të shfaqet tabela me titullin "Customers View"
    Dhe kur klikoj butonin "+"
    Dhe plotësoj të dhënat e klientit të ri me emër, mbiemër, email, fjalëkalim, datëlindjen dhe numër telefoni të rastësishëm
    Dhe klikoj butonin "Save"
    Atëherë duhet të shoh që klienti u shtua me sukses në tabelë