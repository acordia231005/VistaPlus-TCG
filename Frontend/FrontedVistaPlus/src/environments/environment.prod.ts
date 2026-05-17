export const environment = {
  production: true,
  apiUrl: (window as any)['env']?.apiUrl 
  || 'http://192.168.0.112:8080'
};