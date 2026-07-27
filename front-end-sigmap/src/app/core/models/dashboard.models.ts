export type DashboardId = 'summary' | 'assets' | 'workOrders' | 'failures' | 'indicators';
export type Accent = 'success' | 'warning' | 'danger';

export interface Metric {
  readonly code: string;
  readonly label: string;
  readonly value: string;
  readonly caption: string;
  readonly accent: Accent;
}

export interface ProgressItem {
  readonly label: string;
  readonly value: number;
  readonly displayValue?: string;
  readonly accent?: Accent;
}

export interface TableRow {
  readonly reference: string;
  readonly equipment: string;
  readonly activity: string;
  readonly indicator: string;
  readonly status: string;
}

export interface DashboardConfig {
  readonly title: string;
  readonly subtitle: string;
  readonly chartTitle: string;
  readonly tableTitle: string;
  readonly metrics: readonly Metric[];
  readonly chart: readonly ProgressItem[];
  readonly status: readonly ProgressItem[];
  readonly rows: readonly TableRow[];
}
