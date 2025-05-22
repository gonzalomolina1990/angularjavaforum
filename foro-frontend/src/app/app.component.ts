import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegistroComponent } from './components/registro/registro.component';
import { LoginComponent } from './components/login/login.component';
import { TemasComponent } from './components/temas/temas.component';

@Component({
selector: 'app-root',
standalone: true,
imports: [RegistroComponent, LoginComponent, TemasComponent, CommonModule],
templateUrl:'app.component.html'
})
export class AppComponent {
title = 'foro-frontend';

get usuarioLogueado(): string | null {
  return localStorage.getItem('usuario');
}

logout() {
  localStorage.removeItem('usuario');
}
}