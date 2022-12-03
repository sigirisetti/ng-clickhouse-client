import { Component, Input, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { ChartDataSets, ChartOptions } from 'chart.js';
import { Color, BaseChartDirective, Label } from 'ng2-charts';
import * as pluginAnnotations from 'chartjs-plugin-annotation';
import { MassQuoteService } from '../../mass-quotes-service'
import { Subscription, Subscriber } from 'rxjs';
import { TfxStaticDataService } from '../../../common/static-data/tfx-static-data.service'
import { Currency } from '../../../common/static-data/currency';
import * as globals from '../../../globals'

@Component({
  selector: 'app-tfx-price-series',
  templateUrl: './tfx-price-series.component.html',
  styleUrls: ['./tfx-price-series.component.css']
})
export class TfxPriceSeriesComponent implements OnInit, OnDestroy {

  @ViewChild(BaseChartDirective, { static: true }) chart: BaseChartDirective;

  @Input()
  public ccyPair = "";

  private priceTickSub: Subscription;

  public lineChartLegend = true;
  public lineChartType = 'line';
  public lineChartPlugins = [pluginAnnotations];

  public lineChartData: ChartDataSets[] = [
    { data: [], label: 'Mkt Bid' },
    { data: [], label: 'Mkt Ask' },
    { data: [], label: 'Bid' },
    { data: [], label: 'Ask' },
    //{ data: [], label: 'Spreads' }
  ];

  public lineChartLabels: Label[] = [];
  public lineChartLabelsInternal: Label[] = [];

  constructor(private tfxStaticDataService: TfxStaticDataService, private massQuoteService: MassQuoteService) {
  }

  ngOnInit() {
    //console.log("tfx price series component initialized : " + this.createTime)
  }

  ngOnDestroy(): void {
    //console.log("tfx price series component destroyed!")
  }

  public lineChartOptions: (ChartOptions & { annotation: any }) = {
    responsive: true,
    scales: {
      // We use this empty structure as a placeholder for dynamic theming.
      xAxes: [{
        display: true,
        ticks: {
          fontColor: 'black',
        }
      }],
      yAxes: [
        {
          id: 'y-axis',
          position: 'left',
          ticks: {
            fontColor: 'black',
            beginAtZero: false
          }
        }
      ]
    },
    annotation: {
      annotations: [
        {
          type: 'line',
          mode: 'vertical',
          scaleID: 'x-axis-0',
          value: 'March',
          borderColor: 'orange',
          borderWidth: 2,
          label: {
            enabled: true,
            fontColor: 'orange',
            content: 'LineAnno'
          }
        },
      ],
    },
    elements: {
      line: {
        tension: 0,
        fill: false
      }
    }
  };


  // events
  public chartClicked({ event, active }: { event: MouseEvent, active: {}[] }): void {
    //console.log(event, active);
  }

  public chartHovered({ event, active }: { event: MouseEvent, active: {}[] }): void {
    //console.log(event, active);
  }

  public render() {
    this.getChartData(this.ccyPair);
    this.priceTickSub = this.subscribeToPriceTicks(this.ccyPair);
  }

  public stopRendering() {
    this.priceTickSub.unsubscribe();
  }

  public pushOne(tick) {
    this.lineChartData.forEach((x, i) => {
      if (x.data.length == globals.MAX_SERIES_LENGTH) {
        x.data.slice(1);
      }
      x.data.push(tick[i]);
    })
    if (this.lineChartLabelsInternal.length < globals.MAX_SERIES_LENGTH) {
      this.lineChartLabelsInternal.push(`T-${this.lineChartLabelsInternal.length}`);
    }
    this.lineChartLabels = this.lineChartLabelsInternal.slice().reverse();
    this.chart.update();
  }

  subscribeToPriceTicks(ccyPair) {
    const subscriber = Subscriber.create(
      (tick) => this.pushOne(tick),
      (error) => { console.log("Failed to subscribe for " + ccyPair + " price series") }
    );
    console.log("Subscribing price ticks for " + ccyPair)
    return this.massQuoteService.subscribeToPriceTicks(ccyPair, subscriber);
  }

  getChartData(ccyPair) {
    this.lineChartData = this.massQuoteService.getChartData(ccyPair);
    let len = this.lineChartData[0].data.length;
    if (this.lineChartLabelsInternal.length < globals.MAX_SERIES_LENGTH) {
      this.lineChartLabelsInternal = [];
      for (let i = 0; i < len; i++) {
        this.lineChartLabelsInternal.push(`T-${i}`);
      }
      this.lineChartLabels = this.lineChartLabelsInternal.slice().reverse();
    }
    //console.log("Got initial chart data for " + ccyPair + " with length " + this.lineChartData[0].data.length)
  }

  public lineChartColors: Color[] = [
    { // blue
      backgroundColor: 'rgba(0,0,255,0.1)',
      borderColor: 'rgba(0,0,255,1)',
      pointBackgroundColor: 'rgba(0,0,255,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(0,0,255,1)'
    },
    { // blue
      backgroundColor: 'rgba(0,0,255,0.1)',
      borderColor: 'rgba(0,0,255,1)',
      pointBackgroundColor: 'rgba(0,0,255,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(0,0,255,1)'
    },
    { // red
      backgroundColor: 'rgba(255,0,0,0.1)',
      borderColor: 'red',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    },
    { // red
      backgroundColor: 'rgba(255,0,0,0.1)',
      borderColor: 'red',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    },
    { // green
      backgroundColor: 'rgba(0,255,0,0.1)',
      borderColor: 'rgba(0,255,0,1)',
      pointBackgroundColor: 'rgba(0,255,0,0.3)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(0,255,0,0.3)'
    },
  ];
}
