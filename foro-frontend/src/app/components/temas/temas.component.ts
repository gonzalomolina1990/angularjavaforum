import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TemaService, Tema } from '../../services/tema.service';
import { ComentariosComponent } from '../comentarios/comentarios.component';


@Component({
selector: 'app-temas',
standalone: true,
imports: [CommonModule, FormsModule, ComentariosComponent],
templateUrl: './temas.component.html'
})
export class TemasComponent implements OnInit {
temas: Tema[] = [];
titulo = '';
contenido = '';
mensaje = '';

constructor(private temaService: TemaService) {}

ngOnInit() {
  this.cargarTemas();
}

cargarTemas() {
  this.temaService.listar().subscribe({
    next: temas => this.temas = temas,
    error: err => this.mensaje = 'Error al cargar temas'
  });
}

crearTema() {
const usuarioId = localStorage.getItem('usuarioId');
if (!usuarioId) {
  this.mensaje = 'Debes estar logueado para crear un tema.';
  return;
}
this.temaService.crear({
titulo: this.titulo,
contenido: this.contenido,
usuarioId: Number(usuarioId)
} as any).subscribe({
  next: tema => {
    this.mensaje = 'Tema creado!';
    this.titulo = '';
    this.contenido = '';
    this.cargarTemas();
  },
  error: err => this.mensaje = 'Error al crear tema'
});
}

mensajeVoto: string = '';


votarPositivo(id: number) {
const usuarioId = localStorage.getItem('usuarioId');
if (!usuarioId) return;
this.temaService.votarPositivo(id, Number(usuarioId)).subscribe({
  next: ()=>{
    this.cargarTemas();
    this.mensajeVoto ="";
  },
  error: err => {
    if(err.error && typeof err.error === "string" && err.error.includes('Ya votaste')){
      this.mensajeVoto = 'Ya votaste este tema';
    } else {
      this.mensajeVoto = 'Error al votar.';
    }
     this.cargarTemas();
     setTimeout(() => this.mensajeVoto = '', 3000);
  }
});
}

votarNegativo(id: number) {
const usuarioId = localStorage.getItem('usuarioId');
if (!usuarioId) return;
this.temaService.votarNegativo(id, Number(usuarioId)).subscribe({
  next: ()=>{
    this.cargarTemas();
    this.mensajeVoto ="";
  },
  error: err => {
    if(err.error && typeof err.error === "string" && err.error.includes('Ya votaste')){
      this.mensajeVoto = 'Ya votaste este tema';
    } else {
      this.mensajeVoto = 'Error al votar.';
    }
     this.cargarTemas();
     setTimeout(() => this.mensajeVoto = '', 3000);
  }
});
}

get usuarioLogueado(): boolean {
  return !!localStorage.getItem('usuario');
}

}