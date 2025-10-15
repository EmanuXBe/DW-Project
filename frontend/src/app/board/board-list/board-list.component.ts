import { Component, signal, inject, OnInit } from '@angular/core';
import { Board } from '../../model/board';
import { BoardService } from '../../shared/board.service';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-board-list',
  imports: [RouterLink],
  templateUrl: './board-list.component.html',
  styleUrl: './board-list.component.css',
})
export class BoardListComponent implements OnInit {
  boards = signal<Board[]>([]);
  router = inject(Router);
  boardService = inject(BoardService);

  ngOnInit() {
    this.loadBoards();
  }

  loadBoards() {
    this.boardService.findAll().subscribe({
      next: (data) => {
        this.boards.set(data);
        // Si no hay boards, generar uno automáticamente
        if (data.length === 0) {
          this.generateDefaultMap();
        }
      },
      error: (err) => {
        console.error('Error cargando boards:', err);
      },
    });
  }

  generateDefaultMap() {
    this.boardService.generateDefaultMap(15, 11).subscribe({
      next: (board) => {
        console.log('Mapa generado automáticamente:', board);
        this.loadBoards();
      },
      error: (err) => {
        console.error('Error generando mapa:', err);
      },
    });
  }

  goToUsers() {
    this.router.navigate(['/users']);
  }

  goToShips() {
    this.router.navigate(['/ships']);
  }

  goToModels() {
    this.router.navigate(['/models']);
  }
}
