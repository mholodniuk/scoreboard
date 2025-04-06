# Worldcup Scoreboard

## Requirements
- Starting a game
- Finishing a game
- Updating teams' score
- Retrieving summary of current games

## Assumptions

### Design
- Game's state (other than score) does not matter
- Game's score values won't be insanely large
- Game's score can be manipulated in every way (goals can be withdrawn or added two or more at a time for both teams)
- Games are unique in one scoreboard (e.g. there cannot be two games for any team happening at once)
- There can be multiple scoreboards associated with multiple games, so:
  - Only games that are within the scope of a given scoreboard can be affected by the scoreboard operations (e.g. scoreboard 1 cannot update games coming from scoreboard 2)
  - Games that are untracked by given scoreboard are ignored

### Technical
- Game can be only modified and initialized using Scoreboard
- Games are identified by their internal ID (in order to ensure uniqueness across multiple instances of scoreboard)
- Custom domain exception classes are not defined, messages should provide sufficient failure description
- No concurrent operations are expected

## Potential improvements
- Improve mechanism of handling game updates. Allow users to modify scoreboard's games from "outside" of it (Currently, method `Game#updateScore` is package-private).