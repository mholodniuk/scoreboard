# Assumptions

## Design
- game's state (other than score) does not matter
- game's score values won't be insanely large
- game's score can be manipulated in every way (goals can be withdrawn or added two or more at a time)
- games are unique in one scoreboard (e.g. there cannot be two games for any team happening at once)
- there can be multiple scoreboards associated with multiple games, so:
  - only games that are within the scope of a given scoreboard can be affected by the scoreboard operations (e.g. scoreboard 1 cannot update games coming from scoreboard 2)
  - games that are untracked by given scoreboard are ignored

## Technical
- Game can be only modified and initialized using Scoreboard
- Games are identified by their memory address (no ID is used)