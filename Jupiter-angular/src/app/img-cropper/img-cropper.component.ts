import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { ImageCroppedEvent, ImageCropperComponent } from 'ngx-image-cropper';

@Component({
  selector: 'app-img-cropper',
  templateUrl: './img-cropper.component.html',
  styleUrls: ['./img-cropper.component.css']
})
export class ImgCropperComponent implements OnInit {

//   @Input('imageChangedEvent') event:any = null;
  @Input('imageBase64') base64:any = null;
  @Output('resultImage') resultImage: any = new EventEmitter();
  @ViewChild('imgcrop') imageCropper!: ImageCropperComponent;

  imageChangedEvent: any = '';
  croppedImage: any = '';
  imageBase64:any = null;
  constructor() { }

  ngOnChanges(changes: SimpleChanges): void {
    //  this.imageChangedEvent = this.event;
     this.imageBase64 = this.base64;
  }

  ngOnInit(): void {
      this.imageBase64 = this.base64
  }

  imageCropped(event: ImageCroppedEvent) {
      console.log("imageCropped")
      this.resultImage.emit(event.base64)
  }
  imageLoaded() {
      console.log("imageLoaded")
      // show cropper
  }
  cropperReady() {
      console.log("cropperReady")
      // cropper ready
  }
  loadImageFailed() {
      console.log("loadImageFailed")
      // show message
  }
  finishedCropImage(){
      this.imageCropper.crop();
  }

}
