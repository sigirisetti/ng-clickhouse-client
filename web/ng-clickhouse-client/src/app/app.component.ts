import {Component, ViewChild, ElementRef, ViewEncapsulation, AfterViewInit} from '@angular/core';
import {VERSION} from '@angular/material';
import {NavItem} from './core/nav/nav-item';
import {NavService} from './core/nav/nav.service';
import {NotificationService} from './common/notification.service';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class AppComponent implements AfterViewInit {
  @ViewChild('appDrawer', { static: true }) appDrawer: ElementRef;
  version = VERSION;
  navItems: NavItem[] = [
    {
      displayName: 'Pricing',
      iconName: 'euro',
      route: '',
      children: [
        {
          displayName: 'TFX',
          iconName: 'star_rate',
          route: 'pricing/tfx'
        },
        {
          displayName: 'TFX Large',
          iconName: 'star_rate',
          route: 'pricing/tfx-large',
        },
      ]
    },
    {
      displayName: 'Samples',
      iconName: 'view_list',
      children: [
        {
          displayName: 'Stock Quotes',
          iconName: 'money',
          route: 'samples/ws/stocks'
        },
        {
          displayName: 'Line Chart',
          iconName: 'star_rate',
          route: 'samples/charts/line-chart'
        },
        {
          displayName: 'Expandable Table Rows',
          iconName: 'star_rate',
          route: 'samples/ng-examples/expandable-table-rows'
        }
      ]
    }
  ];

  constructor(
    private navService: NavService, 
    private snackBar: MatSnackBar, 
    private notificationService: NotificationService) {

      this.notificationService.notification$.subscribe(message => {
        this.snackBar.open(message, "Close", {
          duration: 3000,
          panelClass: ['blue-snackbar']
        });
      });
      this.notificationService.error$.subscribe(message => {
        this.snackBar.open(message, "Close", {
          duration: 3000,
          panelClass: ['red-snackbar']
        });
      });
  }

  ngAfterViewInit() {
    this.navService.appDrawer = this.appDrawer;
  }
}
