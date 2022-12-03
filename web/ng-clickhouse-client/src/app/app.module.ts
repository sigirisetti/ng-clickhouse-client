import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from './material.module';
import { LayoutModule } from '@angular/cdk/layout';
import { MenuListItemComponent } from './core/nav/menu-list-item/menu-list-item.component';
import { NavService } from './core/nav/nav.service';
import { TopNavComponent } from './core/nav/top-nav/top-nav.component';
import { StocksComponent } from './samples/ws/stocks/stocks.component';
import { HttpClientModule } from '@angular/common/http';

import { ChartsModule } from 'ng2-charts';

import { PageUnderConstructionComponent } from './common/page-under-construction/page-under-construction.component';
import { TfxComponent } from './pricing/tfx/tfx.component';
import { TfxLargeComponent } from './pricing/tfx-large/tfx-large.component';
import { LineChartComponent } from './samples/charts/line-chart/line-chart.component';
import { ExpandableTableComponent } from './samples/ng-examples/expandable-table/expandable-table.component';
import { TfxPriceSeriesComponent } from './pricing/charts/tfx-price-series/tfx-price-series.component';
import { SnackBarComponent } from './common/snack-bar/snack-bar.component';

@NgModule({
  declarations: [
    AppComponent,
    PageUnderConstructionComponent,
    MenuListItemComponent,
    TopNavComponent,
    StocksComponent,
    PageUnderConstructionComponent,
    TfxComponent,
    TfxLargeComponent,
    LineChartComponent,
    ExpandableTableComponent,
    TfxPriceSeriesComponent,
    SnackBarComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    MaterialModule,
    LayoutModule,
    AppRoutingModule,
    HttpClientModule,
    ChartsModule
  ],
  providers: [NavService],
  bootstrap: [AppComponent]
})
export class AppModule {
}