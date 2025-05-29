import { Component, OnInit  } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegistroComponent } from './components/registro/registro.component';
import { LoginComponent } from './components/login/login.component';
import { TemasComponent } from './components/temas/temas.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { UsuarioService, Usuario } from './services/usuario.service';



@Component({
selector: 'app-root',
standalone: true,
imports: [RegistroComponent, LoginComponent, TemasComponent, CommonModule, NavbarComponent],
templateUrl:'app.component.html'
})
export class AppComponent implements OnInit {
title = 'foro-frontend';

usuarios: Usuario[] = [];
mensajeUsuarios: string = '';

constructor(private usuarioService: UsuarioService) {}

ngOnInit() {
  this.cargarUsuarios();
}

cargarUsuarios() {
  this.usuarioService.listar().subscribe({
    next: usuarios => this.usuarios = usuarios,
    error: err => this.mensajeUsuarios = 'Error al cargar usuarios'
  });
}

eliminarUsuario(id: number) {
  if (!confirm('¿Seguro que deseas eliminar este usuario?')) return;
  this.usuarioService.eliminar(id).subscribe(() => {
    this.cargarUsuarios();
  });
}

get usuarioLogueado(): string | null {
  return localStorage.getItem('usuario');
}

get esAdmin(): boolean {
  return localStorage.getItem('rol') === 'ADMIN';
}

logout() {
  localStorage.removeItem('usuario');
  localStorage.removeItem('usuarioId');
  localStorage.removeItem('rol');
  window.location.reload(); // o recarga datos del estado si prefieres SPA pura
}
}