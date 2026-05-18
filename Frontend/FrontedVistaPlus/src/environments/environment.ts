export const environment = {
  production: false,
  apiUrl: (typeof window !== 'undefined' ? ((window as any)['env']?.apiUrl || (window as any)['env']?.API_URL) : null)
    || 'http://localhost:8080'
};