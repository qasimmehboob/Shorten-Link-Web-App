import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ShorterLink } from '../model/shorter-link';

@Injectable({
  providedIn: 'root'
})
export class ShortenLinkService {
  private observer = new BehaviorSubject<ShorterLink[]>([]);
  readonly subscriber$ = this.observer.asObservable();
  private links: ShorterLink[] = [];

  constructor(private httpClient: HttpClient) { }

  getLinks(){
    const requestUrl = `${environment.baseUrl}`;    
    this.httpClient.get<ShorterLink[]>(requestUrl)
    .subscribe((response) => {
        this.links = response;        
        this.observer.next(this.links);
      });
  }
  createShorterLink(link: ShorterLink) {
    // Making Request to server to add item into cart
    const requestUrl = `${environment.baseUrl}`;
    this.httpClient.post<ShorterLink>(requestUrl, link).subscribe({
      next: response => {  
          console.log(`Post Response: ${JSON.stringify(response)}`);
          this.getLinks();
      },
      error: error => {
        console.log(`Post ERROR Response: ${JSON.stringify(error.error)}`);
      }
    });

  }
  updateShorterLink(link: ShorterLink) {
    // Making Request to server to add item into cart
    const requestUrl = `${environment.baseUrl}/${link.id}`;
    this.httpClient.put<ShorterLink>(requestUrl, link).subscribe({
      next: response => {  
          console.log(`Put Response: ${JSON.stringify(response)}`);
          this.getLinks();
      },
      error: error => {
        console.log(`Put ERROR Response: ${JSON.stringify(error.error)}`);
      }
    });
  }
  deleteShorterLink(link: ShorterLink) {
    // Making Request to server to add item into cart
    const requestUrl = `${environment.baseUrl}/${link.id}`;
    this.httpClient.delete(requestUrl).subscribe({
      next: response => {  
          console.log(`Delete Response: ${JSON.stringify(response)}`);
          this.getLinks();
      },
      error: error => {
        console.log(`Delete ERROR Response: ${JSON.stringify(error.error)}`);
      }
    });

  }
}
