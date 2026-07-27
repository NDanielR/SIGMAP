import { ChangeDetectionStrategy, Component, input } from '@angular/core';
import { Metric } from '../../../core/models/dashboard.models';

@Component({
  selector: 'app-metric-card',
  templateUrl: './metric-card.html',
  styleUrl: './metric-card.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class MetricCard {
  readonly metric = input.required<Metric>();
}
