POO TV

Prima etapa în implementarea proiectului a fost crearea claselor specifice pentru input. 
Pentru stocarea datelor am definit clasa DataBase si am citit datele din fisierele json cu ajutorul
metodei readValue specifice clasei ObjectMapper in obiectul database.
 Logica de rulare a paginilor web a fost implementata cu ajutorul design paternului state. Clasa abstracta 
PageState reprezinta stare unei pagini la o actiune a userului. Clasa WebPage defineste interfata de rulare a 
siteului, contine un obiect de tip state(private PageState state). In dependenta de actiunile executatate aceasta isi 
schimba starea modificand obiectul. Fiecare stare contine la randul ei obiectul de tip WebPage ce este definit 
prin constructor, astfel la initierea unei noi stari atribuim acesteia si interfata de rulare.

Pana la inregistrare/logare, utilizatorul curent nu exista - fiind pe homepage neutentificat -> de pe care poate 
accesa login sau register ( actiuni care valideaza datele utilizatorului - pentru login daca acesta exista in database
, iar pentru register - daca acesta este deja in database, in caz contrar fiind adaugat).


Dupa autentificare informatia aferenta userului este pastrata in obiectul currentUser, iar lista de filme aferenta 
in currentUserMovies. In urma actiunelor de tip search si filter lista de filme este modificata dupa convenienta,
pentru a nu produce schimbari in database acestea sunt pastrate in currentMovieList. 

1. Movies - pentru filtrarea filmelor in functie de rating si durata, am suprascris metoda compareTo,
implementand interfata Comparable realizand comparatia filmelor crescator sau descrescator corespunzator cerintelor.
Pentru cautarea filmului, am utilizat metoda startsWith.
2. See Details - pentru a vedea detaliile filmului, este executata o verificare daca acesta exista, iar daca da
utlizatorul poate accesa features specifice paginii de See Details , cum ar fi :
    - Purchase - unde se verifica doua cazuri -  utilizator premium si standard - si in functie de caz 
   fie sunt scazute numarul de filme premium, fie sunt cumparate cu tokens pentru primul tip, sau doar sunt cumparate
   cu tokens pentru al doilea.
    - Watch - daca utilizatorul a cumparat filmul, acesta poate sa-l vizioneze, fiind adaugat corespunzator in 
   lista de filme vizionate.
    - Rate - daca utilizatorul a vizionat filmul, acesta poate sa-i dea un rating , filmului fiindu-i crescut 
   numarul de ratings , si recalculanduise ratingul final.
    - Like - daca utilizatorul a vizionat filmul, acesta poate sa-i dea like , filmului fiindu-i crescut numarul
   de like-uri
3. Upgrades - pe aceasta pagina pot fi realizate actiunile de tip buy tokens si buy premium account, pentru prima 
actiune fiind verificat daca utilizatorul are destul credit in balance, in caz afirmativ fiindui adaugate tokens si 
actualizata balanta, iar pentru a doua , este verificat daca utilizatorul are destui tokens (10) pentru a face 
upgrade la tipul contului.
4. Logout - resteaza userul curent si lista de filme, redirectionand la Homepage Neautentificat.

Codreanu Marcel 325CD
