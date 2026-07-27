import { Routes } from '@angular/router';
import { DashboardLayout } from './layout/dashboard-layout/dashboard-layout';

export const routes: Routes = [
  {
    path: '',
    component: DashboardLayout,
    children: [
      { path: '', pathMatch: 'full', redirectTo: 'resumen' },
      {
        path: 'resumen',
        title: 'Resumen | Aguadulce',
        data: { dashboardId: 'summary' },
        loadComponent: () =>
          import('./features/dashboard/dashboard-page').then((m) => m.DashboardPage),
      },
      {
        path: 'activos',
        title: 'Activos | Aguadulce',
        data: { dashboardId: 'assets' },
        loadComponent: () =>
          import('./features/dashboard/dashboard-page').then((m) => m.DashboardPage),
      },
      {
        path: 'ordenes',
        title: 'Órdenes de trabajo | Aguadulce',
        data: { dashboardId: 'workOrders' },
        loadComponent: () =>
          import('./features/dashboard/dashboard-page').then((m) => m.DashboardPage),
      },
      {
        path: 'fallas',
        title: 'Fallas y eventos | Aguadulce',
        data: { dashboardId: 'failures' },
        loadComponent: () =>
          import('./features/dashboard/dashboard-page').then((m) => m.DashboardPage),
      },
      {
        path: 'indicadores',
        title: 'Indicadores | Aguadulce',
        data: { dashboardId: 'indicators' },
        loadComponent: () =>
          import('./features/dashboard/dashboard-page').then((m) => m.DashboardPage),
      },
    ],
  },
  { path: '**', redirectTo: 'resumen' },
];
