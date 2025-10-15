import { Component, signal, inject, OnInit } from '@angular/core';
import { Board, Cell } from '../../model/board';
import { BoardService } from '../../shared/board.service';
import { ActivatedRoute, Router } from '@angular/router';
import { switchMap } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-board-view',
  imports: [CommonModule],
  templateUrl: './board-view.component.html',
  styleUrl: './board-view.component.css',
})
export class BoardViewComponent implements OnInit {
  board = signal<Board | null>(null);
  boardGrid = signal<Cell[][]>([]);

  boardService = inject(BoardService);
  route = inject(ActivatedRoute);
  router = inject(Router);

  ngOnInit(): void {
    this.route.params
      .pipe(switchMap((params) => this.boardService.findById(params['id'])))
      .subscribe((resp) => {
        this.board.set(resp);
        this.buildGrid(resp);
      });
  }

  buildGrid(board: Board) {
    if (!board.cells || !board.height || !board.width) return;

    // Crear una matriz bidimensional
    const grid: Cell[][] = [];

    for (let y = 0; y < board.height; y++) {
      const row: Cell[] = [];
      for (let x = 0; x < board.width; x++) {
        const cell = board.cells.find((c) => c.x === x && c.y === y);
        if (cell) {
          row.push(cell);
        } else {
          // Celda vacía por defecto
          row.push({ x, y, type: ' ' });
        }
      }
      grid.push(row);
    }

    this.boardGrid.set(grid);
  }

  getCellClass(cell: Cell): string {
    if (cell.shipId) return 'cell-ship';

    switch (cell.type) {
      case ' ':
        return 'cell-water';
      case 'X':
        return 'cell-wall';
      case 'P':
        return 'cell-start';
      case 'M':
        return 'cell-finish';
      default:
        return 'cell-water';
    }
  }

  getCellSymbol(cell: Cell): string {
    if (cell.shipName) return '🚢';

    switch (cell.type) {
      case ' ':
        return '';
      case 'X':
        return '❌';
      case 'P':
        return '🟢';
      case 'M':
        return '🏁';
      default:
        return '';
    }
  }

  goBack() {
    this.router.navigate(['/boards']);
  }
}
