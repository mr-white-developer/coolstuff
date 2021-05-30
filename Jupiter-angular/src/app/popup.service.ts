import { ApplicationRef, ComponentFactoryResolver, EmbeddedViewRef, Injectable, Injector } from '@angular/core';
import { LoadingComponent } from './popup/loading/loading.component';
import { PopupComponent } from './popup/popup.component';
declare var $: any;
@Injectable({
  providedIn: 'root'
})
export class PopupService {
  loadingEl: any;
  constructor(
    private componentFactoryResolver: ComponentFactoryResolver,
    private appRef: ApplicationRef,
    private injector: Injector,


  ) { }
  showAlert(message: string) {
    // Create element
    const popup = document.createElement('popup-component');
    // Create the component and wire it up with the element
    const factory = this.componentFactoryResolver.resolveComponentFactory(PopupComponent);
    const popupComponentRef = factory.create(this.injector, [], popup);
    // Attach to the view so that the change detector knows to run
    this.appRef.attachView(popupComponentRef.hostView);
    // Listen to the close event
    popupComponentRef.instance.closed.subscribe(() => {
      $(popup).remove();
      this.appRef.detachView(popupComponentRef.hostView);
    });
    // Set the message
    popupComponentRef.instance.message = message
    // Add to the DOM
    let notifierContainerRef = document.body.getElementsByClassName("notifier");
    $(notifierContainerRef).append(popup);
    setTimeout(() => {
      $(popup).remove();
      this.appRef.detachView(popupComponentRef.hostView);
    }, 3000);
  }
  showLoading(message: string) {
    return new Promise((resolve, reject) => {
      const popup = document.createElement('loading-component');
      const factory = this.componentFactoryResolver.resolveComponentFactory(LoadingComponent);
      this.loadingEl = factory.create(this.injector, [], popup);
      this.appRef.attachView(this.loadingEl.hostView);
      this.loadingEl.instance.message = message;
      let appRoot = document.body.getElementsByTagName('app-root');
      resolve({
        present: () => {
          document.body.appendChild(popup);
          // (appRoot[0] as HTMLElement).style.pointerEvents = "none";
          // (appRoot[0] as HTMLElement).style.opacity = "0.6";
        },
        dismiss: () => {
          setTimeout(() => {
            document.body.removeChild(popup);
            // (appRoot[0] as HTMLElement).style.pointerEvents ="auto";
            // (appRoot[0] as HTMLElement).style.opacity = '1';
          }, 500)

        }
      })


    })
  }
}
