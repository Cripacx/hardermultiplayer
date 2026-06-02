# Entwickle ein Minecraft-Mod/Plugin namens "Soul Revival"

## Kernmechanik

Wenn ein Spieler stirbt:

1. Alle Items des Spielers werden normal gedroppt.
2. Der Spieler respawnt NICHT.
3. Stattdessen wird er in einen "Knocked Out" (KO) State versetzt.

## KO State

Ein KO-Spieler:

* bleibt an seiner Todesposition
* kann die Kamera bewegen
* kann chatten
* kann nicht laufen
* kann nicht springen
* kann nicht angreifen
* kann keine Items benutzen
* kann keine Blöcke abbauen oder platzieren
* wird von Mobs vollständig ignoriert
* kann keinen Schaden erhalten
* ist für andere Spieler sichtbar

Der Zustand ähnelt einem sichtbaren Spectator-Modus, jedoch fest an der Todesposition.

## Wiederbelebung

Ein KO-Spieler kann auf zwei Arten wiederbelebt werden:

### Methode 1

Ein anderer Spieler rechtsklickt den KO-Spieler mit einem Soul Charm.

### Methode 2

Ein anderer Spieler wirft einen Soul Charm direkt auf den KO-Spieler.

In beiden Fällen:

* der Soul Charm wird verbraucht
* der KO-Spieler wird sofort wiederbelebt
* der Spieler erhält volle Kontrolle zurück
* der Spieler erscheint an seiner KO-Position

## Soul Charm

Der Soul Charm ist ein Custom Item.

Eigenschaften:

* einzigartiger Name: "Soul Charm"
* eigenes Custom Model Data / Item Model
* nicht stapelbar
* nur über das Rezept der aktuellen Stage craftbar

## Stages

Es existieren genau 3 Stages.

Der Server speichert die aktuelle Stage global.

Ein Admin kann die Stage per Command setzen:

/soulrevival stage set <1-3>

/soulrevival stage get

## Standard-Rezepte

### Stage 1 – Early Game

Standardstage beim Weltenstart.

Rezept:

D G D
G A G
D G D

D = Diamond Block
G = Gold Block
A = Golden Apple

### Stage 2 – Nether Age

Rezept:

B N B
N G N
B N B

B = Blaze Rod
N = Netherite Scrap
G = Ghast Tear

### Stage 3 – End Age

Rezept:

S E S
N A N
C E C

S = Nether Star
E = Echo Shard
N = Netherite Ingot
C = End Crystal
A = Enchanted Golden Apple

## Automatischer Stage-Wechsel

Optional konfigurierbar.

Wenn aktiviert:

Stage 1 → Stage 2:
Sobald irgendein Spieler erstmals den Nether betritt.

Stage 2 → Stage 3:
Sobald irgendein Spieler erstmals das End betritt.

## Persistenz

Der KO-State muss Server-Neustarts überleben.

Gespeichert werden:

* Spieler UUID
* Position
* aktuelle Stage
* KO Status

## Anforderungen

* Multiplayer-tauglich
* performant
* keine Dupe-Exploits
* kein Itemverlust durch Revive
* saubere API-Struktur
* klare Trennung von Gameplay, Persistence und Commands

Implementiere den vollständigen Code inklusive Registrierungen, Events, Commands, Datenhaltung und Crafting-Rezepten.
