const fs = require('fs');
const path = require('path');

const backend = process.env.BACKEND_URL || process.env.RENDER_BACKEND_URL || 'http://localhost:8081';
const prodEnvPath = path.join(__dirname, 'src', 'environments', 'environment.prod.ts');

const content = `export const environment = {\n  production: true,\n  apiUrl: '${backend}'\n};\n`;

fs.writeFileSync(prodEnvPath, content, { encoding: 'utf8' });
console.log('Wrote', prodEnvPath, 'with backend URL:', backend);
