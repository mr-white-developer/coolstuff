import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { ImageCroppedEvent, ImageCropperComponent } from 'ngx-image-cropper';

@Component({
  selector: 'app-img-cropper',
  templateUrl: './img-cropper.component.html',
  styleUrls: ['./img-cropper.component.css']
})
export class ImgCropperComponent implements OnInit {

  @Input('imageChangedEvent') event:any = null;
  @Output('resultImage') resultImage: any = new EventEmitter();
  @ViewChild('imgcrop') imageCropper!: ImageCropperComponent;

  imageChangedEvent: any = '';
  croppedImage: any = '';
 
  constructor() { }

  ngOnChanges(changes: SimpleChanges): void {
     this.imageChangedEvent = this.event
  }

  ngOnInit(): void {
      this.imageChangedEvent = this.event
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
