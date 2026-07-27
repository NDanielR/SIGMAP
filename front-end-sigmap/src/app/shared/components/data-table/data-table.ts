import { ChangeDetectionStrategy, Component, input } from '@angular/core';
import { TableRow } from '../../../core/models/dashboard.models';

@Component({
  selector: 'app-data-table',
  templateUrl: './data-table.html',
  styleUrl: './data-table.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DataTable {
  readonly title = input.required<string>();
  readonly rows = input.required<readonly TableRow[]>();
}
