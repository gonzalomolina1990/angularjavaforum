import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Comentario {
id?: number;
contenido: string;
temaId: number;
usuarioId: number;
username: string;
fechaCreacion: string;
}

export interface CrearComentarioRequest {
contenido: string;
temaId: number;
usuarioId: number;
}


@Injectable({
providedIn: 'root'
})
export class ComentarioService {
apiUrl = 'https://foro-backend-5nf9.onrender.com/api/comentarios';

constructor(private http: HttpClient) {}

listarPorTema(temaId: number): Observable<Comentario[]> {
  return this.http.get<Comentario[]>(`${this.apiUrl}/por-tema/${temaId}`);
}

crear(comentario: CrearComentarioRequest): Observable<Comentario> {
return this.http.post<Comentario>(`${this.apiUrl}/crear`, comentario);
}

}