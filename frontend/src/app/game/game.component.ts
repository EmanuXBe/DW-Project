import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { ShipService } from '../shared/ship.service';
import { BoardService } from '../shared/board.service';
import { Ship, PossibleMovesDTO, PositionDTO } from '../model/ship';
import { Board, Cell } from '../model/board';

@Component({
  selector: 'app-game',
  imports: [CommonModule],
  templateUrl: './game.component.html',
  styleUrl: './game.component.css',
})
export class GameComponent implements OnInit {
  shipId = signal<number>(0);
  boardId = signal<number>(0);

  ship = signal<Ship>({});
  board = signal<Board>({});
  grid = signal<Cell[][]>([]);

  possibleMoves = signal<PossibleMovesDTO | null>(null);
  selectedMove = signal<PositionDTO | null>(null);

  gameStarted = signal<boolean>(false);
  gameOver = signal<boolean>(false);

  constructor(
    private route: ActivatedRoute,
    public router: Router,
    private shipService: ShipService,
    private boardService: BoardService
  ) {}

  ngOnInit(): void {
    const shipIdParam = this.route.snapshot.queryParamMap.get('shipId');
    const boardIdParam = this.route.snapshot.queryParamMap.get('boardId');

    console.log('Game Component Init - shipId:', shipIdParam, 'boardId:', boardIdParam);

    if (shipIdParam && boardIdParam) {
      this.shipId.set(+shipIdParam);
      this.boardId.set(+boardIdParam);
      this.loadGame();
    } else {
      alert('Por favor selecciona un barco y tablero para jugar');
      this.router.navigate(['/game-setup']);
    }
  }

  loadGame(): void {
    console.log('Loading game with shipId:', this.shipId(), 'boardId:', this.boardId());

    // Cargar PRIMERO el tablero
    this.boardService.findById(this.boardId()).subscribe({
      next: (boardData) => {
        console.log('Board loaded:', boardData);
        console.log('Board cells:', boardData.cells?.length);
        console.log('First 5 cells:', boardData.cells?.slice(0, 5));
        this.board.set(boardData);
        this.buildGrid(boardData);

        // DESPUÉS cargar el barco
        this.shipService.findById(this.shipId()).subscribe({
          next: (shipData) => {
            console.log('Ship loaded:', shipData);
            this.ship.set(shipData);

            // Si el barco ya está corriendo, cargar movimientos posibles
            if (shipData.racing) {
              this.gameStarted.set(true);
              this.loadPossibleMoves();
            } else {
              // Iniciar el juego automáticamente en la primera celda START disponible
              this.autoStartGame();
            }
          },
          error: (err) => console.error('Error cargando barco:', err),
        });
      },
      error: (err) => console.error('Error cargando tablero:', err),
    });
  }

  buildGrid(board: Board): void {
    console.log(
      'Building grid - width:',
      board.width,
      'height:',
      board.height,
      'cells:',
      board.cells?.length
    );

    if (!board.cells || board.width === undefined || board.height === undefined) {
      console.warn('Cannot build grid - missing data');
      return;
    }

    const grid: Cell[][] = [];
    for (let y = 0; y < board.height; y++) {
      grid[y] = [];
      for (let x = 0; x < board.width; x++) {
        const cell = board.cells.find((c) => c.x === x && c.y === y);
        if (cell) {
          grid[y][x] = cell;
        }
      }
    }
    console.log('Grid built with', grid.length, 'rows');
    this.grid.set(grid);
  }

  autoStartGame(): void {
    // Buscar la primera celda START en el tablero
    const board = this.board();
    if (!board.cells) return;

    const startCell = board.cells.find((cell) => cell.type === 'P' || cell.type === 'START');
    if (startCell && startCell.x !== undefined && startCell.y !== undefined) {
      console.log('Auto-starting game at position:', startCell.x, startCell.y);
      this.startRace(startCell.x, startCell.y);
    }
  }

  startRace(startX: number, startY: number): void {
    this.shipService.startRace(this.shipId(), this.boardId(), startX, startY).subscribe({
      next: (data) => {
        this.ship.set(data);
        this.gameStarted.set(true);
        this.loadPossibleMoves();
      },
      error: (err) => {
        console.error('Error iniciando carrera:', err);
        alert('No se pudo iniciar la carrera: ' + (err.error || err.message));
      },
    });
  }

