import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { GlobalNavigationComponent } from './components/global-navigation/global-navigation.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  standalone: true,
  imports: [RouterModule, GlobalNavigationComponent]
})
export class AppComponent {
  title = 'liquor-notes';
}
