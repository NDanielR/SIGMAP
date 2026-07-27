import { Injectable } from '@angular/core';
import { DashboardConfig, DashboardId, ProgressItem, TableRow } from '../models/dashboard.models';

const STATUS: readonly ProgressItem[] = [
  { label: 'Operativo', value: 90, displayValue: '23', accent: 'success' },
  { label: 'Atención', value: 48, displayValue: '4', accent: 'warning' },
  { label: 'Crítico', value: 23, displayValue: '2', accent: 'danger' },
];

const WORK_ROWS: readonly TableRow[] = [
  {
    reference: 'OT-2841',
    equipment: 'QC-03',
    activity: 'Encoder trolley',
    indicator: 'Crítica',
    status: 'En ejecución',
  },
  {
    reference: 'OT-2836',
    equipment: 'RTG-12',
    activity: 'Inspección spreader',
    indicator: 'Alta',
    status: 'Programada',
  },
  {
    reference: 'OT-2829',
    equipment: 'QC-01',
    activity: 'Motor hoist',
    indicator: 'Alta',
    status: 'Pendiente',
  },
  {
    reference: 'OT-2814',
    equipment: 'RTG-07',
    activity: 'Sensores gantry',
    indicator: 'Media',
    status: 'Completada',
  },
];

const CONFIGS: Readonly<Record<DashboardId, DashboardConfig>> = {
  summary: {
    title: 'Resumen de mantenimiento',
    subtitle: 'Confiabilidad y operación de equipos portuarios',
    chartTitle: 'Tendencia operacional',
    tableTitle: 'Detalle operativo',
    metrics: [
      {
        code: 'DI',
        label: 'Disponibilidad',
        value: '94,8 %',
        caption: '+1,7 %',
        accent: 'success',
      },
      { code: 'MT', label: 'MTBF', value: '118 h', caption: '+8 h', accent: 'success' },
      { code: 'MT', label: 'MTTR', value: '4,6 h', caption: '−0,9 h', accent: 'success' },
      { code: 'OT', label: 'OT críticas', value: '7', caption: '2 vencen hoy', accent: 'danger' },
    ],
    chart: [
      { label: 'Mar', value: 70 },
      { label: 'Abr', value: 78 },
      { label: 'May', value: 84 },
      { label: 'Jun', value: 90 },
      { label: 'Jul', value: 95 },
    ],
    status: STATUS,
    rows: WORK_ROWS,
  },
  assets: {
    title: 'Activos QC y RTG',
    subtitle: 'Condición técnica y disponibilidad por equipo',
    chartTitle: 'Tendencia operacional',
    tableTitle: 'Detalle operativo',
    metrics: [
      { code: 'RE', label: 'Registrados', value: '29', caption: 'Inventario', accent: 'success' },
      { code: 'OP', label: 'Operativos', value: '23', caption: '79 % flota', accent: 'success' },
      { code: 'EN', label: 'En mantto.', value: '4', caption: 'Activos', accent: 'warning' },
      { code: 'FU', label: 'Fuera servicio', value: '2', caption: 'Prioridad', accent: 'danger' },
    ],
    chart: [
      { label: 'QC-01', value: 95 },
      { label: 'QC-03', value: 62 },
      { label: 'RTG-07', value: 91 },
      { label: 'RTG-12', value: 82 },
      { label: 'Promedio', value: 90 },
    ],
    status: STATUS,
    rows: [
      {
        reference: 'QC-03',
        equipment: 'QC',
        activity: 'Trolley',
        indicator: '71,2 %',
        status: 'Fuera servicio',
      },
      {
        reference: 'RTG-12',
        equipment: 'RTG',
        activity: 'Spreader',
        indicator: '88,6 %',
        status: 'Mantenimiento',
      },
      {
        reference: 'QC-01',
        equipment: 'QC',
        activity: 'Hoist',
        indicator: '96,8 %',
        status: 'Operativo',
      },
      {
        reference: 'RTG-07',
        equipment: 'RTG',
        activity: 'Gantry',
        indicator: '94,3 %',
        status: 'Operativo',
      },
    ],
  },
  workOrders: {
    title: 'Órdenes de trabajo',
    subtitle: 'Planificación, seguimiento y cumplimiento',
    chartTitle: 'Tendencia operacional',
    tableTitle: 'Detalle operativo',
    metrics: [
      { code: 'OT', label: 'OT abiertas', value: '46', caption: '12 nuevas', accent: 'success' },
      { code: 'EN', label: 'En ejecución', value: '18', caption: '39 %', accent: 'success' },
      { code: 'VE', label: 'Vencidas', value: '6', caption: 'Reprogramar', accent: 'danger' },
      { code: 'CU', label: 'Cumplimiento', value: '87 %', caption: '+4 %', accent: 'success' },
    ],
    chart: [
      { label: 'Lunes', value: 84 },
      { label: 'Martes', value: 70 },
      { label: 'Miércoles', value: 94 },
      { label: 'Jueves', value: 78 },
      { label: 'Viernes', value: 60 },
    ],
    status: STATUS,
    rows: WORK_ROWS,
  },
  failures: {
    title: 'Fallas y eventos',
    subtitle: 'Recurrencia, severidad y causas probables',
    chartTitle: 'Pareto por sistema',
    tableTitle: 'Registro reciente',
    metrics: [
      { code: 'FA', label: 'Fallas', value: '128', caption: '12 meses', accent: 'warning' },
      { code: 'RE', label: 'Reincidentes', value: '19', caption: '14,8 %', accent: 'success' },
      { code: 'HO', label: 'Horas detenidas', value: '342 h', caption: '−11 %', accent: 'success' },
      {
        code: 'SI',
        label: 'Sistema principal',
        value: 'Hoist',
        caption: '28 %',
        accent: 'success',
      },
    ],
    chart: [
      { label: 'Hoist', value: 96 },
      { label: 'Trolley', value: 78 },
      { label: 'Spreader', value: 62 },
      { label: 'Gantry', value: 48 },
      { label: 'Control', value: 35 },
    ],
    status: STATUS,
    rows: [
      {
        reference: 'EV-382',
        equipment: 'QC-03',
        activity: 'Pérdida encoder',
        indicator: '3,4 h',
        status: 'Crítica',
      },
      {
        reference: 'EV-381',
        equipment: 'RTG-12',
        activity: 'Interlock twistlock',
        indicator: '1,8 h',
        status: 'Alta',
      },
      {
        reference: 'EV-379',
        equipment: 'QC-01',
        activity: 'Temperatura motor',
        indicator: '0,9 h',
        status: 'Media',
      },
      {
        reference: 'EV-374',
        equipment: 'RTG-07',
        activity: 'Diferencia posición',
        indicator: '1,2 h',
        status: 'Media',
      },
    ],
  },
  indicators: {
    title: 'Indicadores de confiabilidad',
    subtitle: 'MTBF, MTTR, disponibilidad y frecuencia',
    chartTitle: 'Tendencia operacional',
    tableTitle: 'Detalle operativo',
    metrics: [
      {
        code: 'DI',
        label: 'Disponibilidad',
        value: '94,8 %',
        caption: 'Meta 95 %',
        accent: 'success',
      },
      { code: 'MT', label: 'MTBF', value: '118 h', caption: 'Meta 120 h', accent: 'success' },
      { code: 'MT', label: 'MTTR', value: '4,6 h', caption: 'Meta ≤ 5 h', accent: 'success' },
      { code: 'FR', label: 'Frecuencia', value: '2,3', caption: '/ 1.000 h', accent: 'success' },
    ],
    chart: [
      { label: 'Mar', value: 74 },
      { label: 'Abr', value: 80 },
      { label: 'May', value: 86 },
      { label: 'Jun', value: 90 },
      { label: 'Jul', value: 95 },
    ],
    status: STATUS,
    rows: [
      {
        reference: 'QC-01',
        equipment: 'QC',
        activity: 'Confiabilidad',
        indicator: '96,8 %',
        status: 'Cumple',
      },
      {
        reference: 'QC-03',
        equipment: 'QC',
        activity: 'Confiabilidad',
        indicator: '71,2 %',
        status: 'Crítico',
      },
      {
        reference: 'RTG-12',
        equipment: 'RTG',
        activity: 'Confiabilidad',
        indicator: '88,6 %',
        status: 'Atención',
      },
      {
        reference: 'RTG-07',
        equipment: 'RTG',
        activity: 'Confiabilidad',
        indicator: '94,3 %',
        status: 'Cumple',
      },
    ],
  },
};

@Injectable({ providedIn: 'root' })
export class DashboardDataService {
  getDashboard(id: DashboardId): DashboardConfig {
    return CONFIGS[id];
  }
}
