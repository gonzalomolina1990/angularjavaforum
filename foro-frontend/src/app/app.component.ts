import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegistroComponent } from './components/registro/registro.component';
import { LoginComponent } from './components/login/login.component';
import { TemasComponent } from './components/temas/temas.component';
import { NavbarComponent } from './components/navbar/navbar.component';


@Component({
selector: 'app-root',
standalone: true,
imports: [RegistroComponent, LoginComponent, TemasComponent, CommonModule, NavbarComponent],
templateUrl:'app.component.html'
})
export class AppComponent {
title = 'foro-frontend';

get usuarioLogueado(): string | null {
  return localStorage.getItem('usuario');
}

logout() {
  localStorage.removeItem('usuario');
  localStorage.removeItem('usuarioId');
  window.location.reload(); // o recarga datos del estado si prefieres SPA pura
}
}