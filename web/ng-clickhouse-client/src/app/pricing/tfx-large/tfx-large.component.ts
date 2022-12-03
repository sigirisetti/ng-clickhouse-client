import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-tfx-large',
  templateUrl: './tfx-large.component.html',
  styleUrls: ['./tfx-large.component.css']
})
export class TfxLargeComponent implements OnInit {

  displayedColumns = ["symbol", "spotDate", "marketBid", "marketAsk", "bid", "ask", "spread"];

  constructor() { }

  ngOnInit() {
  }

}
