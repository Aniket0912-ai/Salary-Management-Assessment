import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component, OnInit, inject } from '@angular/core';
import { environment } from '../environments/environment';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';

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

interface DashboardSummary {
  totalEmployees: number;
  annualPayroll: number;
  averageSalary: number;
  salaryVariance: number;
  departmentBreakdown: Array<{ name: string; headcount: number; payroll: number; share: number }>;
  salaryBands: Array<{ label: string; employees: number }>;
}

interface EmployeeRecord {
  id: number;
  employeeCode: string;
  firstName: string;
  lastName: string;
  email: string;
  country: string;
  department: string;
  designation: string;
  joiningDate: string;
  currency: string;
}

interface EmployeePage {
  content: EmployeeRecord[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

interface EmployeeForm {
  employeeCode: string;
  firstName: string;
  lastName: string;
  email: string;
  country: string;
  department: string;
  designation: string;
  joiningDate: string;
  currency: string;
}

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, MatButtonModule, HttpClientModule, FormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
  private readonly http = inject(HttpClient);
  private summarySnapshot: DashboardSummary | null = null;

  readonly periods = ['Monthly', 'Quarterly', 'Yearly'];
  selectedPeriod = 'Monthly';
  selectedTab: 'dashboard' | 'employees' | 'add' | 'details' = 'dashboard';
  isLoading = true;
  statusMessage = 'Dashboard ready';

  statCards: StatCard[] = [];
  departmentStats: DepartmentStat[] = [];
  salaryBands: Array<{ label: string; employees: number; color: string }> = [];
  employees: EmployeeRecord[] = [];
  employeeDetails: EmployeeRecord | null = null;
  employeeIdInput = '';

  employeeForm: EmployeeForm = {
    employeeCode: '',
    firstName: '',
    lastName: '',
    email: '',
    country: 'USA',
    department: 'Engineering',
    designation: 'Senior Engineer',
    joiningDate: '2025-01-15',
    currency: 'USD'
  };

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

  ngOnInit(): void {
    this.loadDashboardSummary();
    this.loadEmployees();
  }

  setPeriod(period: string): void {
    this.selectedPeriod = period;
    this.applyDashboardSummary(this.summarySnapshot ?? this.buildFallbackSummary());
  }

  openTab(tab: 'dashboard' | 'employees' | 'add' | 'details'): void {
    this.selectedTab = tab;
    if (tab === 'employees' && this.employees.length === 0) {
      this.loadEmployees();
    }
  }

  private loadDashboardSummary(): void {
    this.http.get<DashboardSummary>(`${environment.apiUrl}/api/employees/dashboard-summary`).subscribe({
      next: (summary) => {
        this.summarySnapshot = summary;
        this.applyDashboardSummary(summary);
        this.isLoading = false;
      },
      error: () => {
        this.summarySnapshot = this.buildFallbackSummary();
        this.applyDashboardSummary(this.summarySnapshot);
        this.isLoading = false;
      }
    });
  }

  loadEmployees(): void {
    this.http.get<EmployeePage>(`${environment.apiUrl}/api/employees?size=20&page=0`).subscribe({
      next: (page) => {
        this.employees = page.content ?? [];
      },
      error: () => {
        this.statusMessage = 'Could not load employees from the API.';
      }
    });
  }

  fetchEmployeeDetails(): void {
    const id = Number(this.employeeIdInput);
    if (!id) {
      this.statusMessage = 'Enter a valid employee ID.';
      return;
    }

    this.http.get<EmployeeRecord>(`${environment.apiUrl}/api/employees/${id}`).subscribe({
      next: (employee) => {
        this.employeeDetails = employee;
        this.statusMessage = `Loaded employee ${employee.employeeCode}.`;
      },
      error: () => {
        this.employeeDetails = null;
        this.statusMessage = 'Employee not found.';
      }
    });
  }

  submitEmployee(): void {
    const payload = {
      ...this.employeeForm,
      employeeCode: this.employeeForm.employeeCode || `EMP-${Date.now()}`
    };

    this.http.post<EmployeeRecord>(`${environment.apiUrl}/api/employees`, payload).subscribe({
      next: (employee) => {
        this.statusMessage = `Employee ${employee.firstName} ${employee.lastName} added.`;
        this.employeeForm = {
          employeeCode: '',
          firstName: '',
          lastName: '',
          email: '',
          country: 'USA',
          department: 'Engineering',
          designation: 'Senior Engineer',
          joiningDate: '2025-01-15',
          currency: 'USD'
        };
        this.loadEmployees();
        this.loadDashboardSummary();
      },
      error: () => {
        this.statusMessage = 'Failed to add employee. Please check the form data.';
      }
    });
  }

  exportReport(): void {
    const rows = this.employees.length > 0 ? this.employees : this.buildFallbackEmployees();
    const header = ['ID', 'Employee Code', 'First Name', 'Last Name', 'Email', 'Country', 'Department', 'Designation', 'Joining Date', 'Currency'];
    const csv = [
      header.join(','),
      ...rows.map((employee) => [
        employee.id,
        employee.employeeCode,
        employee.firstName,
        employee.lastName,
        employee.email,
        employee.country,
        employee.department,
        employee.designation,
        employee.joiningDate,
        employee.currency
      ].map((value) => `"${String(value).replace(/"/g, '""')}"`).join(','))
    ].join('\n');

    const blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
    const url = window.URL.createObjectURL(blob);
    const anchor = document.createElement('a');
    anchor.href = url;
    anchor.download = `acme-employee-report-${this.selectedPeriod.toLowerCase()}.csv`;
    anchor.click();
    window.URL.revokeObjectURL(url);
    this.statusMessage = 'Employee report exported successfully.';
  }

  handleQuickAction(action: string): void {
    this.statusMessage = `${action} action triggered.`;
  }

  private applyDashboardSummary(summary: DashboardSummary): void {
    const periodMultiplier = this.selectedPeriod === 'Monthly' ? 1 : this.selectedPeriod === 'Quarterly' ? 1.12 : 1.28;

    this.statCards = [
      { label: 'Total employees', value: this.formatNumber(summary.totalEmployees), delta: '+2.1%', trend: 'up' },
      { label: 'Annual payroll', value: this.formatCurrency(summary.annualPayroll * periodMultiplier), delta: '+4.8%', trend: 'up' },
      { label: 'Average salary', value: this.formatCurrency(summary.averageSalary * periodMultiplier), delta: '+1.6%', trend: 'up' },
      { label: 'Salary variance', value: `${(summary.salaryVariance * (this.selectedPeriod === 'Yearly' ? 0.9 : 1)).toFixed(1)}%`, delta: '-0.7%', trend: 'down' }
    ];

    this.departmentStats = summary.departmentBreakdown.slice(0, 4).map((item) => ({
      name: item.name,
      headcount: item.headcount,
      payroll: this.formatCurrency(item.payroll * periodMultiplier),
      share: `${item.share.toFixed(1)}%`
    }));

    const bandColors = ['#60a5fa', '#34d399', '#fbbf24', '#f472b6'];
    this.salaryBands = summary.salaryBands.map((band, index) => ({
      ...band,
      employees: Math.max(1, Math.round(band.employees * (this.selectedPeriod === 'Monthly' ? 1 : this.selectedPeriod === 'Quarterly' ? 1.05 : 1.08))),
      color: bandColors[index % bandColors.length]
    }));
  }

  private buildFallbackSummary(): DashboardSummary {
    return {
      totalEmployees: 10000,
      annualPayroll: 42000000,
      averageSalary: 126000,
      salaryVariance: 12.4,
      departmentBreakdown: [
        { name: 'Engineering', headcount: 2480, payroll: 14800000, share: 34.7 },
        { name: 'Sales', headcount: 1690, payroll: 9300000, share: 21.9 },
        { name: 'Finance', headcount: 840, payroll: 7500000, share: 17.6 },
        { name: 'Operations', headcount: 1130, payroll: 6600000, share: 15.5 }
      ],
      salaryBands: [
        { label: '0-60K', employees: 1420 },
        { label: '60-100K', employees: 2680 },
        { label: '100-150K', employees: 3050 },
        { label: '150K+', employees: 2850 }
      ]
    };
  }

  private buildFallbackEmployees(): EmployeeRecord[] {
    return [
      { id: 1001, employeeCode: 'EMP-1001', firstName: 'Alice', lastName: 'Patel', email: 'alice.patel@acme.com', country: 'USA', department: 'Engineering', designation: 'Senior Engineer', joiningDate: '2022-03-12', currency: 'USD' },
      { id: 1002, employeeCode: 'EMP-1002', firstName: 'David', lastName: 'Nguyen', email: 'david.nguyen@acme.com', country: 'Singapore', department: 'Finance', designation: 'Manager', joiningDate: '2021-06-21', currency: 'SGD' }
    ];
  }

  private formatNumber(value: number): string {
    return new Intl.NumberFormat('en-US').format(Math.round(value));
  }

  private formatCurrency(value: number): string {
    if (value >= 1000000) {
      return `$${(value / 1000000).toFixed(1)}M`;
    }
    if (value >= 1000) {
      return `$${(value / 1000).toFixed(1)}K`;
    }
    return `$${value.toFixed(0)}`;
  }
}
