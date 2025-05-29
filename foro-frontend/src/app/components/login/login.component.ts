import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UsuarioService } from '../../services/usuario.service';

@Component({
selector: 'app-login',
standalone: true,
imports: [CommonModule, FormsModule],
templateUrl: './login.component.html'
})
export class LoginComponent {
username = '';
password = '';
mensaje = '';
tipoAlerta: string = 'success'; // 'success', 'danger', 'warning', etc.


constructor(private usuarioService: UsuarioService) {}

login() {
this.usuarioService.login({ username: this.username, password: this.password })
  .subscribe({
    next: (respuesta: any) => {
      this.mensaje = 'Login exitoso!';
      this.tipoAlerta = 'success';

      // Guarda el id y el username en localStorage
      localStorage.setItem('usuario', respuesta.username);
      localStorage.setItem('usuarioId', respuesta.id.toString());
      localStorage.setItem('rol', respuesta.rol); 
      this.username = '';
      this.password = '';
    },
    error: err => {
      this.mensaje = 'Error al iniciar sesión: ' + (err.error?.message || err.message);
      this.tipoAlerta = 'danger';

    }
  });
}
}