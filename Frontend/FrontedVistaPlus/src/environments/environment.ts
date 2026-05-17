export const environment = {
  production: false,
  apiUrl: (typeof window !== 'undefined' ? (window as any)['env']?.apiUrl : null)
    || 'http://192.168.0.112:8080'
};