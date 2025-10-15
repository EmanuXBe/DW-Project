import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardEditComponent } from './board-edit.component';

describe('BoardEditComponent', () => {
  let component: BoardEditComponent;
  let fixture: ComponentFixture<BoardEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BoardEditComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoardEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
