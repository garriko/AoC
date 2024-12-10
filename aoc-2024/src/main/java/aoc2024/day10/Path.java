package aoc2024.day10;

import java.util.ArrayList;

import aoc.shared.model.Position;

public class Path extends ArrayList<Position> {
   private static final long serialVersionUID = 6477255332460099422L;
   public Path(Position e) {
       add(e);
   }

   public Path(Path pathToCopy) {
       for(Position pos: pathToCopy) {
           add(new Position(pos.getRow(), pos.getCol()));
       }
   }
}
