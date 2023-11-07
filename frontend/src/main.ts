import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { importProvidersFrom } from '@angular/core';
import { RouterModule } from '@angular/router';
import { routes } from './app/app.routes';
import { StoreModule } from '@ngrx/store';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import { reviewListReducer } from './app/components/review-list/review-list.reducers';
import { EffectsModule } from '@ngrx/effects';
import { ReviewListEffects } from './app/components/review-list/review.effects';
import { HttpClientModule } from '@angular/common/http';


bootstrapApplication(AppComponent, {
  providers: [importProvidersFrom(
    HttpClientModule,
    RouterModule.forRoot(routes),
    EffectsModule.forRoot([ReviewListEffects]),
    StoreModule.forRoot({ reviews: reviewListReducer }),
    StoreDevtoolsModule.instrument({})
  )],
}).catch((err) => console.error(err));