  loadPossibleMoves(): void {
    this.shipService.getPossibleMoves(this.shipId(), this.boardId()).subscribe({
      next: (data) => {
        this.possibleMoves.set(data);
        this.selectedMove.set(null);
      },
      error: (err) => console.error('Error cargando movimientos:', err),
    });
  }

  selectMove(position: PositionDTO): void {
    if (position.valid) {
      this.selectedMove.set(position);
    }
  }

  executeMove(): void {
    const move = this.selectedMove();
    if (!move) {
      alert('Selecciona un movimiento primero');
      return;
    }

    this.shipService
      .executeMove(this.shipId(), this.boardId(), {
        deltaVx: move.deltaVx,
        deltaVy: move.deltaVy,
      })
      .subscribe({
        next: (data) => {
          this.ship.set(data);

          if (data.finished) {
            this.gameOver.set(true);
            alert(`¡Felicidades! Terminaste en ${data.turnCount} turnos`);
          } else {
            this.loadPossibleMoves();
          }
        },
        error: (err) => {
          console.error('Error ejecutando movimiento:', err);
          alert('Movimiento inválido: ' + (err.error || err.message));
        },
      });
  }

  getCellClass(cell: Cell, x: number, y: number): string {
    const classes = ['cell'];

    // Clase según tipo de celda
    let cellTypeClass = '';
    switch (cell.type) {
      case 'P':
        cellTypeClass = 'cell-start';
        break;
      case 'M':
        cellTypeClass = 'cell-finish';
        break;
      case 'X':
        cellTypeClass = 'cell-wall';
        break;
      case ' ':
        cellTypeClass = 'cell-water';
        break;
      default:
        cellTypeClass = `cell-${cell.type?.toLowerCase()}`;
    }
    classes.push(cellTypeClass);

    // Si es la posición actual del barco
    if (this.gameStarted() && this.ship().posX === x && this.ship().posY === y) {
      classes.push('cell-ship');
    }

    return classes.join(' ');
  }

  getCellContent(cell: Cell, x: number, y: number): string {
    // Mostrar el barco si está en esta posición
    if (this.gameStarted() && this.ship().posX === x && this.ship().posY === y) {
      return '🚢';
    }

    // Mostrar símbolo de la celda
    switch (cell.type) {
      case 'START':
      case 'P':
        return '🏁';
      case 'FINISH':
      case 'M':
        return '🏆';
      case 'WALL':
      case 'X':
        return '🧱';
      case 'WATER':
      case ' ':
        return '🌊';
      default:
        return '';
    }
  }

  isPossibleMove(x: number, y: number): boolean {
    const moves = this.possibleMoves();
    if (!moves) return false;

    const pos = moves.possiblePositions.find((p) => p.x === x && p.y === y);
    return pos !== undefined && pos.valid;
  }

  onCellClick(cell: Cell, x: number, y: number): void {
    // Solo permitir click en celdas START para iniciar el juego
    if (!this.gameStarted() && cell.type === 'START') {
      this.startRace(x, y);
    }
  }

  restartGame(): void {
    // Reiniciar el estado del juego en el frontend
    this.gameStarted.set(false);
    this.gameOver.set(false);
    this.possibleMoves.set(null);
    this.selectedMove.set(null);

    // Buscar la primera celda START para reiniciar
    const board = this.board();
    if (board.cells) {
      const startCell = board.cells.find((cell) => cell.type === 'P' || cell.type === 'START');
      if (startCell?.x !== undefined && startCell?.y !== undefined) {
        // Reiniciar la carrera en el backend desde la posición inicial
        this.startRace(startCell.x, startCell.y);
      } else {
        // Fallback: si no hay celda START, recargar el juego completo
        this.loadGame();
      }
    } else {
      // Fallback: si no hay board cargado, recargar todo
      this.loadGame();
    }
  }

  changeVelocity(deltaVx: number, deltaVy: number): void {
    // Buscar el movimiento correspondiente en las opciones disponibles
    const moves = this.possibleMoves();
    if (!moves) return;

    const targetMove = moves.possiblePositions.find(
      (pos) => pos.deltaVx === deltaVx && pos.deltaVy === deltaVy
    );

    if (targetMove && targetMove.valid) {
      this.selectMove(targetMove);
    } else if (targetMove) {
      alert(`Movimiento inválido: ${targetMove.invalidReason}`);
    }
  }
}
