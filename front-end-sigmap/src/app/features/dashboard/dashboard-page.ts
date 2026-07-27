import { ChangeDetectionStrategy, Component, computed, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DashboardId } from '../../core/models/dashboard.models';
import { DashboardDataService } from '../../core/services/dashboard-data.service';
import { DataTable } from '../../shared/components/data-table/data-table';
import { MetricCard } from '../../shared/components/metric-card/metric-card';
import { ProgressPanel } from '../../shared/components/progress-panel/progress-panel';

@Component({
  selector: 'app-dashboard-page',
  imports: [MetricCard, ProgressPanel, DataTable],
  templateUrl: './dashboard-page.html',
  styleUrl: './dashboard-page.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DashboardPage {
  private readonly route = inject(ActivatedRoute);
  private readonly dashboardData = inject(DashboardDataService);
  protected readonly dashboard = computed(() => {
    const id = this.route.snapshot.data['dashboardId'] as DashboardId;
    return this.dashboardData.getDashboard(id);
  });
}
