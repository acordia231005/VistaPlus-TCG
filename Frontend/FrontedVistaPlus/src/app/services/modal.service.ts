import { Injectable, signal } from '@angular/core';

export interface ModalOptions {
  title: string;
  message: string;
  confirmText?: string;
  cancelText?: string;
  type?: 'info' | 'warning' | 'danger' | 'success';
}

@Injectable({
  providedIn: 'root'
})
export class ModalService {
  isOpen = signal(false);
  options = signal<ModalOptions | null>(null);
  private resolveCallback: ((value: boolean) => void) | null = null;

  confirm(options: ModalOptions): Promise<boolean> {
    this.options.set(options);
    this.isOpen.set(true);
    
    return new Promise((resolve) => {
      this.resolveCallback = resolve;
    });
  }

  close(result: boolean) {
    this.isOpen.set(false);
    if (this.resolveCallback) {
      this.resolveCallback(result);
      this.resolveCallback = null;
    }
  }
}
