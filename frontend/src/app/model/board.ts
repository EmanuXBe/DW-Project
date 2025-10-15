export interface Board {
  id?: number;
  height?: number;
  width?: number;
  cells?: Cell[];
}

export interface Cell {
  id?: number;
  type: string;
  x: number;
  y: number;
  shipId?: number;
  shipName?: string;
}
