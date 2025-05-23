import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Tema {
id?: number;
titulo: string;
contenido: string;
usuarioId?: number;
username: string;
fechaCreacion: string;
votosPositivos: number;
votosNegativos: number;
}

@Injectable({
providedIn: 'root'
})
export class TemaService {
apiUrl = 'https://foro-backend-5nf9.onrender.com/api/temas';

constructor(private http: HttpClient) {}

listar(): Observable<Tema[]> {
  return this.http.get<Tema[]>(`${this.apiUrl}/todos`);
}

crear(tema: Tema): Observable<Tema> {
  return this.http.post<Tema>(`${this.apiUrl}/crear`, tema);
}

votarPositivo(id: number, usuarioId: number): Observable<Tema> {
return this.http.post<Tema>(`${this.apiUrl}/${id}/votar-positivo?usuarioId=${usuarioId}`, {});
}

votarNegativo(id: number, usuarioId: number): Observable<Tema> {
return this.http.post<Tema>(`${this.apiUrl}/${id}/votar-negativo?usuarioId=${usuarioId}`, {});
}
}