import {Injectable } from '@angular/core';
import {Http, Headers, Response} from '@angular/http';
import { RestBaseService } from '../tools/rest.tools';

import 'rxjs/add/operator/toPromise';

@Injectable()
export class EstadoService extends RestBaseService{	

	private urlDetalle= '/rest/estados';
	

	constructor(private http:Http){super();}

	buscarEstados(): Promise <Estado[]>{
		return this.http.get(EstadoService.serverUrl + this.urlDetalle, this.getRestHeader())
		.toPromise()
		.then(response => {
			return response.json() as Estado[];
		})
		.catch(this.handleError);


	}

	guardarEstado(value: Estado): Promise<Estado>{
		
	return this.http.post(EstadoService.serverUrl + this.urlDetalle, JSON.stringify(value), this.getRestHeader())
			.toPromise()
			.then(response =>{
				return response.json() as Estado;
			})
			.catch(this.handleError);
	}

	eliminarEstado(id: number):Promise <any>{
		if(id){
			return this.http.delete(EstadoService.serverUrl + this.urlDetalle + "/" + id, this.getRestHeader())
			.toPromise()
			.then(response => {
				return "";
			})
			.catch(this.handleError);
		}
	}
}

export interface Estado{
	id: number;
	detalle:string;
	usuario: string;
}
