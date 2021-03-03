FORWARD MODEL:
-- odkaz na STATIC DATA
-- jaký výřez dynamických obsabuje (x, width) => 1 int na reprezentaci
-- cameraX, cameraY
-- currentTick (depth in game tree)
-- freeze after Mario hit? ... freeze frame (asi se dá skipnout, visuální věc)

ENTITY
  -- pixelX (22bitů?), pixelY (10bit?) => možná bude stačit 1 int na repezentaci
  -- tileX, tileY => potřebujeme tohle?

  -- MARIO
     -- rychlost
     -- jump frame?
  -- ENEMIES
     -- typ => odkaz na statickou informaci
     -- vnitřní stav? => na to je se třeba podívat
  -- SHELLs a FIREBALLs
     -- vnitřní stav? => na to je se třeba podívat
 
-- BREAKABLE TILES
  -- že můžou být stornuté v Map<int, BreakableTiles>
                                 +- int zase kóduje tileX, tileY

-- COLLECTIBLES
  -- něco jako ENTITIES, ale dají se picknout

=============================

TASK:

ORIGINAL OOP FORWARD MODEL => to je ten, co je teď v Máriovi

teď chceme naimplementovat

SLIM OOP FORWARD MODEL
  -- rozdělí data na statická a dynamická
  -- clonovat se budou pouze dynamická data
  -- má na sobě metodu "advance", která dokáže spočítat další stav světa
 
KONVERTOR: ORIGINAL OOP => SLIM OOP
  -- přičemž je možné zadat jak velkou část světa si to vyřizne

TARGET JE:
  Original original = ...;
  Slim slim = Converter.Convert(original);
  original.Advance();
  slim.Advance();
  Slim slim2 = Converter.Convert(original);

  ??? slim.equals(slim2) ??? <== tohle je náš target


STEPS:
1) nastřelit SLIM OOP MODEL (ve vlastním package)
2) implementovat Advance() s tím, že nás moc nezajímá efektivita zatím
3) implementovat Converter
4) implementovat .equals pro SLIM OOP MODEL
5) udělat validovacího agenta, který bude sledovat, jestli SLIM OOP běží jako ORIGINAL OOP
