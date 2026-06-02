# Harder Multiplayer

Harder Multiplayer is a server-authoritative multiplayer mod for Minecraft (Balm multi-loader: Fabric, Forge, NeoForge).

Project goal: when players die, they should not respawn normally. Instead, they enter a persistent KO state and can only be revived with a special item.

## Project Summary

Harder Multiplayer introduces a new risk and teamplay mechanic:

- On death, player items are dropped normally.
- Instead of respawning, the player enters a KO state.
- KO players remain visible at their death position.
- Reviving requires exactly one Soul Charm.
- The Soul Charm recipe depends on a global stage.
- The stage can be set manually or optionally progress automatically based on Nether/End progression.
- KO status and stage persist across server restarts.

## Gameplay Rules

### Death Behavior

1. A player dies.
2. The inventory drops normally.
3. The player enters KO state (no normal respawn).

### KO-State

A KO player:

- stays at the death position
- can move the camera
- can chat
- cannot walk, jump, attack, or use items
- cannot break or place blocks
- cannot receive damage
- is ignored by mobs
- remains visible to other players

### Revival

There are two ways to revive a KO player:

1. Right-click a KO player while holding a Soul Charm
2. Throw a Soul Charm directly onto a KO player

In both cases:

- exactly 1 Soul Charm is consumed
- the KO player is revived immediately
- full control is restored
- the player remains at the KO position

## Soul Charm

- custom item with unique identity
- non-stackable
- own item model / custom model data
- craftable only via stage-dependent recipe

## Stage System

There are exactly 3 global stages.

### Stage 1 (Early Game)

D G D
G A G
D G D

- D = Diamond Block
- G = Gold Block
- A = Golden Apple

### Stage 2 (Nether Age)

B N B
N G N
B N B

- B = Blaze Rod
- N = Netherite Scrap
- G = Ghast Tear

### Stage 3 (End Age)

S E S
N A N
C E C

- S = Nether Star
- E = Echo Shard
- N = Netherite Ingot
- C = End Crystal
- A = Enchanted Golden Apple

## Commands

- /soulrevival stage get
- /soulrevival stage set <1-3>

## Automatic Stage Progression

Optional and configurable:

- Stage 1 -> Stage 2: when any player enters the Nether for the first time
- Stage 2 -> Stage 3: when any player enters the End for the first time

## Persistence

Persisted data:

- player UUID
- KO position
- KO status
- current global stage

Data survives server restarts.

## Technical Goals

- multiplayer-ready and performant
- server-authoritative
- clear separation of gameplay, persistence, and commands
- loader-safe architecture for Fabric, Forge, and NeoForge
- no dupe exploits
- no item loss during the revive flow

## Project Structure

- common: loader-independent gameplay logic, persistence, commands, registrations
- fabric: Fabric bootstrap and loader glue
- forge: Forge bootstrap and loader glue
- neoforge: NeoForge bootstrap and loader glue

## Development Status

This repository is focused on Harder Multiplayer and uses the requirements in idea.md as the functional foundation for implementation.
