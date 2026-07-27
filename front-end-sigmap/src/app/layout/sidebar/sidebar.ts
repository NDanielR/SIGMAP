import { ChangeDetectionStrategy, Component, input, output } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

interface MenuItem {
  readonly label: string;
  readonly path: string;
  readonly icon: string;
}

@Component({
  selector: 'app-sidebar',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Sidebar {
  readonly open = input(false);
  readonly navigate = output<void>();

  protected readonly menuItems: readonly MenuItem[] = [
    { label: 'Resumen', path: '/resumen', icon: '⌂' },
    { label: 'Activos QC / RTG', path: '/activos', icon: '▦' },
    { label: 'Órdenes de trabajo', path: '/ordenes', icon: '✓' },
    { label: 'Fallas y eventos', path: '/fallas', icon: '!' },
    { label: 'Indicadores', path: '/indicadores', icon: '↗' },
  ];
}
