package br.com.wame.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.wame.manager.SessionManager;
import br.com.wame.model.Work;
import br.com.wame.model.dto.ApiResponse;

public class WorkService {

	private static final String BASE_URL = "http://localhost:8080/api/work";
	HttpClient client;
	ObjectMapper mapper;

	public WorkService() {
		client = HttpClient.newHttpClient();
		mapper = new ObjectMapper();
	}

	public ApiResponse<List<Work>> findAll() {
		ApiResponse<List<Work>> apiResponse = buildError(
				"Não foi possível encontrar os trabalhos, verifique sua conexão.");
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(BASE_URL))
					.header("Cookie", SessionManager.getToken())
					.GET()
					.build();
			HttpResponse<String> response;
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
			TypeReference<ApiResponse<List<Work>>> typeRef = new TypeReference<>() {
			};
			apiResponse = mapper.readValue(response.body(), typeRef);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return apiResponse;
	}

	public ApiResponse<Void> save(Work work) {
		ApiResponse<Void> apiResponse = buildError("Não foi possível encontrar os trabalhos, verifique sua conexão.");
		try {
			String jsonString = mapper.writeValueAsString(work);
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(BASE_URL))
					.header("Content-Type", "application/json")
					.header("Cookie", SessionManager.getToken())
					.POST(BodyPublishers.ofString(jsonString))
					.build();
			HttpResponse<String> response;
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
			TypeReference<ApiResponse<Void>> typeRef = new TypeReference<>(){};
			apiResponse = mapper.readValue(response.body(), typeRef);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return apiResponse;
	}
	
	public ApiResponse<Work> findById(Long id) {
		ApiResponse<Work> apiResponse = buildError(
				"Não foi possível encontrar o trabalho, verifique sua conexão.");
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(BASE_URL + "/" + id))
					.header("Cookie", SessionManager.getToken())
					.GET()
					.build();
			HttpResponse<String> response;
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
			TypeReference<ApiResponse<Work>> typeRef = new TypeReference<>() {
			};
			apiResponse = mapper.readValue(response.body(), typeRef);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return apiResponse;
	}
	
	public ApiResponse<Void> delete(Long id) {
		ApiResponse<Void> apiResponse = buildError(
				"Não foi possível encontrar o trabalho, verifique sua conexão.");
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(BASE_URL + "/" + id))
					.header("Cookie", SessionManager.getToken())
					.DELETE()
					.build();
			HttpResponse<String> response;
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
			TypeReference<ApiResponse<Void>> typeRef = new TypeReference<>() {
			};
			apiResponse = mapper.readValue(response.body(), typeRef);
			System.out.println(apiResponse);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return apiResponse;
	}

	// Utilitário para criar erro padronizado
	private <T> ApiResponse<T> buildError(String message) {
		return new ApiResponse<>("error", 0, null, message);
	}
}
