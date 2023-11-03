import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { importProvidersFrom } from '@angular/core';
import { RouterModule } from '@angular/router';
import { routes } from './app/app.routes';
import { StoreModule } from '@ngrx/store';


bootstrapApplication(AppComponent, {
  providers: [importProvidersFrom(
    RouterModule.forRoot(routes),
    StoreModule.forRoot({ review: ReviewReducer })
  )],
}).catch((err) => console.error(err));
