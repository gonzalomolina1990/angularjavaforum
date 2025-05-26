import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
selector: 'app-navbar',
standalone: true,
imports: [CommonModule],
templateUrl: './navbar.component.html'
})
export class NavbarComponent {
@Input() usuarioLogueado: string | null = null;
@Output() logout = new EventEmitter<void>();

onLogout() {
  this.logout.emit();
}
}