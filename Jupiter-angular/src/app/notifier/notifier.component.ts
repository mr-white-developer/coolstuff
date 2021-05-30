import { ApplicationRef, Component, ComponentFactoryResolver, Injector, OnInit } from '@angular/core';
import { PopupComponent } from '../popup/popup.component';
declare var $ :any;
@Component({
  selector: 'app-notifier',
  templateUrl: './notifier.component.html',
  styleUrls: ['./notifier.component.css']
})
export class NotifierComponent implements OnInit {

  constructor(private componentFactoryResolver: ComponentFactoryResolver,
    private appRef: ApplicationRef,
    private injector: Injector) { }

  ngOnInit(): void {
  }
  appendChild(){
    const popup = document.createElement('popup-component');
    // Create the component and wire it up with the element
    const factory = this.componentFactoryResolver.resolveComponentFactory(PopupComponent);
    const popupComponentRef = factory.create(this.injector, [], popup);
    // Attach to the view so that the change detector knows to run
    this.appRef.attachView(popupComponentRef.hostView);
    popupComponentRef.instance.message = "";
    // Add to the DOM
    $( ".alertify-notifier" ).append(popup);
    
    setTimeout(() => {
      document.body.removeChild(popup);
      this.appRef.detachView(popupComponentRef.hostView);
    }, 3000);
  }

}
