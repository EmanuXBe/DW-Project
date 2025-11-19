import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ShipService } from '../../shared/ship.service';
import { Ship } from '../../model/ship';

@Component({
  selector: 'app-game-setup',
  imports: [CommonModule],
  templateUrl: './game-setup.component.html',
  styleUrl: './game-setup.component.css',
})
export class GameSetupComponent implements OnInit {
  myShip = signal<Ship | null>(null);
  loading = signal<boolean>(true);
  error = signal<string | null>(null);

  constructor(private router: Router, private shipService: ShipService) {}

  ngOnInit(): void {
    this.loadMyShip();
  }

  loadMyShip(): void {
    this.loading.set(true);
    this.shipService.getMyShip().subscribe({
      next: (ship) => {
        console.log('My ship loaded:', ship);
        this.myShip.set(ship);
        this.loading.set(false);
      },
      error: (err) => {
        console.error('Error loading my ship:', err);
        this.error.set('No tienes un barco asignado. Contacta al administrador.');
        this.loading.set(false);
      },
    });
  }

  startGame(): void {
    const ship = this.myShip();

    if (!ship || !ship.id) {
      alert('No se pudo cargar tu barco');
      return;
    }

    // El tablero se selecciona automáticamente (ID 1 por defecto)
    this.router.navigate(['/game'], {
      queryParams: {
        shipId: ship.id,
        boardId: 1, // Tablero por defecto
      },
    });
  }
}
