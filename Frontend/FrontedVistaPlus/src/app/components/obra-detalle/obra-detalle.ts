import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ObrasService, Obra } from '../../services/obras.service';

@Component({
  selector: 'app-obra-detalle',
  imports: [CommonModule, RouterLink],
  templateUrl: './obra-detalle.html',
  styleUrl: './obra-detalle.css',
})
export class ObraDetalle implements OnInit {
  obra: Obra | undefined;

  constructor(
    private route: ActivatedRoute,
    private obrasService: ObrasService
  ) {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const id = Number(params.get('id'));
      if (id) {
        this.obra = this.obrasService.getObraById(id);
      }
    });
  }
}
