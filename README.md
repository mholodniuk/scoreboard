# Worldcup Scoreboard

## Requirements
- Starting a game
- Finishing a game
- Updating teams' score
- Retrieving summary of current games

## Assumptions

### Design
- game's state (other than score) does not matter
- game's score values won't be insanely large
- game's score can be manipulated in every way (goals can be withdrawn or added two or more at a time for both teams)
- games are unique in one scoreboard (e.g. there cannot be two games for any team happening at once)
- there can be multiple scoreboards associated with multiple games, so:
  - only games that are within the scope of a given scoreboard can be affected by the scoreboard operations (e.g. scoreboard 1 cannot update games coming from scoreboard 2)
  - games that are untracked by given scoreboard are ignored

### Technical
- Game can be only modified and initialized using Scoreboard
- Games are identified by their reference
- Custom domain exception classes are not defined, messages should provide sufficient failure description
- No concurrent operations are expected

## Potential improvements
- Instead of relying on object references, there could be some kind of ID-tracking approach used