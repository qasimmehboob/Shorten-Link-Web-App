import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ShorterLink } from './model/shorter-link';
import { ShortenLinkService } from './services/shorten-link.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy{
  private linksSub!: Subscription;
  expiryDate!: Date;
  minDate!: Date;
  inputLink: string = "";
  selectedLink!: ShorterLink | null; 

  baseUrl: String = environment.baseUrl;

  now!: Date;
  
  links: ShorterLink[] = [];
  constructor(private shortenLinkService: ShortenLinkService,
    private cdr: ChangeDetectorRef) { }

  ngOnInit() {
      //this.productService.getProductsSmall().then(data => this.products = data);
      this.linksSub = this.shortenLinkService.subscriber$
                    .subscribe((response) => {
                      this.links = response
                      this.now = new Date();
                      this.now.setHours(0,0,0,0);
                    });
      let today = new Date();
      let day = today.getDay;
      let month = today.getMonth();
      let year = today.getFullYear();
      let prevMonth = (month === 0) ? 11 : month -1;
      let prevYear = (prevMonth === 11) ? year - 1 : year;
      let nextMonth = (month === 11) ? 0 : month + 1;
      let nextYear = (nextMonth === 0) ? year + 1 : year;
      this.minDate = new Date();
      //this.minDate.set(prevMonth);
      //this.minDate.setMonth(prevMonth);
      //this.minDate.setFullYear(year);
      this.expiryDate = new Date()
      this.expiryDate.setDate(today.getDate() + 7);

  }
  createShorterLink() {
    if(this.expiryDate) {
      console.log(`Expiry Date: ${this.expiryDate.getDate()}-${this.expiryDate.getMonth() + 1}-${this.expiryDate.getFullYear()}`); 
      this.expiryDate.setHours(0,0,0,0);
    }
    const linkDetail = new ShorterLink(this.inputLink, this.expiryDate);
    this.shortenLinkService.createShorterLink(linkDetail);
    this.resetSelection();
  }
  updateShorterLink() {
    if(this.selectedLink) {
      this.expiryDate.setHours(0,0,0,0);
      this.selectedLink.expiryDate = this.expiryDate;
      this.shortenLinkService.updateShorterLink(this.selectedLink);
      this.resetSelection();
    }
  }
  deleteShorterLink() {
    if(this.selectedLink) {
      this.shortenLinkService.deleteShorterLink(this.selectedLink);
      this.resetSelection();
    }
  }
  getEventValue($event:any) :string {
    return $event.target.value;
  } 
  getRedirectUrl(hash: String) {
    return environment.baseUrl + "/" + hash;
  }
  onRowSelect(event: ShorterLink) {
    if(this.selectedLink){
      console.log("Selected Event Call")
      console.log(this.selectedLink.originalLink + " --- " + this.selectedLink.expiryDate);
      this.inputLink = this.selectedLink.originalLink;
      if(this.selectedLink.expiryDate) {
        this.expiryDate = new Date(this.selectedLink.expiryDate);
      }
    }
  }
  resetSelection() {
    this.inputLink = "";
    this.expiryDate = new Date()
    this.expiryDate.setDate((new Date()).getDate() + 7);
    this.selectedLink = null;
  }
  onRowUnselect(event: ShorterLink) {
    console.log("UnSelected Event Call")
    this.resetSelection();
  }
  ngAfterViewInit(): void {
    this.shortenLinkService.getLinks();
    this.cdr.detectChanges();
  }
  ngOnDestroy(): void {
    this.linksSub.unsubscribe();
  }
}

