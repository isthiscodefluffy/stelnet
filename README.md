# Stellar Networks

Stellar Networks is a pure-information oriented Starsector mod. It adds three
new intel tabs: Commodity, Market, and Storage. Each tab comes with its own
interactive board that can be used to manage presented intel.

## Boards

### Commodity

Commodity board (formerly [Galactic Markets](https://fractalsoftworks.com/forum/index.php?topic=19383))
is used to display buying and selling prices among all known markets.

![Commodity](https://github.com/jaghaimo/stelnet/raw/master/images/commodity.png)

* Profit tab which calculates 5 most profitable trades.
* Shows the total distance needed to fly from players current location to location A (buy) and location B (sell)
* Highlights trades in the same system

![Profit tab](images/commodity_profit.png)

### Market

Market board (formerly [Hyperspace Networks](https://fractalsoftworks.com/forum/index.php?topic=19252))
manages market searches. These can be either staff search (e.g. freelance
administrator, steady officer, aggressive mercenary), item search (e.g. specific
mod-spec or weapon), or ship search (e.g. pristine frigate, d-modded carrier).

![Market](https://github.com/jaghaimo/stelnet/raw/master/images/market.png)

### Storage

Storage board (formerly [Stellar Logistics](https://fractalsoftworks.com/forum/index.php?topic=18948))
displays all cargo and ships stored among all Storages. It allows to display
a unified view to quickly verify what is available, and per-location view to
find out where.

![Storage](https://github.com/jaghaimo/stelnet/raw/master/images/storage.gif)

## Installation

This mod can be added to an existing playthrough - just enable it in the mod list
and load the save.

This mod can be removed from an existing playthrough - edit `data/config/settings.json`
and set `stellicsUninstallMod` to `true`. Then load the save, save the game, quit
the game, and finally disable this mod in the mod list.
