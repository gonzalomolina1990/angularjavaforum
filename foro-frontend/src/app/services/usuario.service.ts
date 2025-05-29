import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Usuario {
id?: number;
username: string;
password: string;
rol?: string;
}

@Injectable({
providedIn: 'root'
})
export class UsuarioService {
private apiUrl = 'https://foro-backend-5nf9.onrender.com/api/usuarios';

constructor(private http: HttpClient) {}

registrar(usuario: Usuario): Observable<Usuario> {
  return this.http.post<Usuario>(`${this.apiUrl}/registro`, usuario);
}

login(usuario: Usuario): Observable<any> {
  return this.http.post(`${this.apiUrl}/login`, usuario);
}

listar(): Observable<Usuario[]> {
  return this.http.get<Usuario[]>(`${this.apiUrl}/todos`);
}

eliminar(id: number): Observable<any> {
  return this.http.delete(`${this.apiUrl}/${id}`);
}

}