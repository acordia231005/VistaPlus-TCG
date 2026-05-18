export const environment = {
  production: true,
  apiUrl: (typeof window !== 'undefined' ? ((window as any)['env']?.apiUrl || (window as any)['env']?.API_URL) : null)
    || 'http://192.168.9.150:8080'
};