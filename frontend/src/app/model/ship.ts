export interface Model {
  id?: number;
  name?: string;
  color?: string;
}

export interface Owner {
  id?: number;
  name?: string;
}

export class Ship {
  id?: number;
  name?: string;

  // Velocidad (vector)
  vx?: number;
  vy?: number;

  // Posición actual en el tablero
  posX?: number;
  posY?: number;

  // Estado de la carrera
  turnCount?: number;
  racing?: boolean;
  finished?: boolean;

  // Relaciones con otros modelos (pueden ser objetos o strings/numbers)
  model?: Model | string | number;
  owner?: Owner | string | number;
}

export interface MoveDTO {
  deltaVx: number; // -1, 0, or 1
  deltaVy: number; // -1, 0, or 1
}

export interface PositionDTO {
  x: number;
  y: number;
  vx: number;
  vy: number;
  deltaVx: number;
  deltaVy: number;
  valid: boolean;
  invalidReason?: string;
}

export interface PossibleMovesDTO {
  currentX: number;
  currentY: number;
  currentVx: number;
  currentVy: number;
  possiblePositions: PositionDTO[];
}
