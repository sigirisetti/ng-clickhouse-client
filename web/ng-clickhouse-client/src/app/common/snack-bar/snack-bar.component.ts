import { Component, OnInit } from '@angular/core';
import { MatLegacySnackBar as MatSnackBar } from '@angular/material/legacy-snack-bar';

@Component({
  selector: 'app-snack-bar',
  templateUrl: './snack-bar.component.html',
  styleUrls: ['./snack-bar.component.css']
})
export class SnackBarComponent implements OnInit {

  ngOnInit() {
  }

  durationInSeconds = 5;

  constructor(private _snackBar: MatSnackBar) { }

  openSnackBar(c) {
    this._snackBar.openFromComponent(c, {
      duration: this.durationInSeconds * 1000,
    });
  }
}
