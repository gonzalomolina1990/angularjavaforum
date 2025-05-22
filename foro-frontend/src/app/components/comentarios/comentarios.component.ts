import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ComentarioService, Comentario } from '../../services/comentario.service';

@Component({
selector: 'app-comentarios',
standalone: true,
imports: [CommonModule, FormsModule],
templateUrl: './comentarios.component.html'
})
export class ComentariosComponent implements OnInit {
@Input() temaId!: number;
comentarios: Comentario[] = [];
contenido = '';
mensaje = '';

get usuarioLogueado(): boolean {
  return !!localStorage.getItem('usuario');
}

get usuarioId(): number | null {
  const id = localStorage.getItem('usuarioId');
  return id ? Number(id) : null;
}

constructor(private comentarioService: ComentarioService) {}

ngOnInit() {
  this.cargarComentarios();
}

cargarComentarios() {
  this.comentarioService.listarPorTema(this.temaId).subscribe({
    next: comentarios => this.comentarios = comentarios,
    error: err => this.mensaje = 'Error al cargar comentarios'
  });
}

crearComentario() {
  if (!this.usuarioLogueado || !this.usuarioId) {
    this.mensaje = 'Debes estar logueado para comentar.';
    return;
  }
this.comentarioService.crear({
  contenido: this.contenido,
  temaId: this.temaId,
  usuarioId: this.usuarioId
  }).subscribe({
  next: comentario => {
    this.mensaje = 'Comentario agregado!';
    this.contenido = '';
    this.cargarComentarios();
  },
  error: err => this.mensaje = 'Error al agregar comentario'
});
}
}