import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Task } from '../model/task.model';

@Injectable({
  providedIn: 'root',
})
export class TaskService {
  private API_URL = 'http://localhost:8080/tasks';

  constructor(private http: HttpClient) {}

  getTasks(page: number, size: number): Observable<{ content: Task[] }> {
    return this.http.get<{ content: Task[] }>(`${this.API_URL}?page=${page}&size=${size}`);
  }

  createTask(task: Task): Observable<Task> {
    return this.http.post<Task>(this.API_URL, task);
  }

  markTaskAsCompleted(id: number): Observable<Task> {
    return this.http.patch<Task>(`${this.API_URL}/${id}`, {});
  }
}
