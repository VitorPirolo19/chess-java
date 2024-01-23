package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {
    private ChessMatch chessMatch;

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public String toString(){
        return "K";
    }

    private boolean testRookCastling(Position position){
        ChessPiece p = (ChessPiece) getBoard().piece(position);
        return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
    }

    private boolean canMove(Position position){
        ChessPiece auxPiece = (ChessPiece)getBoard().piece(position);
        return auxPiece == null || auxPiece.getColor() != getColor();
    }
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position auxPosition = new Position(0,0);

        //above
        auxPosition.setValues(position.getRow()-1, position.getColumn());
        if(getBoard().positionExists(auxPosition) && canMove(auxPosition)){
            mat[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        //below
        auxPosition.setValues(position.getRow() + 1, position.getColumn());
        if(getBoard().positionExists(auxPosition) && canMove(auxPosition)){
            mat[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        //left
        auxPosition.setValues(position.getRow(), position.getColumn() -1);
        if(getBoard().positionExists(auxPosition) && canMove(auxPosition)){
            mat[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        //rigth
        auxPosition.setValues(position.getRow(), position.getColumn() + 1);
        if(getBoard().positionExists(auxPosition) && canMove(auxPosition)){
            mat[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        //nw
        auxPosition.setValues(position.getRow() -1, position.getColumn()-1);
        if(getBoard().positionExists(auxPosition) && canMove(auxPosition)){
            mat[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        //ne
        auxPosition.setValues(position.getRow() - 1, position.getColumn() + 1);
        if(getBoard().positionExists(auxPosition) && canMove(auxPosition)){
            mat[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        //sw
        auxPosition.setValues(position.getRow() + 1, position.getColumn() -1);
        if(getBoard().positionExists(auxPosition) && canMove(auxPosition)){
            mat[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        //se
        auxPosition.setValues(position.getRow()+1, position.getColumn()+1);
        if(getBoard().positionExists(auxPosition) && canMove(auxPosition)){
            mat[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        //#specialmove castling
        if (getMoveCount() == 0 && !chessMatch.getCheck()){
            //castling kingside rook
            Position posT1 = new Position(position.getRow(), position.getColumn()+3);
            if(testRookCastling(posT1)) {
                Position p1 = new Position(position.getRow(), position.getColumn()+1);
                Position p2 = new Position(position.getRow(), position.getColumn() +2);
                if(getBoard().piece(p1) == null && getBoard().piece(p2) == null ){
                    mat[position.getRow()][position.getColumn() + 2] = true;
                }
            }
            //castling queenside rook
            Position posT2 = new Position(position.getRow(), position.getColumn()-4);
            if(testRookCastling(posT2)) {
                Position p1 = new Position(position.getRow(), position.getColumn()-1);
                Position p2 = new Position(position.getRow(), position.getColumn() -2);
                Position p3 = new Position(position.getRow(), position.getColumn() -3);
                if(getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null){
                    mat[position.getRow()][position.getColumn() - 2] = true;
                }
            }
        }
        return mat;
    }
}
