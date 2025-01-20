import axios from "axios";
import type { AxiosInstance, AxiosRequestConfig, AxiosResponse } from "axios";

class ApiService {
  private axiosInstance: AxiosInstance;

  constructor(baseURL: string) {
    this.axiosInstance = axios.create({
      baseURL,
    });

    // Add interceptors
    this.axiosInstance.interceptors.response.use(
      (response) => this.handleSuccess(response),
      (error) => this.handleError(error)
    );
  }

  private handleSuccess(response: AxiosResponse): AxiosResponse {
    return response;
  }

  private handleError(error: Error): Promise<never> {
    console.error("API Error:", error);
    return Promise.reject(error);
  }

  protected async get<T>(url: string, config?: AxiosRequestConfig): Promise<T> {
    const response = await this.axiosInstance.get<T>(url, config);
    return response.data;
  }
}

export default ApiService;
