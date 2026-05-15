import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModalService } from '../../services/modal.service';

@Component({
  selector: 'app-modal',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './modal.html',
  styleUrl: './modal.css',
})
export class ModalComponent {
  modalService = inject(ModalService);

  onConfirm() {
    this.modalService.close(true);
  }

  onCancel() {
    this.modalService.close(false);
  }

  getIcon() {
    const type = this.modalService.options()?.type || 'info';
    switch (type) {
      case 'danger': return '⚠️';
      case 'warning': return '🔔';
      case 'success': return '✅';
      default: return 'ℹ️';
    }
  }
}
