const fs = require('fs');
const path = require('path');

const countries = [
  { name: 'USA', currency: 'USD', department: ['Engineering', 'Finance', 'People Ops', 'Sales'] },
  { name: 'India', currency: 'INR', department: ['Engineering', 'Operations', 'Finance', 'Marketing'] },
  { name: 'Germany', currency: 'EUR', department: ['Engineering', 'Legal', 'Support', 'Sales'] },
  { name: 'United Kingdom', currency: 'GBP', department: ['Engineering', 'Finance', 'Operations', 'HR'] },
  { name: 'Canada', currency: 'CAD', department: ['Engineering', 'Design', 'Sales', 'People Ops'] }
];

const names = ['Ava', 'Noah', 'Mia', 'Ethan', 'Olivia', 'Liam', 'Sophia', 'Mason', 'Charlotte', 'Lucas'];
const lastNames = ['Patel', 'Nguyen', 'Smith', 'Muller', 'Brown', 'Lee', 'Singh', 'Kim', 'Rossi', 'Davis'];
const designations = ['Senior Engineer', 'Manager', 'Analyst', 'Director', 'Associate'];

function createEmployee(index) {
  const country = countries[index % countries.length];
  const department = country.department[index % country.department.length];
  const firstName = names[index % names.length];
  const lastName = lastNames[(index * 3) % lastNames.length];
  const baseSalary = 50000 + ((index * 791) % 140000);

  return {
    employeeCode: `ACME-${String(index + 1).padStart(5, '0')}`,
    firstName,
    lastName,
    email: `${firstName.toLowerCase()}.${lastName.toLowerCase()}${index}@acme.com`,
    country: country.name,
    department,
    designation: designations[index % designations.length],
    joiningDate: new Date(2016 + (index % 8), (index % 12), (index % 28) + 1).toISOString().slice(0, 10),
    currency: country.currency,
    annualSalary: baseSalary,
    reason: 'Initial seed record'
  };
}

const employeeCount = 10000;
const rows = Array.from({ length: employeeCount }, (_, index) => createEmployee(index));
const outDir = path.join(__dirname, '..', 'output');
fs.mkdirSync(outDir, { recursive: true });
fs.writeFileSync(path.join(outDir, 'employees.json'), JSON.stringify(rows, null, 2));
console.log(`Wrote ${rows.length} seed records to ${path.join(outDir, 'employees.json')}`);
