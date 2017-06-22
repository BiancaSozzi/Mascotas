import {Component, OnInit} from '@angular/core';
import{ EstadoService } from './estados.service';
import { Estado } from './estados.service';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { Router } from '@angular/router';
import { Usuario, UsuarioService } from '../usuario/usuario.service';
import { Location } from '@angular/common';
import { PerfilService, Perfil } from '../perfil/perfil.service';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
	selector: 'detalle-Perfil',
	templateUrl:'./estados.component.html',
	styleUrls: ['./estilosEstado.component.css'],
})

export class EstadosComponent {
	

	default = "Escribe tu estado";
	estado: Estado;
	errorMessage: string;
	estados:Estado[];
	usuario: Usuario;
	perfil: Perfil;
	safeImage: SafeResourceUrl;
	


	constructor( private estadoService: EstadoService, private perfilService: PerfilService,
		private router:Router, private usuarioService: UsuarioService, private location: Location,
		private domSanitizer: DomSanitizer){
		this.estado = {id: null, detalle: '', usuario:''}
	}

	ngOnInit(){

		this.usuarioService.getPrincipal()
		.then(usuario => this.usuario = usuario)
		.catch(error => this.errorMessage = <any>error);

		this.estadoService.buscarEstados()
		.then(estados => this.estados = estados)
		.catch(error => this.errorMessage = <any>error);

		this.perfilService.buscarPerfil()
		.then(perfil => this.asignarPerfil(perfil))
		.catch(error=> this.errorMessage = <any>error);

	}
		
	asignarPerfil(perfil: Perfil){
		this.perfil = perfil;
		this.sanitize();
	}

	sanitize(){
		this.safeImage = this.domSanitizer.bypassSecurityTrustUrl(this.perfil.imagen);
	}
	limpiar(opcion:string){
		if(opcion=="cancelar"){
			this.default="Escribe tu estado";
		}
		else{	
		if(this.default =="Escribe tu estado" || this.default==""){
			this.default="";
		}else{
			this.default=this.default;
		}
		}	
	}

	publicar(){
		if(this.default != "" && this.default != "Escribe tu estado" && this.usuario){
			
			this.estado.detalle=this.default;
			this.estado.usuario = this.usuario.login;
			
			this.estadoService.guardarEstado(this.estado)
			.then(estado=> this.default="Escribe tu estado")
			.catch(error => this.errorMessage = <any>error);
			this.reload();
			
			
		}
	}

	eliminar(id: number){
		
		this.estadoService.eliminarEstado(id)
		.then(any => this.reload());
	}

	reload(){
		window.location.reload();
	}


}

