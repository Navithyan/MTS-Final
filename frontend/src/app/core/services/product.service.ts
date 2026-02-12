import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import { environment } from '../../../environments/environment';
import { ApiResponse } from '../models/api-response.model';
import { Product } from '../models/product.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private readonly baseUrl = environment.apiUrl;

  constructor(private readonly http: HttpClient) {}

  getAllProducts(): Observable<Product[]> {
    return this.http.get<ApiResponse<Product[]>>(`${this.baseUrl}/secure/products`).pipe(map((res) => res.data));
  }

  getProductById(id: number): Observable<Product> {
    return this.http.get<ApiResponse<Product>>(`${this.baseUrl}/secure/products/${id}`).pipe(map((res) => res.data));
  }

  createProduct(product: Product): Observable<Product> {
    return this.http.post<ApiResponse<Product>>(`${this.baseUrl}/products`, product).pipe(map((res) => res.data));
  }

  getProductByName(name: string): Observable<Product> {
    return this.http.get<ApiResponse<Product>>(`${this.baseUrl}/products/name/${name}`).pipe(map((res) => res.data));
  }

  countByName(name: string): Observable<number> {
    return this.http.get<ApiResponse<number>>(`${this.baseUrl}/products/count/${name}`).pipe(map((res) => res.data));
  }

  searchProductsByName(name: string): Observable<Product[]> {
    return this.http.get<ApiResponse<Product[]>>(`${this.baseUrl}/productpage/names/${name}`).pipe(map((res) => res.data));
  }
}
