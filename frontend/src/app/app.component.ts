import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { RouterOutlet } from '@angular/router';

interface StatCard {
  label: string;
  value: string;
  delta: string;
  trend: 'up' | 'down';
}

interface DepartmentStat {
  name: string;
  headcount: number;
  payroll: string;
  share: string;
}

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, MatButtonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  readonly periods = ['Monthly', 'Quarterly', 'Yearly'];
  selectedPeriod = 'Monthly';

  readonly statCards: StatCard[] = [
    { label: 'Total employees', value: '10,000', delta: '+2.1%', trend: 'up' },
    { label: 'Annual payroll', value: '$42.6M', delta: '+4.8%', trend: 'up' },
    { label: 'Average salary', value: '$126K', delta: '+1.6%', trend: 'up' },
    { label: 'Salary variance', value: '12.4%', delta: '-0.7%', trend: 'down' }
  ];

  readonly departmentStats: DepartmentStat[] = [
    { name: 'Engineering', headcount: 2480, payroll: '$14.8M', share: '34.7%' },
    { name: 'Sales', headcount: 1690, payroll: '$9.3M', share: '21.9%' },
    { name: 'Finance', headcount: 840, payroll: '$7.5M', share: '17.6%' },
    { name: 'Operations', headcount: 1130, payroll: '$6.6M', share: '15.5%' }
  ];

  readonly salaryBands = [
    { label: '0-60K', employees: 1420, color: '#60a5fa' },
    { label: '60-100K', employees: 2680, color: '#34d399' },
    { label: '100-150K', employees: 3050, color: '#fbbf24' },
    { label: '150K+', employees: 2850, color: '#f472b6' }
  ];

  readonly watchlist = [
    'US engineering lead adjustments',
    'India cost-of-living review',
    'Germany manager retention band',
    'New hire compensation benchmark'
  ];

  readonly quickActions = [
    'Review approvals',
    'Audit changes',
    'Export dashboard',
    'Benchmark countries'
  ];

  setPeriod(period: string): void {
    this.selectedPeriod = period;
  }
}
