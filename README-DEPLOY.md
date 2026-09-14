Deployment instructions (Render automatic deploy)

1) Commit and push everything to GitHub (main branch):

```powershell
cd "C:\Users\asusl\OneDrive\Desktop\AI_App\acme-salary-management"
git init
git remote add origin https://github.com/Aniket0912-ai/Salary-Management-Assessment.git
git add .
git commit -m "chore: prepare Render deploy (Dockerfile, render.yaml, frontend env injection)"
git branch -M main
git push -u origin main
```

2) In Render dashboard -> New -> Pull Request/Repository -> Import Repository. Render will detect `render.yaml` and create two services:
   - `acme-backend` (Docker service) — build uses `backend/Dockerfile`
   - `acme-frontend` (Static site) — build command runs `npm ci && npm run build:prod`

3) Configure service environment variables on Render:
   - For `acme-backend`: set `FRONTEND_ORIGINS` to your frontend URL (e.g., `https://your-frontend.onrender.com`) or `*` for demo.
   - For `acme-frontend`: set `BACKEND_URL` to the backend URL Render provides (e.g., `https://acme-backend.onrender.com`). The build step will inject this into `environment.prod.ts`.

4) Trigger a deploy. Once both services are live, open the frontend URL and test the Add Employee flow.

Notes/Tips:
- If Render's automatic detection doesn't pick up the `render.yaml`, create the two services manually and point them to the same repo, setting the root for the frontend service to the `frontend` folder.
- Alternatively, you can host the frontend on Vercel: set the project root to `frontend`, build command `npm run build:prod`, and set the `BACKEND_URL` env var in Vercel.
