import { ChangeDetectionStrategy, Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Sidebar } from '../sidebar/sidebar';

@Component({
  selector: 'app-dashboard-layout',
  imports: [RouterOutlet, Sidebar],
  templateUrl: './dashboard-layout.html',
  styleUrl: './dashboard-layout.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DashboardLayout {
  protected readonly menuOpen = signal(false);
  protected toggleMenu(): void {
    this.menuOpen.update((open) => !open);
  }
  protected closeMenu(): void {
    this.menuOpen.set(false);
  }
}
