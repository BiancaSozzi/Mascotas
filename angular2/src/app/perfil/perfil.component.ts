import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProvinciaService, Provincia } from '../provincia/provincia.service';
import { PerfilService, Perfil } from './perfil.service';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { Router } from '@angular/router';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';


@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css']
})
export class PerfilComponent implements OnInit {
  form: FormGroup;
  errorMessage: string;
  formSubmitted: boolean;

  provincias: Provincia[];

  base64Image : string;

  safeImage : SafeResourceUrl;

  constructor(fb: FormBuilder,
    private provinciaService: ProvinciaService,
    private perfilService: PerfilService,
    private route: ActivatedRoute,
    private router: Router,
    private domSanitizer: DomSanitizer) {
    this.form = fb.group({
      'id': [null, null],
      'provincia': [null, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
      'email': [null, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
      'nombre': [null, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
      'direccion': [null, Validators.required],
      'telefono': [null, Validators.minLength(2)],
      'imagen' : [null]
     })
    this.form.patchValue({ id: null, nombre: '', email: '', provincia: '', direccion: '', telefono: '', imagen:'' });
  }

  ngOnInit() {
    this.provinciaService.getProvincias()
      .then(provincias => this.provincias = provincias)
      .catch(error => this.errorMessage = <any>error);

    this.perfilService.buscarPerfil()
      .then(perfil => this.sanitize(perfil) )
      .catch(error => this.errorMessage = <any>error);
  }

  sanitize(perfil: Perfil){
    this.form.patchValue(perfil);
    this.safeImage = this.domSanitizer.bypassSecurityTrustUrl(this.form.value.imagen);
  }
  
  submitForm() {
    if(this.base64Image==null && this.form.value.imagen != null){
    	this.form.value.imagen = this.form.value.imagen
    }else{
    	this.form.value.imagen = this.base64Image;
    }
    this.cleanRestValidations();
    if (this.form.valid) {
      this.perfilService.guardarPerfil(this.form.value)
        .then(usuario => this.router.navigate(['/']))
        .catch(error => this.errorMessage = <any>error);
    } else {
      this.formSubmitted = true;
    }
  }

 changeListener($event) : void {
  this.readThis($event.target);
}

readThis(inputValue: any): void {
  var file:File = inputValue.files[0];
  var myReader:FileReader = new FileReader();

  myReader.onloadend = (e) => {
    this.base64Image = myReader.result;
  }
  myReader.readAsDataURL(file);
}


  cleanRestValidations() {
    //    this.form.controls['nombre'].setValidity( "rest", true );
    //    $scope.form.fechaNacimiento.$setValidity( "rest", true );
    //    $scope.form.descripcion.$setValidity( "rest", true );
  }

  procesarValidacionesRest(data) {
    /*   if ( data.message ) {
           for ( var i in data.message ) {
               var error = data.message[i];
               if ( $scope.form[error.path] ) {
                   $scope.form[error.path].$setValidity( "rest", false );
                   $scope.form[error.path].$error.restMessage = error.message;
               }
           }
       } else {
           toastr.error( "Error al grabar el perfil.", data.message );
       }*/
  }
}

