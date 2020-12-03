import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BaggageHandlerSharedModule } from 'app/shared/shared.module';

import { DocsComponent } from './docs.component';

import { docsRoute } from './docs.route';

@NgModule({
  imports: [BaggageHandlerSharedModule, RouterModule.forChild([docsRoute])],
  declarations: [DocsComponent],
})
export class DocsModule {}
