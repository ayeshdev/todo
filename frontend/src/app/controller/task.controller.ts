import { Injectable, signal } from '@angular/core';
import { TaskService } from '../service/task.service';
import { Task } from '../model/task.model';

@Injectable({
  providedIn: 'root',
})
export class TaskController {
  tasks = signal<Task[]>([]);
  page = signal(0);
  size = 5;
  hasMore = signal(true);

  constructor(private taskService: TaskService) {}

  loadTasks() {
    this.taskService.getTasks(this.page(), this.size).subscribe((data) => {
      if (data.content.length === 0) {
        this.hasMore.set(false);
      } else {
        this.tasks.set([...this.tasks(), ...data.content]);
        this.page.set(this.page() + 1);
      }
    });
  }

  addTask(title: string, description: string) {
    console.log(title);
    console.log(description);

    if (!title || !description) return;
    this.taskService.createTask({ title, description, completed: false }).subscribe(() => {
      this.page.set(0);
      this.tasks.set([]);
      this.loadTasks();
    });
  }

  completeTask(id: number) {
    this.taskService.markTaskAsCompleted(id).subscribe(() => {
      this.page.set(0);
      this.tasks.set([]);
      this.loadTasks();
    });
  }
}
