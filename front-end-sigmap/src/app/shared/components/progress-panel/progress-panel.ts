import { ChangeDetectionStrategy, Component, input } from '@angular/core';
import { ProgressItem } from '../../../core/models/dashboard.models';

@Component({
  selector: 'app-progress-panel',
  templateUrl: './progress-panel.html',
  styleUrl: './progress-panel.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ProgressPanel {
  readonly title = input.required<string>();
  readonly items = input.required<readonly ProgressItem[]>();
  readonly compact = input(false);
}
