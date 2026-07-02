/*
AUFGABE 3: REFLEKTION

1. GENERICS:
- Fehlervermeidung: Java prüft schon beim Tippen, ob das Tier zum Gehege passt. Das verhindert Abstürze beim Starten.
- Beispiel: Ein `Aquarium` ist ein `Enclosure<Fish>`. Wenn man versucht, eine `Mouse` oder einen `Chimpanzee` reinzustecken, meckert IntelliJ sofort rot.

2. LOGGING:
- Warum besser als println?: Man kann Log-Meldungen nach Wichtigkeit filtern, sie im Betrieb einfach abschalten und automatisch in Textdateien speichern lassen.
- INFO: Für normale Starts/Aktionen (z.B. "Suche nach Gehege X gestartet").
- WARNING: Für Fehler, die nicht schlimm sind (z.B. "Gehege X wurde nicht gefunden").
- SEVERE: Für kritische Fehler (z.B. "Datenbank down" oder "Zoo-Daten beschädigt").

3. STREAMS:
- Gut bei: Einfachen Filtern und Suchen (z.B. Gehege nach Name finden). Der Code wird super kurz (Einzeiler).
- Schlecht bei: Komplizierten Rechnungen oder Sortierungen (z.B. Tiere zählen nach Typ). Da wird der Code schnell unübersichtlich und schwer zu verstehen.
  */