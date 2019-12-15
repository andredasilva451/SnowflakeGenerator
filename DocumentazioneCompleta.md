1. [Introduzione](#introduzione)

  - [Informazioni sul progetto](#informazioni-sul-progetto)

  - [Abstract](#abstract)

  - [Scopo](#scopo)

1. [Analisi](#analisi)

  - [Analisi del dominio](#analisi-del-dominio)
  
  - [Analisi dei mezzi](#analisi-dei-mezzi)

  - [Analisi e specifica dei requisiti](#analisi-e-specifica-dei-requisiti)

  - [Use case](#use-case)

  - [Pianificazione](#pianificazione)

1. [Progettazione](#progettazione)

  - [Design dell’architettura del sistema](#design-dell’architettura-del-sistema)

  - [Design dei dati e database](#design-dei-dati-e-database)

1. [Implementazione](#implementazione)

1. [Test](#test)

  - [Protocollo di test](#protocollo-di-test)

  - [Risultati test](#risultati-test)

  - [Mancanze/limitazioni conosciute](#mancanze/limitazioni-conosciute)

1. [Consuntivo](#consuntivo)

1. [Conclusioni](#conclusioni)

  - [Sviluppi futuri](#sviluppi-futuri)

  - [Considerazioni personali](#considerazioni-personali)

1. [Sitografia](#sitografia)

1. [Allegati](#allegati)


## Introduzione

### Informazioni sul progetto

Il progetto SnowFlake Generator comprende la partecipazione di: 

- André Da Silva, progettista e sviluppatore.
- Luca Muggiasca, docente,consulente e cliente.
- Geo Petrini, docente, consulente e cliente.

Data inizio: 06.09.19
Data fine: 20.12.19


SAMT, Sezione informatica modulo 306 + progetti.


### Abstract

  E’ una breve e accurata rappresentazione dei contenuti di un documento,
  senza notazioni critiche o valutazioni. Lo scopo di un abstract efficace
  dovrebbe essere quello di far conoscere all’utente il contenuto di base
  di un documento e metterlo nella condizione di decidere se risponde ai
  suoi interessi e se è opportuno il ricorso al documento originale.

  Può contenere alcuni o tutti gli elementi seguenti:

  -   **Background/Situazione iniziale**

  -   **Descrizione del problema e motivazione**: Che problema ho cercato
      di risolvere? Questa sezione dovrebbe includere l'importanza del
      vostro lavoro, la difficoltà dell'area e l'effetto che potrebbe
      avere se portato a termine con successo.

  -   **Approccio/Metodi**: Come ho ottenuto dei progressi? Come ho
      risolto il problema (tecniche…)? Quale è stata l’entità del mio
      lavoro? Che fattori importanti controllo, ignoro o misuro?

  -   **Risultati**: Quale è la risposta? Quali sono i risultati? Quanto è
      più veloce, più sicuro, più economico o in qualche altro aspetto
      migliore di altri prodotti/soluzioni?

  Esempio di abstract:

  > *As the size and complexity of today’s most modern computer chips
  > increase, new techniques must be developed to effectively design and
  > create Very Large Scale Integration chips quickly. For this project, a
  > new type of hardware compiler is created. This hardware compiler will
  > read a C++ program, and physically design a suitable microprocessor
  > intended for running that specific program. With this new and powerful
  > compiler, it is possible to design anything from a small adder, to a
  > microprocessor with millions of transistors. Designing new computer
  > chips, such as the Pentium 4, can require dozens of engineers and
  > months of time. With the help of this compiler, a single person could
  > design such a large-scale microprocessor in just weeks.*

### Scopo

  Lo scopo del progetto è quello di sviluppare un software programmato in Java il cui compito è quello di
  generare un fiocco di neve. Per fare ciò l'utente deve ritagliare, a scelta, le varie parti di un triangolo 
  rettangolo al fine di generare il fiocco. Infine, per scelta dell'utente, sarà possibile salvare il risultato
  in formato raster o vettoriale.
  
  
## Analisi

### Analisi del dominio

  Essendo al terzo anno della sezione informatica della scuola Arti e Mestieri di Trevano, occorre
  essere capaci di progettare e sviluppare un progetto in modo autonomo, sia per l'esame del prossimo anno che in futuro in un'azienda. 
  Per questo, nel primo semestre, è stato deciso di affidarci a tutti lo stesso progetto, cioè un generatore di fiocchi
  di neve, di cui dobbiamo occuparci della progettazione con tutte le varie fasi (analisi,progettazione,implementazione,test).
  
### Analisi e specifica dei requisiti


  |**ID**	|**Nome**			|**Priorità**|**Vers**|**Note**  |
  |----|------------|--------|----|------|
  |Req-1 |Il software deve essere scritto in JAVA|1|1.0|-|
  |Req-2|Occorre l'utilizzo di un'interfaccia grafica|1|1.0|-|
  |Req-3|La finistra dell'interfaccia è ridimensionabile, minimo 1024x768|1|1.0|-|
  |Req-4|Deve esistere un sito con la descrizione del software|1|1.0|-|
  |Req-5|Deve essere possibile scaricare il software dal sito|1|1.0|-|
  |Req-6|Il software deve avere un'interfaccia grafica (jframe o frame)|1|1.0|-|
  |Req-7|All'avvio deve essere mostrato il triangolo da ritagliare|1|1.0|-|
  |Req-8|Il triangolo può ridimensionarsi in base alla grandezza dell'interfaccia (iniziale 50%)|1|1.0|-|
  |Req-9|I tagli (punti) del triangolo devono poter essere fatti con il mouse|1|1.0|-|
  |Req-10|Deve essere possibile resettare i punti del ritaglio|1|1.0|-|
  |Req-11|Deve esistere un tasto 'genera fiocco'|1|1.0|-|
  |Req-12|La generazione del fiocco deve avvenire in tempo reale (anteprima con i punti attualmente messi)|2|1.0|-|
  |Req-13|I punti devono poter essere spostati o rimossi |2|1.0|-|
  |Req-14|Il lavoro deve poter essere salvato|1|1.0|-|
  |Req-15|Il salvataggio del fiocco deve essere fatto in formato PNG o SVG tramite un bottone 'salva'|1|1.0|-|
  |Req-16|Il salvataggio deve avere dimensioni definite dall'utente|1|1.0|-|
  |Req-17|I punti devono essere salvabili in un file|1|1.0|-|
  |Req-18|I punti potranno essere importati tramite esplora file|1|1.0|-|
  |Req-19|È possibile switchare tra creazione/eliminazione punto tramite un bottone 'creazione/eliminazione'|2|1.0|-|
  
**Spiegazione elementi tabella dei requisiti:**

**ID**: ID dell' requisito

**Nome**: Breve descrizione del requisito

**Priorità**: indica l’importanza di un requisito nell’insieme del
progetto, definita assieme al committente:
1: Priorità alta
2: Priorità bassa

**Versione**: indica la versione del requisito. Ogni modifica del
requisito avrà una versione aggiornata.

**Note**: eventuali osservazioni importanti o riferimenti ad altri
requisiti.

### Use case

![alt text](https://github.com/andredasilva451/SnowflakeGenerator/blob/master/UseCase.png)

### Pianificazione

Prima di stabilire una pianificazione bisogna avere almeno una vaga idea
del modello di sviluppo che si intende adottare. In questa sezione
bisognerà inserire il modello concettuale di sviluppo che si seguirà
durante il progetto. Gli elementi di riferimento per una buona
pianificazione derivano da una scomposizione top-down della problematica
del progetto.

La pianificazione può essere rappresentata mediante un diagramma di
Gantt.

Se si usano altri metodi di pianificazione (es scrum), dovranno apparire
in questo capitolo.

### Analisi dei mezzi

#### Hardware
- ROG GL702VM
#### Librerie
- Java Swing
- Java AWT
- Java IO
- ImageTracer

#### Piattaforma
Qualsiasi Sistema operativo (Windows,Mac OS, distribuzioni Linux, etc.)

## Progettazione

Questo capitolo descrive esaustivamente come deve essere realizzato il
prodotto fin nei suoi dettagli. Una buona progettazione permette
all’esecutore di evitare fraintendimenti e imprecisioni
nell’implementazione del prodotto.

### Design dell’architettura del sistema

Descrive:

-   La struttura del programma/sistema lo schema di rete...

-   Gli oggetti/moduli/componenti che lo compongono.

-   I flussi di informazione in ingresso ed in uscita e le
    relative elaborazioni. Può utilizzare *diagrammi di flusso dei
    dati* (DFD).

-   Eventuale sitemap

### Design delle interfacce

L'interfaccia del software sarà composta da 2 frame:

- **Menu Frame**: Schermata iniziale con tasto "avvia" per utilizzare il SW, tasto "about" per delle informazioni sul software ed il tasto "esci" per poter chiudere il tutto.
- **SnowFlake Frame**: Frame principale del SW, composto da vari Panel, ognuno con il proprio scopo:
   - **SnowFlake Panel**: Pannello dove viene generato il triangolo, i tagli ed il fiocco di neve.
   - **Buttons Panel**: Pannello con i vari bottoni per il salvataggio, generazione fiocco, etc.
   - **Options Panel**: Pannello che includerà varie opzoni facoltative (es. colore sfondo), inoltre include il **PreviewPanel** che mostrerà l'anteprima del fiocco di neve.

![alt text](https://github.com/andredasilva451/SnowflakeGenerator/blob/master/design.png)

### Design procedurale

Descrive i concetti dettagliati dell’architettura/sviluppo utilizzando
ad esempio:

-   Diagrammi di flusso e Nassi.

-   Tabelle.

-   Classi e metodi.

-   Tabelle di routing

-   Diritti di accesso a condivisioni …

Questi documenti permetteranno di rappresentare i dettagli procedurali
per la realizzazione del prodotto.

## Implementazione

In questo capitolo dovrà essere mostrato come è stato realizzato il
lavoro. Questa parte può differenziarsi dalla progettazione in quanto il
risultato ottenuto non per forza può essere come era stato progettato.

Sulla base di queste informazioni il lavoro svolto dovrà essere
riproducibile.

In questa parte è richiesto l’inserimento di codice sorgente/print
screen di maschere solamente per quei passaggi particolarmente
significativi e/o critici.

Inoltre dovranno essere descritte eventuali varianti di soluzione o
scelte di prodotti con motivazione delle scelte.

Non deve apparire nessuna forma di guida d’uso di librerie o di
componenti utilizzati. Eventualmente questa va allegata.

Per eventuali dettagli si possono inserire riferimenti ai diari.

**Triangolo**

Per generare il triangolo è stata creata una classe apparte di nome 'Triangolo' che richiede, per essere istanziata, le coordinate x e y del punto in cui disegnare il triangolo, ed infine larghezza e altezza. Per disegnare il triangolo si prendono le coordinate degli spigoli che vengono salvate in due array, uno per le X ed uno per le Y, definiti tramite 2 metodi:

```java

private int[] pointsXdefinition(){
       
        this.pointX[0] = this.posX;
        this.pointX[1] = this.posX + this.width;
        this.pointX[2] = this.posX + this.width;
        return pointX;
    
    }
    
    private int[] pointsYdefinition(){
        
        this.pointY[0] = this.posY;
        this.pointY[1] = this.posY;
        this.pointY[2] = this.posY + this.height;  
        return pointY;
    }

```








## Diagramma UML



## Test

### Protocollo di test

|Test Case      | TC-001                               |
|---------------|--------------------------------------|
|**Nome**       |Generazione triangolo centrato proporzionalmente |
|**Riferimento**|REQ-07,REQ-08                               |
|**Descrizione**|Aprendo il SW, si crea un triangolo centrato e proporzionale al pannello |
|**Prerequisiti**| Aprire il SW |
|**Procedura**   | Aprire il SW tramite il file .jar o NetBeans |
|**Risultati attesi** | Il triangolo è presente centrato e ridimensionando il frame, si adatta ad esso |

|Test Case      | TC-002                               |
|---------------|--------------------------------------|
|**Nome**       |Creazione punti ritaglio |
|**Riferimento**|REQ-09                              |
|**Descrizione**|è possibile generare dei 'punti ritaglio' al fine di creare dei 'poligoni ritaglio'|
|**Prerequisiti**| Aprire il SW, mouse |
|**Procedura**   | Aprire il SW tramite il file .jar o NetBeans, Cliccare con il tasto Sx in un punto qualsiasi del frame |
|**Risultati attesi** | I punti ritaglio vengono creati e sono collegati tra di loro in caso non venga ancora definito il poligono |

|Test Case      | TC-003                               |
|---------------|--------------------------------------|
|**Nome**       |Eliminazione punti ritaglio |
|**Riferimento**|REQ-13                             |
|**Descrizione**|è possibile eliminare 'punti ritaglio' creati|
|**Prerequisiti**| Aver generarato almeno 1 punto |
|**Procedura**   | Dopo aver creato un punto ritaglio, premendoci sopra con il tasto destro dovrebbe eliminarsi. In caso si avesse già creato altri punti, essi continuano ad essere connessi tra di loro senza problemi |
|**Risultati attesi** | Il punto selezionato non c'è piu, la struttura di punti precedentemente creata continua ad esistere correttamente |

|Test Case      | TC-004                               |
|---------------|--------------------------------------|
|**Nome**       |Spostamento punti ritaglio |
|**Riferimento**|REQ-13                             |
|**Descrizione**|è possibile spostare i punti ritaglio selezionati |
|**Prerequisiti**| Aver generarato almeno 1 punto |
|**Procedura**   | Dopo aver creato un punto ritaglio, tener premuto il tasto sx su di esso e trascinarlo con il mouse. |
|**Risultati attesi** | Il punto selezionato si sposta seguendo correttamente il cursore. In caso di piu punti creati, anche il filo connesso ad esso si muove e segue il punto. |

|Test Case      | TC-005                               |
|---------------|--------------------------------------|
|**Nome**       |Generazione poligono di ritaglio |
|**Riferimento**|REQ-09                             |
|**Descrizione**|è possibile generare un poligono di ritaglio tramite chiusura dei punti ritaglio |
|**Prerequisiti**| Aver generarato 2 punti |
|**Procedura**   | Dopo aver creato 2 punti, il terzo dovrà essere fatto sopra il primo affinchè venga chiuso. A questo punto, il colore dei punti risulterà essere Arancione e premendo il tasto sx del mouse, si genera il poligono. |
|**Risultati attesi** | Viene generato un poligono blu dove prima vi erano i punti ritaglio. |

|Test Case      | TC-006                               |
|---------------|--------------------------------------|
|**Nome**       |Reset dei punti ritaglio |
|**Riferimento**|REQ-10                             |
|**Descrizione**|è possibile resettare i punti e i poligoni ritagli creati |
|**Prerequisiti**| Aver generato almeno 1 punto o 1 poligono ritaglio |
|**Procedura**   | Dopo aver fatto almeno 1 punto o poligono di ritaglio, premere il tasto 'reset', presente nel pannello dei bottoni |
|**Risultati attesi** | Tutti gli elementi creati (punti o poligoni) vengono eliminati. Se il tasto viene premuto dopo aver già generato il fiocco, quest'ultimo si eliminerà e si ritornerà alla modalità di ritaglio del triangolo. |

|Test Case      | TC-007                               |
|---------------|--------------------------------------|
|**Nome**       |Generazione fiocco |
|**Riferimento**|REQ-11                             |
|**Descrizione**|è possibile generare un fiocco di neve |
|**Prerequisiti**| Apertura SW |
|**Procedura**   | Premere il tasto 'genera' del pannello con tutti i pulsanti. Si consiglia di aver generato prima un poligono ritaglio sul triangolo per poter vedere il risultato dei ritagli. |
|**Risultati attesi** | Si genera il fiocco di neve (con o senza ritagli).  |

|Test Case      | TC-008                               |
|---------------|--------------------------------------|
|**Nome**       |Salvataggio dei punti ritaglio |
|**Riferimento**|REQ-14, REQ-17                        |
|**Descrizione**|è possibile salvare tutti i punti di ritaglio fatti sul triangolo |
|**Prerequisiti**| Aver generato almeno 1 poligono ritaglio |
|**Procedura**   | Creare un poligono di ritaglio, dopodiché, premere il tasto 'salva punti' e scegliere nome e dove salvare il file con i punti. (ogni formato binario è accettato) |
|**Risultati attesi** | Si viene a creare un file binario (es. .txt) con le coordinate dei punti creati. |

|Test Case      | TC-009                               |
|---------------|--------------------------------------|
|**Nome**       |Salvataggio fiocco di neve            |
|**Riferimento**|REQ-14, REQ-15, REQ-16                       |
|**Descrizione**|è possibile salvare il fiocco generato in formato png o svg. |
|**Prerequisiti**| Aver generato il fiocco di neve |
|**Procedura**   | Dopo aver generato il fiocco di neve, premere il tasto salva e scegliere se salvare in png o svg, dimensioni, nome e la directory. |
|**Risultati attesi** | Si viene a creare un file png o svg con il fiocco di neve disegnato. |

|Test Case      | TC-010                               |
|---------------|--------------------------------------|
|**Nome**       |Import dei punti ritaglio            |
|**Riferimento**|REQ-18                     |
|**Descrizione**|è possibile importare i punti ritaglio creati in precedenza. |
|**Prerequisiti**| Aver già creato e salvato dei punti ritaglio di uno o piu poligoni |
|**Procedura**   | Premere il tasto 'importa punti' e scegliere il file binario dei punti. |
|**Risultati attesi** | Si viene a creare il o i poligoni di ritaglio nella posizione dei punti importati. |

|Test Case      | TC-011                               |
|---------------|--------------------------------------|
|**Nome**       |Generazione in tempo reale del fiocco            |
|**Riferimento**|REQ-12                     |
|**Descrizione**|Ad ogni poligono generato, si crea un'anteprima del fiocco con i tagli fatti |
|**Prerequisiti**| Aprire il SW |
|**Procedura**   | Creare un poligono di ritaglio.  |
|**Risultati attesi** | Nel piccolo pannello a destra, si viene a generare un fiocco con i tagli fatti. |



### Risultati test

| Test Case | Stato | 
|-----------|-------|
|   TC-001  | Riuscito |            
|   TC-002  | Riuscito |            
|   TC-003  | Riuscito |           
|   TC-004  | Riuscito |
|   TC-005 | Riuscito |             
|   TC-006 | Riuscito |             
|   TC-007 | Riuscito |             
|   TC-008 | Riuscito |             
|   TC-009 | Riuscito |  
|   TC-010 | Riuscito |             
|   TC-011 | Non Riuscito |             



### Mancanze/limitazioni conosciute

- Non è presente nessun tasto per lo switch creazione/elimina, ma l'operazione viene eseguita con i tasti sinistro o destro del mouse.
- Non è possibile scegliere la dimensione con cui salvare l'immagine.
- La preview del fiocco di neve non funziona.

## Consuntivo

Consuntivo del tempo di lavoro effettivo e considerazioni riguardo le
differenze rispetto alla pianificazione (cap 1.7) (ad esempio Gannt
consuntivo).

## Conclusioni

Quali sono le implicazioni della mia soluzione? Che impatto avrà?
Cambierà il mondo? È un successo importante? È solo un’aggiunta
marginale o è semplicemente servita per scoprire che questo percorso è
stato una perdita di tempo? I risultati ottenuti sono generali,
facilmente generalizzabili o sono specifici di un caso particolare? ecc

### Sviluppi futuri
  Migliorie o estensioni che possono essere sviluppate sul prodotto.

### Considerazioni personali
  Cosa ho imparato in questo progetto? ecc

## Bibliografia

### Bibliografia per articoli di riviste
1.  Cognome e nome (o iniziali) dell’autore o degli autori, o nome
    dell’organizzazione,

2.  Titolo dell’articolo (tra virgolette),

3.  Titolo della rivista (in italico),

4.  Anno e numero

5.  Pagina iniziale dell’articolo,

### Bibliografia per libri


1.  Cognome e nome (o iniziali) dell’autore o degli autori, o nome
    dell’organizzazione,

2.  Titolo del libro (in italico),

3.  ev. Numero di edizione,

4.  Nome dell’editore,

5.  Anno di pubblicazione,

6.  ISBN.

### Sitografia

1.  URL del sito (se troppo lungo solo dominio, evt completo nel
    diario),

2.  Eventuale titolo della pagina (in italico),

3.  Data di consultazione (GG-MM-AAAA).

**Esempio:**

-   http://standards.ieee.org/guides/style/section7.html, *IEEE
    Standards Style Manual*, 07-06-2008.

## Allegati

Elenco degli allegati, esempio:

-   Diari di lavoro

-   Codici sorgente/documentazione macchine virtuali

-   Istruzioni di installazione del prodotto (con credenziali
    di accesso) e/o di eventuali prodotti terzi

-   Documentazione di prodotti di terzi

-   Eventuali guide utente / Manuali di utilizzo

-   Mandato e/o Qdc

-   Prodotto

-   …
