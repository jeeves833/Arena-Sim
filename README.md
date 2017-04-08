# A Study Into Hearthstone Arena Win Rates
by Lucas Karahadian
## Introduction

In Blizzard's digital card game Hearthstone, Arena mode is a gametype that tests players deck construction skills with a random card set.  Players are to construct a 30-card deck by choosing one card at a time from a randomized set of three.
![_Draft Image_](https://i.imgur.com/I0XUA35.jpg)

After making a deck, players enter a play format with the following rules:
1. Players will only play against other players with the same win-loss ratio for the current Arena run (A run is considered to be the entire process from deck creation to elimination and prize claiming).
2. Players are eliminated when they reach 3 losses.
3. Players are victorious when they reach 12 wins.

## Win Distributions

With these rules in mind, I set out to determine the distribution of wins in completed Arena runs.  My approach was to propagate percentages through a matrix representing win-loss rates.  Luckily, this process can be simulated without the use of any random number generation and without considering win-rates of the individual players.  At each win-loss bracket, regardless of who is playing, half of the players win and half of the players lose.  The results are below and can be recreated using option 1 in ArenaSim.java

![Results Image](https://puu.sh/vdUPc/1234a887c4.png)

Using these results we can calculate the percentage of runs ending at different wins by looking at column 3 and row 12.

![Distribution chart](https://puu.sh/vdVDU/5acccb8a9c.png) ![Distribution Graph](https://puu.sh/vdVGU/3e91e5e257.png)

### Observations

The most interesting thing to notice about this data is at the extremes.  The percent of 0-win runs is less than 1-win runs, and the percent of 11-win runs is less than 12-win runs.  My interpretation of this occurrance is that since the general movement of the players is towards the 12-win ceiling, there are many chances to hop off of the 0-win track and no influx of players into that track since it's the floor, and likewise at the 12-win ceiling there are three different win-loss results that end in 12 wins.
