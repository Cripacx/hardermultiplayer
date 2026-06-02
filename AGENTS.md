# AGENTS.md

## Scope

These instructions apply to the whole workspace.

## Project Shape

- This repository is a Balm-based multi-loader Minecraft mod template targeting Fabric, Forge, and NeoForge.
- Shared gameplay code belongs in `common/src/main/java/de/cripacx/hardermultiplayer`.
- Loader-specific projects in `fabric`, `forge`, and `neoforge` should stay thin and only bootstrap platform/runtime integration.
- The current codebase still contains template/example content under the `hardermultiplayer` package and mod id. When implementing `Soul Revival`, rename or replace template-facing names deliberately instead of mixing old and new identifiers.

## Build And Validation

- The repository snapshot does not include `gradlew` or `gradlew.bat`; prefer installed Gradle commands such as `gradle build`.
- For narrow validation, prefer module-scoped commands first: `gradle :common:compileJava`, `gradle :fabric:compileJava`, `gradle :forge:compileJava`, `gradle :neoforge:compileJava`.
- Use `gradle build` only when a wider check is needed.

## Balm Requirements

- Always use the Balm docs and Balm source as the primary API reference before changing Balm integration or config code.
- Start with these references:
  - Config docs: https://github.com/TwelveIterations/balm-docs/blob/main/content/1.getting-started/5.config.md
  - Balm docs index: https://github.com/TwelveIterations/balm-docs/tree/main/content
  - Balm source for 26.1: https://github.com/TwelveIterations/Balm/tree/26.1
- Match the workspace's Balm version from `gradle/libs.versions.toml` when checking APIs.
- Follow Balm config constraints across all loaders:
  - Use supported config types only (`common`, `client`, `server`) for cross-loader code.
  - `@Synced` is the default way to mirror server-owned values to clients.
  - On Forge and NeoForge, common/client configs may not be available inside the mod initializer; defer access with `Balm.config().onConfigAvailable(...)` when needed.

## Code Placement

- Put gameplay state machines, revive rules, recipe/stage logic, and persistence abstractions in `common`.
- Keep loader entrypoints limited to `Balm.initializeMod(...)`, client bootstrap, and loader-only registration glue.
- Separate future code by responsibility:
  - gameplay: KO state, revive flow, stage rules, recipe switching
  - persistence: saved KO players, global stage, world lifecycle load/save hooks
  - commands: `/soulrevival stage ...`
  - registration: items, recipes, events, networking hooks if required
- Prefer small service-style classes over growing `HarderMultiplayer.java` into a god object.

## Soul Revival Target

- The requested feature set is a server-authoritative multiplayer mod called `Soul Revival`.
- Every gameplay feature must be individually configurable through Balm config.
- Treat these as core invariants when implementing:
  - player death drops items normally
  - dead players enter a persistent KO state instead of respawning
  - KO players remain visible at the death position, cannot interact, cannot take damage, and are ignored by mobs
  - revival consumes exactly one Soul Charm and restores control at the KO position
  - recipes depend on one global stage with exactly 3 stages
  - optional automatic stage progression is triggered by first Nether and End entry
  - KO state and stage must survive server restarts
- Design against dupes and item loss first; avoid solutions that copy inventories, clone entities, or depend on fragile client-only state.

## Existing Code Signals

- `common/src/main/java/de/cripacx/hardermultiplayer/HarderMultiplayer.java` already registers the Balm config and shared registrars.
- `common/src/main/java/de/cripacx/hardermultiplayer/HarderMultiplayerConfig.java` is still example content and should be replaced by a real config model before feature work expands.
- `fabric/.../FabricHarderMultiplayer.java` and `forge/.../ForgeHarderMultiplayer.java` show the current loader bootstrap pattern to preserve.

## Working Style

- Prefer minimal, loader-safe abstractions over platform branching in gameplay code.
- Do not assume Fabric-only conveniences are available on Forge or NeoForge.
- When using a Balm API that is unclear, inspect the matching 26.1 source before writing code.
- Update instructions if the mod id, package structure, or implementation layout changes materially during the Soul Revival buildout.