import { Component, ViewChildren, OnInit } from '@angular/core';
import { MassQuoteService } from '../mass-quotes-service'
import { MassQuote } from '../mass-quotes';
import { animate, state, style, transition, trigger } from '@angular/animations';
import * as globals from '../../globals'

import { Currency } from '../../common/static-data/currency';
import { TfxStaticDataService } from '../../common/static-data/tfx-static-data.service'
import { TfxPriceSeriesComponent } from '../charts/tfx-price-series/tfx-price-series.component'
import { NotificationService } from '../../common/notification.service'

@Component({
  selector: 'app-tfx',
  templateUrl: './tfx.component.html',
  styleUrls: ['./tfx.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
export class TfxComponent implements OnInit {

  @ViewChildren(TfxPriceSeriesComponent) charts;
  private currentChart: TfxPriceSeriesComponent| null;

  private currencies: Currency[];
  displayedColumns = ["symbol", "spotDate", "marketBid", "marketAsk", "bid", "ask", "spread"];
  massQuotes: MassQuote[] = new Array();
  tfxPrices: MassQuote[];
  expandedElement: MassQuote | null;

  constructor(private tfxStaticDataService: TfxStaticDataService, private massQuoteService: MassQuoteService, private notificationService: NotificationService) { }

  ngOnInit() {
    this.tfxStaticDataService.getTfxCurrencies().subscribe(
      (res: Currency[]) => {
        this.currencies = res;
        //console.log(res)
        for (let cp of res) {
          let q = new MassQuote();
          q.symbol = cp.symbol;
          this.massQuotes.push(q);
        }
      },
      error => {
        this.notificationService.error$.next("TFX Currencies rest api error : " + error.message);
        console.log("TFX Currencies rest api error : " + error)
      }
    );


    this.massQuoteService.messages.subscribe(msg => {
      for (let i = 0; i < this.massQuotes.length; i++) {
        let q = this.massQuotes[i];
        if (q.symbol === msg.symbol) {
          this.massQuotes[i] = msg;
          break;
        }
      }
      this.tfxPrices = Object.assign([], this.massQuotes);
    });
  }

  onRowClicked(row) {
    this.expandedElement = this.expandedElement === row ? null : row;
    let selected = null;
    for (let chart of this.charts) {
      if(chart.ccyPair === row.symbol) {
        chart.render();
        selected = chart;
      }
    }
    if(this.currentChart != null) {
      //console.log(this.currentChart)
      this.currentChart.stopRendering();
    }
    this.currentChart = selected;
  }

  getSuggestedMin(sym) {
    for(let cp of this.currencies) {
      if(cp.symbol === sym) {
        return cp.chartLowerBound;
      }
    }
  }

  getSuggestedMax(sym) {
    for(let cp of this.currencies) {
      if(cp.symbol === sym) {
        return cp.chartUpperBound;
      }
    }
  }
}
