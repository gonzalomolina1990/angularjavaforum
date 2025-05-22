import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UsuarioService } from '../../services/usuario.service';

@Component({
selector: 'app-registro',
standalone: true,
imports: [CommonModule, FormsModule],
templateUrl: './registro.component.html'
})
export class RegistroComponent {
username = '';
password = '';
mensaje = '';

constructor(private usuarioService: UsuarioService) {}

registrar() {
  this.usuarioService.registrar({ username: this.username, password: this.password })
    .subscribe({
      next: usuario => {
        this.mensaje = 'Usuario registrado con éxito!';
        this.username = '';
        this.password = '';
      },
      error: err => {
        this.mensaje = 'Error al registrar usuario: ' + (err.error?.message || err.message);
      }
    });
}
}